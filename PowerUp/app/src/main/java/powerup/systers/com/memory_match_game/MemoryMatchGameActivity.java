package powerup.systers.com.memory_match_game;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import powerup.systers.com.StartActivity;
import powerup.systers.com.powerup.PowerUpUtils;

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
    public boolean calledFromActivity = true;

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
        //Making the third tile not visible
        arrayTile = new ArrayList<>();

        //Setting the view of first tile
        position = random.nextInt(8);
        imgTile1.setImageResource(PowerUpUtils.MEMORY_GAME_TILE[position]);
        updateArray(position);

        btnYes.setEnabled(false);
        btnNo.setEnabled(false);

        //Setting up the Countdown Timer
        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                txtTime.setText(String.valueOf(seconds));
            }

            public void onFinish() {
                gameEnd();
            }
        };
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
        if (arrayTile.get(positionCount).equals(arrayTile.get(positionCount - 1)))
            correctAnswer();
        else
            wrongAnswer();
        if(calledFromActivity)
            updateViews();
    }

    @OnClick(R.id.btn_no)
    public void clickNo() {
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
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                position = random.nextInt(8);
                imgTile1.setImageResource(PowerUpUtils.MEMORY_GAME_TILE[position]);
                updateArray(position);
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
    }

    /**
     *Increasing the number of wrong answers
     */
    public void wrongAnswer() {
        Log.v("MemoryMatchGameActivity", "Wrong Answer");
        wrongAnswer+=1;
    }

    /**
     * Ending the game after the time is over
     */
    public void gameEnd() {
        Log.v("MemoryMatchGameActivity", "Total score: " + score);
        Log.v("MemoryMatchGameActivity", "Number of Correct Answers: " + correctAnswer);
        Log.v("MemoryMatchGameActivity", "Number of Wrong Answers: " + wrongAnswer);
        startActivity(new Intent(MemoryMatchGameActivity.this, StartActivity.class));
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
}