package powerup.systers.com.sink_to_swim_game;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import powerup.systers.com.GameOverActivity;
import powerup.systers.com.R;
import powerup.systers.com.powerup.PowerUpUtils;

/**
 * Created by sachinaggarwal on 7/07/17.
 */

public class SinkToSwimGame extends AppCompatActivity {

    public ImageView pointer, boat;
    public int height;
    public Animation mAnimation;
    public int score, curQuestion, speed;
    public Button trueOption, falseOption, skipOption;
    public TextView questionView, timer, scoreView;
    public long millisLeft;
    public CountDownTimer countDownTimer;
    public ViewPropertyAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sink_to_swim_game);
        boat = (ImageView) findViewById(R.id.boat);
        trueOption = (Button) findViewById(R.id.true_option);
        skipOption = (Button) findViewById(R.id.skip_option);
        falseOption = (Button) findViewById(R.id.false_option);
        questionView = (TextView) findViewById(R.id.questionView);
        scoreView = (TextView) findViewById(R.id.swim_score);
        timer = (TextView) findViewById(R.id.time);
        pointer = (ImageView) findViewById(R.id.pointer);

        //get the height of screen to move translation animation proportionally
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        initialSetUp();
    }

    public void gameStarted(View view) {
        findViewById(R.id.continue_button).setVisibility(View.GONE);
        //defines the wave animation on boat
        mAnimation = AnimationUtils.loadAnimation(this, R.animator.boat_animation); // Now it should work smoothy as hanging problem is solved now
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boat.startAnimation(mAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        boat.startAnimation(mAnimation); //starts wave animation on boat
        countDownTimer.start();
    }

    /**
     * @desc sets up the initial setting of the game
     */
    public void initialSetUp() {
        score = 0;
        speed = 2; // speed with which boat and pointer will come down
        curQuestion = 0;
        millisLeft = 40000; //=40 sec //time left before game is over
        questionView.setText(PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[curQuestion][0]);
        bringPointerAndBoatToInitial(); //brings the pointer of scale and boat to their initial positions
        setButtonsEnabled(true); //enables the true,false,and skip button for clicking
        countDownTimer = new CountDownTimer(millisLeft, 1000) {

            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                long secLeft = millisLeft / 1000;
                timer.setText("" + secLeft); //set the new time in timer textView
                bringPointerAndAvatarDown(); //every second, pointer and avatar are brought down by some amount
                float pointerBottomPoint = height * 0.75f;
                if (pointer.getTranslationY() > pointerBottomPoint) {
                    gameEnd(); //game ends if pointer reaches bottom i.e. boat drowns
                }
            }

            public void onFinish() {
                gameEnd(); //game ends when time finishes
            }
        };
    }

    /**
     * @desc brings the pointer and avatar to their initial position
     */
    public void bringPointerAndBoatToInitial() {
        float initial_height = height * 0.10f;
        pointer.setTranslationY(initial_height);
        boat.setTranslationY(-height * 0.05f);
    }

    /**
     * @desc ends the game
     */
    public void gameEnd() {
        countDownTimer.cancel();
        Intent intent = new Intent(SinkToSwimGame.this, GameOverActivity.class);
        finish();
        startActivityForResult(intent, 0);
    }

    /**
     * @desc shows the next question which a fade in and out animation
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNextQuestion() {
        curQuestion++;
        if (curQuestion == PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS.length) { //if last question in database,
            gameEnd();
            return;
        }
        final AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        final AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        fadeOut.setFillAfter(true);
        fadeIn.setDuration(800);
        fadeOut.setDuration(800);
        fadeIn.setFillAfter(true);
        fadeIn.setStartOffset(500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                questionView.setText(PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[curQuestion][0]);
                questionView.setBackground(null);
                questionView.startAnimation(fadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setButtonsEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        questionView.startAnimation(fadeOut);
    }

    /**
     * @param view the button view which is chosen i.e true, false or skip
     * @desc shows right or wrong cross according to answer
     * updates the score according to the answer chosen
     * brings the avatar and pointer up for correct answer
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void answerChosen(View view) {
        setButtonsEnabled(false);
        if (view == findViewById(R.id.true_option)) {
            if (PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[curQuestion][1] == "T") {
                score += 1;
                bringPointerAndAvatarUp();
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_right));
            } else {
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_cross));
            }
        } else if (view == findViewById(R.id.false_option)) {
            if (PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[curQuestion][1] == "F") {
                score += 1;
                bringPointerAndAvatarUp();
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_right));
            } else {
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_cross));
            }
        }

        questionView.setText("");
        showNextQuestion();
        scoreView.setText("Score: " + score);
    }

    /**
     * @desc brings avatar and pointer up by one unit
     * called for correct answer
     */
    public void bringPointerAndAvatarUp() {
        if (animator != null) {
            animator.cancel();
        }
        if (pointer.getTranslationY() < height * 0.2) //if pointer already at the topmost position, return
            return;
        float pixels = height * 0.1f;
        pointer.animate().translationYBy(-pixels).setDuration(10).setInterpolator(new LinearInterpolator());
        boat.animate().translationYBy(-(height * 0.1f * 0.66f)).setDuration(10).setInterpolator(new LinearInterpolator());
    }

    /**
     * @desc brings avatar and pointer down smoothly
     * called continously inside timer
     */
    public void bringPointerAndAvatarDown() {
        float pixels = height * 0.01f * speed;
        animator = pointer.animate().translationYBy(pixels).setDuration(1010).setInterpolator(new LinearInterpolator());
        boat.animate().translationYBy(height * 0.01f * speed * 0.66f).setDuration(1000);
    }

    /**
     * @param isEnabled mine which is opened
     * @desc enables all buttons for clicking i.e. true,false,skip
     */
    public void setButtonsEnabled(boolean isEnabled) {
        trueOption.setClickable(isEnabled);
        falseOption.setClickable(isEnabled);
        skipOption.setClickable(isEnabled);
    }

    /**
     * Since app is coming back to active state, start the timer from same time
     * Create a new timer with counter starting from last time
     */
    @Override
    public void onResume() {
        if (countDownTimer == null) //to ensure that there is no counter already running
            countDownTimer = new CountDownTimer(millisLeft, 1000) {

                public void onTick(long millisUntilFinished) {
                    millisLeft = millisUntilFinished;
                    long secLeft = millisLeft / 1000;
                    timer.setText("" + secLeft); //set the new time in timer textView
                    bringPointerAndAvatarDown(); //every second, pointer and avatar are brought down by some amount
                    float pointerBottomPoint = height * 0.75f;
                    if (pointer.getTranslationY() > pointerBottomPoint) {
                        gameEnd(); //game ends if pointer reaches bottom i.e. boat drowns
                    }
                }

                public void onFinish() {
                    gameEnd(); //game ends when time finishes
                }
            }.start();
        super.onResume();
    }

    /**
     * When app is paused, stop the timer
     * CountDown Timer can't be paused. It has to be canceled.
     */
    @Override
    public void onPause() {
        countDownTimer.cancel();
        countDownTimer = null;
        super.onPause();
    }
}
