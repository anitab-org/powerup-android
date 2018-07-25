package powerup.systers.com.save_the_blood_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.ui.map_screen_level2.MapLevel2Activity;
import powerup.systers.com.utils.PowerUpUtils;

public class SaveTheBloodGameActivity extends Activity {

    @BindView(R.id.txt_score_save)
    public TextView txtScore;
    @BindView(R.id.txt_time_save)
    public TextView txtTime;
    @BindView(R.id.txt_save_blood_label)
    public TextView txtSituation;
    @BindView(R.id.txt_option_1)
    public TextView txtOption1;
    @BindView(R.id.txt_option_2)
    public TextView txtOption2;
    @BindView(R.id.txt_option_3)
    public TextView txtOption3;
    @BindView(R.id.txt_option_4)
    public TextView txtOption4;
    @BindView(R.id.txt_option_5)
    public TextView txtOption5;
    @BindView(R.id.txt_option_6)
    public TextView txtOption6;
    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;
    public int progress = 100, count = 0, answerCount = 0, roundCount = 1, score = 0, correctAnswer = 0, wrongAnswer = 0;
    public boolean calledFromActivity = true;
    private long millisLeft = 7000;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_the_blood_game);
        ButterKnife.bind(this);

        countDownTimer = new CountDownTimer(millisLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                int seconds = (int) (millisLeft / 1000);
                txtTime.setText(String.valueOf(seconds));
                progress = progress - 2;
                progressBar.setProgress(progress);
                if (progress <= 0)
                    gameEnd();
            }

            public void onFinish() {
                showNextRound(null);
                if (roundCount == 7)
                    gameEnd();
            }
        };
        initializeViews();
    }

    //Setting up the initial state of elements
    public void initializeViews() {
        progressBar.setProgress(progress);
        countDownTimer.start();
        txtSituation.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][0]);
        txtOption1.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][1]);
        txtOption2.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][2]);
        txtOption3.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][3]);
        txtOption4.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][4]);
        txtOption5.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][5]);
        txtOption6.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][6]);
    }

    @OnClick(R.id.img_option_1)
    public void firstOptionClicked() {
        checkAnswer(1);
    }

    @OnClick(R.id.img_option_2)
    public void secondOptionClicked() {
        checkAnswer(2);
    }

    @OnClick(R.id.img_option_3)
    public void thirdOptionClicked() {
        checkAnswer(3);
    }

    @OnClick(R.id.img_option_4)
    public void fourthOptionClicked() {
        checkAnswer(4);
    }

    @OnClick(R.id.img_option_5)
    public void fifthOptionClicked() {
        checkAnswer(5);
    }

    @OnClick(R.id.img_option_6)
    public void sixthOptionClicked() {
        checkAnswer(6);
    }

    /**
     * Updating the views after each round
     *
     * @param tv - Last correct text option that was clicked to ensure that textviews update after changing color
     */
    public void showNextRound(TextView tv) {
        if (roundCount == 7)
            gameEnd();
        millisLeft = 7000;
        countDownTimer.start();
        txtOption1.setTextColor(getResources().getColor(R.color.powerup_dark_blue));
        txtOption2.setTextColor(getResources().getColor(R.color.powerup_dark_blue));
        txtOption3.setTextColor(getResources().getColor(R.color.powerup_dark_blue));
        txtOption4.setTextColor(getResources().getColor(R.color.powerup_dark_blue));
        txtOption5.setTextColor(getResources().getColor(R.color.powerup_dark_blue));
        txtOption6.setTextColor(getResources().getColor(R.color.powerup_dark_blue));
        count += 1;
        if (count != 7) {
            updateQA(tv);
        } else {
            countDownTimer.cancel();
        }
        roundCount += 1;
        answerCount = 0;
    }

    /**
     * Adding animations to update each views
     *
     * @param textView - Last correct text option that was clicked to ensure that textviews update after changing color
     */
    public void updateQA(final TextView textView) {
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
                if (textView != null)
                    textView.setTextColor(getResources().getColor(R.color.correct_answer));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                txtSituation.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][0]);
                txtOption1.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][1]);
                txtOption2.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][2]);
                txtOption3.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][3]);
                txtOption4.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][4]);
                txtOption5.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][5]);
                txtOption6.setText(PowerUpUtils.TXT_QUES_ANS_SAVE_BLOOD[count][6]);
                txtSituation.startAnimation(fadeIn);
                txtOption1.startAnimation(fadeIn);
                txtOption2.startAnimation(fadeIn);
                txtOption3.startAnimation(fadeIn);
                txtOption4.startAnimation(fadeIn);
                txtOption5.startAnimation(fadeIn);
                txtOption6.startAnimation(fadeIn);
                if (textView != null)
                    textView.setTextColor(getResources().getColor(R.color.powerup_dark_blue));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        txtSituation.startAnimation(fadeOut);
        txtOption1.startAnimation(fadeOut);
        txtOption2.startAnimation(fadeOut);
        txtOption3.startAnimation(fadeOut);
        txtOption4.startAnimation(fadeOut);
        txtOption5.startAnimation(fadeOut);
        txtOption6.startAnimation(fadeOut);
    }

    /**
     * Checking the answer is correct, wrong , neutral
     *
     * @param position - The text view that was clicked
     */
    public void checkAnswer(int position) {
        TextView tv = findViewById(PowerUpUtils.SAVE_BLOOD_TEXT_VIEWS[position]);

        //When the chosen answer is correct
        if (PowerUpUtils.SAVE_BLOOD_ANSWER[count][position] == 1) {

            correctAnswer += 1;
            answerCount += 1;
            progress += 5;
            score += 1;

            if (calledFromActivity) {
                Log.v("Save Blood Game", "Correct Answer Clicked");
                tv.setTextColor(getResources().getColor(R.color.correct_answer));
                txtScore.setText("" + score);
                progressBar.setProgress(progress);
                if (PowerUpUtils.SAVE_BLOOD_ANSWER_COUNT[roundCount] == answerCount)
                    showNextRound(tv);
            }
        }

        //When the chosen answer is neutral
        else if (PowerUpUtils.SAVE_BLOOD_ANSWER[count][position] == 2) {
            Log.v("Save Blood Game", "Neutral Answer Clicked");
            tv.setTextColor(getResources().getColor(R.color.powerup_yellow));
        }

        //When the chosen Answer is wrong
        else if (PowerUpUtils.SAVE_BLOOD_ANSWER[count][position] == 0) {
            wrongAnswer += 1;
            if (calledFromActivity) {
                Log.v("Save Blood Game", "Wrong Answer Clicked");
                tv.setTextColor(getResources().getColor(R.color.wrong_answer));
            }
        }
    }

    /**
     * Save all the required values
     */
    public void gameEnd() {
        Intent intent = new Intent(SaveTheBloodGameActivity.this, SaveTheBloodEndActivity.class);
        intent.putExtra("score", score);
        intent.putExtra("correct", correctAnswer);
        intent.putExtra("wrong", wrongAnswer);
        countDownTimer.cancel();
        SessionHistory.totalPoints += score;
        SessionHistory.currScenePoints += score;
        Log.v("SaveBloodGameActivity", "Score: " + score);
        Log.v("SaveBloodGameActivity", "Correct answers: " + correctAnswer);
        Log.v("SaveBloodGameActivity", "Wrong answers " + wrongAnswer);
        startActivity(intent);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        countDownTimer.cancel();
        startActivity(new Intent(SaveTheBloodGameActivity.this, MapLevel2Activity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @Override
    protected void onPause() {
        SaveTheBloodSessionManager sessionManager = new SaveTheBloodSessionManager(this);
        sessionManager.saveData(score, millisLeft, count, correctAnswer, wrongAnswer, progress);
        countDownTimer.cancel();
        countDownTimer = null;
        super.onPause();
    }
}