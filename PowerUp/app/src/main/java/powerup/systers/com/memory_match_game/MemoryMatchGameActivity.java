package powerup.systers.com.memory_match_game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.utils.PowerUpUtils;

public class MemoryMatchGameActivity extends Activity {

    @BindView(R.id.img_tile_2)
    public ImageView imgTile2;
    @BindView(R.id.img_tile_1)
    public ImageView imgTile1;
    @BindView(R.id.txt_score_memory)
    public TextView txtScore;
    @BindView(R.id.txt_time_memory)
    public TextView txtTime;
    @BindView(R.id.btn_yes)
    public Button btnYes;
    @BindView(R.id.btn_no)
    public Button btnNo;
    @BindView(R.id.btn_start)
    public ImageView btnStart;
    public List<Integer> arrayTile;
    public int correctAnswer = 0, wrongAnswer = 0;
    public Random random;
    public int position, positionCount = -1, score;
    private Animation translateTile;
    private CountDownTimer countDownTimer;
    public boolean calledFromActivity = true, correctAns = true, buttonClick = false;;
    private long millisLeft = 30000;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_match_game);
        ButterKnife.bind(this);
        translateTile = AnimationUtils.loadAnimation(this, R.animator.translate_tile);
        random = new Random();
        initializeView();
    }

    public void initializeView() {
        arrayTile = new ArrayList<>();
        boolean calledByTutorialActivity = getIntent().getBooleanExtra(PowerUpUtils.CALLED_BY, false);

        if(!calledByTutorialActivity){
            MemoryMatchSessionManager sessionManager = new MemoryMatchSessionManager(this);
            score = sessionManager.getCurrScore();
            millisLeft = sessionManager.getTimeLeft();
            correctAnswer = sessionManager.getCorrectAnswer();
            wrongAnswer = sessionManager.getWrongAnswer();
            arrayTile.add(sessionManager.getPrevTile());
            arrayTile.add(sessionManager.getCurrTile());
            imgTile1.setImageResource(PowerUpUtils.MEMORY_GAME_TILE[arrayTile.get(1)]);
            btnStart.setVisibility(View.GONE);
            positionCount = 1;
            txtScore.setText(""+score);
        } else {
            //Setting the view of first tile
            position = random.nextInt(8);
            imgTile1.setImageResource(PowerUpUtils.MEMORY_GAME_TILE[position]);
            updateArray(position);

            btnYes.setEnabled(false);
            btnNo.setEnabled(false);
        }

        //Setting up the Countdown Timer
        countDownTimer = new CountDownTimer(millisLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                int seconds = (int) (millisLeft / 1000);
                txtTime.setText(String.valueOf(seconds));
            }

            public void onFinish() {
                gameEnd();
            }
        };
        if(!calledByTutorialActivity)
            countDownTimer.start();
    }

    @OnClick(R.id.btn_start)
    public void clickStart() {
        //changing the second tile to star
        imgTile2.setImageResource(R.drawable.yellow_star);

        //StartTimer
        countDownTimer.start();

        updateViews();
        btnStart.setVisibility(View.GONE);
        btnYes.setEnabled(true);
        btnNo.setEnabled(true);
    }

    @OnClick(R.id.btn_yes)
    public void clickYes() {
        buttonClick = true;
        if (arrayTile.get(positionCount).equals(arrayTile.get(positionCount - 1)))
            correctAnswer();
        else
            wrongAnswer();
        if(calledFromActivity)
            updateViews();
    }

    @OnClick(R.id.btn_no)
    public void clickNo() {
        buttonClick = true;
        if (!arrayTile.get(positionCount).equals(arrayTile.get(positionCount - 1)))
            correctAnswer();
        else
            wrongAnswer();
        if(calledFromActivity)
            updateViews();
    }

    /**
     * Left animation of the tiles
     * Updating the value of score after each answer
     */
    public void updateViews() {
        txtScore.setText("" + score);
        imgTile1.startAnimation(translateTile);
        translateTile.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btnYes.setEnabled(false);
                btnNo.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                position = random.nextInt(8);
                if(correctAns && buttonClick) {
                    imgTile2.setImageResource(R.drawable.green_star);
                    playSound(MemoryMatchSound.TYPE_CORRECT);
                }
                else {
                    if(buttonClick) {
                        imgTile2.setImageResource(R.drawable.red_star);
                        playSound(MemoryMatchSound.TYPE_INCORRECT);
                    }
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        imgTile2.setImageResource(R.drawable.yellow_star);
                    }
                }, 500);
                updateArray(position);
                imgTile1.setImageResource(PowerUpUtils.MEMORY_GAME_TILE[position]);
                btnYes.setEnabled(true);
                btnNo.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    /**
     * Increase the points when answer is correct
     * Increasing the number of correct answer
     */
    public void correctAnswer() {
        Log.v("MemoryMatchGameActivity", "Correct Answer");
        //increasing the score
        score = score + 1;
        correctAnswer+=1;
        correctAns = true;
    }

    /**
     *Increasing the number of wrong answers
     */
    public void wrongAnswer() {
        Log.v("MemoryMatchGameActivity", "Wrong Answer");
        wrongAnswer+=1;
        correctAns = false;
    }

    /**
     * Ending the game after the time is over
     */
    public void gameEnd() {
        Log.v("MemoryMatchGameActivity", "Total score: " + score);
        Log.v("MemoryMatchGameActivity", "Number of Correct Answers: " + correctAnswer);
        Log.v("MemoryMatchGameActivity", "Number of Wrong Answers: " + wrongAnswer);
        Intent intent = new Intent(MemoryMatchGameActivity.this, MemoryMatchEndActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("wrong", wrongAnswer);
        intent.putExtra("correct", correctAnswer);
        //Adding mini-game scores
        SessionHistory.totalPoints += score;
        SessionHistory.currScenePoints += score;
        startActivity(intent);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    /**
     * Storing values in array
     * @param value - value of tile that needs to be stored
     */
    public void updateArray(int value) {
        positionCount = positionCount + 1;
        arrayTile.add(value);
        Log.v("MemoryMatchGameActivity", " Value stored at " + positionCount + " is " + arrayTile.get(positionCount));
    }

    private void playSound(int sound) {
        Intent intent = new Intent(this, MemoryMatchSound.class)
                .putExtra(MemoryMatchSound.SOUND_TYPE_EXTRA, sound);
        startService(intent);
    }

    @Override
    protected void onPause() {
        MemoryMatchSessionManager sessionManager = new MemoryMatchSessionManager(this);
        countDownTimer.cancel();
        countDownTimer = null;
        sessionManager.saveData(score, millisLeft, arrayTile.get(positionCount), arrayTile.get(positionCount - 1) , correctAnswer, wrongAnswer);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MemoryMatchGameActivity.this, MapActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }
}