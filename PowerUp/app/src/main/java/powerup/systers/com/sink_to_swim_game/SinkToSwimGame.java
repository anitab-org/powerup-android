package powerup.systers.com.sink_to_swim_game;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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

import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.utils.PowerUpUtils;


/**
 * Created by sachinaggarwal on 7/07/17.
 */

public class SinkToSwimGame extends AppCompatActivity {

    public ImageView pointer, boat;
    public int height;
    public Animation mAnimation;
    public int score, curQuestion, speed, correctAnswers, wrongAnswers;
    public Button trueOption, falseOption, skipOption;
    public TextView questionView, timer, scoreView;
    public long millisLeft;
    public CountDownTimer countDownTimer;
    public ViewPropertyAnimator animator;
    final String SOUND_TYPE = "SOUND_TYPE";
    final static int BGM = 0;
    private SharedPreferences prefs;
    final String CURR_POSITION = "CURR_POSITION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sink_to_swim_game);

        // init views
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

    /**
     * @desc sets up the initial setting of the game
     */
    public void initialSetUp() {
        boolean calledByTutorialsActivity = getIntent().
                getBooleanExtra(PowerUpUtils.CALLED_BY, false);
        if(!calledByTutorialsActivity) {
            SinkToSwimSessionManager session = new SinkToSwimSessionManager(this);
            correctAnswers = session.getCorrectAnswer();
            wrongAnswers = session.getWrongAnswer();
            score = session.getCurrScore();
            scoreView.setText("Score: " + score);
            speed = session.getSpeed();
            curQuestion = session.getCurrQuestion();
            millisLeft = session.getTimeLeft();
            boat.setY(session.getBoatHeight());
            pointer.setY(session.getPointerHeight());
        }else {
            correctAnswers = 0;
            wrongAnswers = 0;
            score = 0;
            speed = 2; // speed with which boat and pointer will come down
            curQuestion = 0;
            millisLeft = 40000; //=40 sec //time left before game is over
            bringPointerAndBoatToInitial(); //brings the pointer of scale and boat to their initial positions
        }
        if (curQuestion == PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS.length) {
            gameEnd();
            return;
        }
        questionView.setText(PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[curQuestion][0]);
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
        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(CURR_POSITION, 0);
        edit.apply();
        gameBegins();
    }


    public void gameBegins() {
        //defines the wave animation on boat
        mAnimation = AnimationUtils.loadAnimation(this, R.animator.boat_animation);
        mAnimation.setInterpolator(new LinearInterpolator());
        mAnimation.setRepeatMode(Animation.INFINITE); //does not work
        boat.startAnimation(mAnimation); //starts wave animation on boat
        countDownTimer.start();
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
        if(countDownTimer!=null){countDownTimer.cancel();}
        Intent intent = new Intent(SinkToSwimGame.this, SinkToSwimEndActivity.class);
        intent.putExtra(PowerUpUtils.SCORE,score);
        intent.putExtra(PowerUpUtils.CORRECT_ANSWERS,correctAnswers);
        intent.putExtra(PowerUpUtils.WRONG_ANSWER,wrongAnswers);
        SessionHistory.totalPoints += score;
        SessionHistory.currScenePoints += score;
        finish();
        startActivity(intent);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
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
        fadeIn.setDuration(600);
        fadeOut.setDuration(600);
        fadeIn.setFillAfter(true);
        fadeIn.setStartOffset(300);
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
            if (PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[curQuestion][1].equals("T")) {
                score += 1;
                correctAnswers++;
                bringPointerAndAvatarUp();
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_right));
            } else {
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_cross));
                wrongAnswers++;
            }
            questionView.setText("");
        } else if (view == findViewById(R.id.false_option)) {
            if (PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[curQuestion][1].equals("F")) {
                score += 1;
                correctAnswers++;
                bringPointerAndAvatarUp();
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_right));
            } else {
                questionView.setBackground(getResources().getDrawable(R.drawable.swim_cross));
                wrongAnswers++;
            }
            questionView.setText("");
        }

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
        startService(new Intent(SinkToSwimGame.this, SinkToSwimSound.class)
                .putExtra(SOUND_TYPE, BGM));
        super.onResume();
    }

    /**
     * When app is paused, stop the timer
     * CountDown Timer can't be paused. It has to be canceled.
     */
    @Override
    public void onPause() {
        super.onPause();
        SinkToSwimSessionManager session = new SinkToSwimSessionManager(this);
        countDownTimer.cancel();
        countDownTimer = null;
        float boatHeight = boat.getY();
        float pointerHeight = pointer.getY();
        session.saveData(score, millisLeft, curQuestion,
                wrongAnswers, correctAnswers, speed, boatHeight, pointerHeight);
        stopService(new Intent(SinkToSwimGame.this, SinkToSwimSound.class));
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(SinkToSwimGame.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
