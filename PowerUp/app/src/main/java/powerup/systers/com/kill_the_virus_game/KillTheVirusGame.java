package powerup.systers.com.kill_the_virus_game;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.ui.map_screen_level2.MapLevel2Activity;
import powerup.systers.com.utils.PowerUpUtils;

public class KillTheVirusGame extends Activity {

    @BindView(R.id.img_virus_1)
    public ImageView imgVirus1;
    @BindView(R.id.img_virus_2)
    public ImageView imgVirus2;
    @BindView(R.id.img_virus_3)
    public ImageView imgVirus3;
    @BindView(R.id.img_virus_4)
    public ImageView imgVirus4;
    @BindView(R.id.img_virus_5)
    public ImageView imgVirus5;
    @BindView(R.id.img_virus_6)
    public ImageView imgVirus6;
    @BindView(R.id.img_virus_7)
    public ImageView imgVirus7;
    @BindView(R.id.img_virus_8)
    public ImageView imgVirus8;
    @BindView(R.id.txt_time)
    public TextView txtTime;
    @BindView(R.id.txt_score)
    public TextView txtScore;
    public int score = 0;
    public boolean correctHit = false;
    @BindView(R.id.img_syringe)
    public View imgSyringe;
    @BindView(R.id.txt_lives)
    public TextView txtLives;
    public int hitCount = 0;
    private int[] virusColors;
    private ValueAnimator virus1Animator, virus2Animator, virus3Animator, virus4Animator, virus5Animator, virus6Animator, virus7Animator, virus8Animator;
    private Animation translateSyringe;
    private CountDownTimer countDownTimer;
    private int lives = 3;
    private long duration = 5000, millisLeft = 30000;;
    private ImageView[] imgVirusArray = new ImageView[9];

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_the_virus_game);
        ButterKnife.bind(this);
        imgVirusArray = new ImageView[]{null, imgVirus1, imgVirus2, imgVirus3, imgVirus4, imgVirus5, imgVirus6, imgVirus7, imgVirus8};
        virusColors = getApplicationContext().getResources().getIntArray(R.array.kill_virus_colors);
        initialSetup();
        translateSyringe = AnimationUtils.loadAnimation(this, R.animator.translate);
    }

    @OnClick(R.id.constraint_layout_kill)
    public void clickScreen() {
        //Start linear animation of syringe
        animateSyringe();
    }

    /**
     * Set rotational animation on the virus images
     * Start the countdown timer
     */
    private void initialSetup() {
        boolean calledByTutorialActivity = getIntent().getBooleanExtra(PowerUpUtils.CALLED_BY, false);
        if(!calledByTutorialActivity){
            KillTheVirusSessionManager session = new KillTheVirusSessionManager(this);
            score = session.getScore();
            millisLeft = session.getTime();
            lives = session.getLives();
            duration = session.getSpeed();
            txtScore.setText(""+score);
            txtLives.setText(""+lives);
            for(int i = 1; i<= 8; i++){
                if(PowerUpUtils.VIRUS_HIT[i])
                    imgVirusArray[i].setVisibility(View.GONE);
            }
            Log.v("KillTheVirusGame","Saved Score: " + score );
            Log.v("KillTheVirusGame", "Saved Time: " + millisLeft);
            Log.v("KillTheVirusGame", "Saved Lives: " + lives);
            Log.v("KillTheVirusGame", "Saved Duration: " + duration);
        }

        virus1Animator = animateVirus(imgVirus1, TimeUnit.SECONDS.toMillis(duration/1000), 0, 359);
        virus2Animator = animateVirus(imgVirus2, TimeUnit.SECONDS.toMillis(duration/1000), 45, 404);
        virus3Animator = animateVirus(imgVirus3, TimeUnit.SECONDS.toMillis(duration/1000), 90, 449);
        virus4Animator = animateVirus(imgVirus4, TimeUnit.SECONDS.toMillis(duration/1000), 135, 494);
        virus5Animator = animateVirus(imgVirus5, TimeUnit.SECONDS.toMillis(duration/1000), 180, 539);
        virus6Animator = animateVirus(imgVirus6, TimeUnit.SECONDS.toMillis(duration/1000), 225, 584);
        virus7Animator = animateVirus(imgVirus7, TimeUnit.SECONDS.toMillis(duration/1000), 270, 629);
        virus8Animator = animateVirus(imgVirus8, TimeUnit.SECONDS.toMillis(duration/1000), 315, 674);

        virus1Animator.start();
        virus2Animator.start();
        virus3Animator.start();
        virus4Animator.start();
        virus5Animator.start();
        virus6Animator.start();
        virus7Animator.start();
        virus8Animator.start();

        countDownTimer = new CountDownTimer(millisLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                long secLeft = millisLeft / 1000;
                txtTime.setText(String.valueOf(secLeft));
            }

            public void onFinish() {
                gameEnd(score);
            }
        }.start();
        startService(new Intent(this, KillTheVirusSound.class)
                .putExtra("SOUND_TYPE", 0));
    }

    /**
     * Increases the score and makes the killed virus invisible
     * @param virusNumber - determines the virus that was shot
     */
    public void rightHit(int virusNumber) {
        correctHit = true;
        imgVirusArray[virusNumber].setVisibility(View.GONE);
        PowerUpUtils.VIRUS_HIT[virusNumber] = true;
        score = increasePoint(score);
        txtScore.setText(Integer.toString(score));
        if (score == 8)
            gameEnd(score);
    }

    /**
     * End the game if the hit was incorrect
     */
    public void wrongHit() {
        correctHit = false;
        updateLives();
    }

    /**
     * Increases the points for correct hit
     * @param currentScore - the current score
     * @return - increases the value of current score by one
     */
    public int increasePoint(int currentScore) {
        return currentScore + 1;
    }

    /**
     * Saves the total score and ends the game
     * @param totalScore - value of total score
     */
    private void gameEnd(int totalScore) {
        countDownTimer.cancel();
        Intent intent = new Intent(KillTheVirusGame.this, KillTheVirusEndActivity.class);
        //Passing the final score using intent extra
        intent.putExtra(getString(R.string.score_kill_the_virus),totalScore);
        //Adding mini-game scores
        SessionHistory.totalPoints += score;
        SessionHistory.currScenePoints += score;
        //End the Game
        startActivity(intent);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    //Attaches rotational animation to all the virus images
    private ValueAnimator animateVirus(final ImageView virus, long duration, int startPoint, int endPoint) {
        ValueAnimator anim = ValueAnimator.ofInt(startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) virus.getLayoutParams();
                layoutParams.circleAngle = val;
                virus.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(duration);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatMode(ValueAnimator.RESTART);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        return anim;
    }

    /**
     * Matches the color of the virus with syringe and calls rightHit() or wrongHit() accordingly
     * @param virusType - the type of virus that was shot ( 0-Purple, 1-Green, 2-Pink, 3-Black )
     */
    private void checkHit(int virusType) {
        if (((int) virus1Animator.getAnimatedValue() >= 70 && (int) virus1Animator.getAnimatedValue() <= 110) && (virusType == PowerUpUtils.VIRUS_TYPE_1))
            rightHit(PowerUpUtils.VIRUS_1);
        else if (((int) virus2Animator.getAnimatedValue() >= 70 && (int) virus2Animator.getAnimatedValue() <= 110) && (virusType == PowerUpUtils.VIRUS_TYPE_2))
            rightHit(PowerUpUtils.VIRUS_2);
        else if ((((int) virus3Animator.getAnimatedValue() >= 70 && (int) virus3Animator.getAnimatedValue() <= 110) || ((int) virus3Animator.getAnimatedValue() >= 430 && (int) virus3Animator.getAnimatedValue() <= 470)) && (virusType == PowerUpUtils.VIRUS_TYPE_3))
            rightHit(PowerUpUtils.VIRUS_3);
        else if (((int) virus4Animator.getAnimatedValue() >= 430 && (int) virus4Animator.getAnimatedValue() <= 470) && (virusType == PowerUpUtils.VIRUS_TYPE_4))
            rightHit(PowerUpUtils.VIRUS_4);
        else if (((int) virus5Animator.getAnimatedValue() >= 430 && (int) virus5Animator.getAnimatedValue() <= 470) && (virusType == PowerUpUtils.VIRUS_TYPE_1))
            rightHit(PowerUpUtils.VIRUS_5);
        else if (((int) virus6Animator.getAnimatedValue() >= 430 && (int) virus6Animator.getAnimatedValue() <= 470) && (virusType == PowerUpUtils.VIRUS_TYPE_2))
            rightHit(PowerUpUtils.VIRUS_6);
        else if (((int) virus7Animator.getAnimatedValue() >= 430 && (int) virus7Animator.getAnimatedValue() <= 470) && (virusType == PowerUpUtils.VIRUS_TYPE_3))
            rightHit(PowerUpUtils.VIRUS_7);
        else if(((int) virus8Animator.getAnimatedValue() >= 430 && (int) virus8Animator.getAnimatedValue() <= 470) && (virusType == PowerUpUtils.VIRUS_TYPE_4))
            rightHit(PowerUpUtils.VIRUS_8);
        else
            wrongHit();
        updateSpeed();
    }

    //Starts linear animation of the syringe each time the screen is clicked
    private void animateSyringe() {
        imgSyringe.startAnimation(translateSyringe);
        translateSyringe.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                checkHit(hitCount);
                incrementHitCount();
                imgSyringe.setBackgroundColor(virusColors[hitCount]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void incrementHitCount(){
        hitCount = hitCount + 1;
        if (hitCount == 4)
            hitCount = 0;
    }

    public void updateLives(){
        lives = lives - 1;
        txtLives.setText("" + lives);
        if(lives == 0)
            gameEnd(score);
    }

    public void updateSpeed(){
        duration = duration - 500;
        virus1Animator.setDuration(duration);
        virus2Animator.setDuration(duration);
        virus3Animator.setDuration(duration);
        virus4Animator.setDuration(duration);
        virus5Animator.setDuration(duration);
        virus6Animator.setDuration(duration);
        virus7Animator.setDuration(duration);
        virus8Animator.setDuration(duration);
    }

    @Override
    protected void onPause() {
        KillTheVirusSessionManager sessionManager = new KillTheVirusSessionManager(this);
        countDownTimer.cancel();
        countDownTimer = null;
        sessionManager.saveData(score,millisLeft ,lives, duration);
        stopService(new Intent(KillTheVirusGame.this, KillTheVirusSound.class));
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KillTheVirusGame.this, MapLevel2Activity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }
}