package powerup.systers.com.kill_the_virus_game;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
import powerup.systers.com.powerup.PowerUpUtils;

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
    public int hitCount = 0;
    private int[] virusColors;
    private ValueAnimator virus1Animator, virus2Animator, virus3Animator, virus4Animator, virus5Animator, virus6Animator, virus7Animator, virus8Animator;
    private Animation translateSyringe;
    private CountDownTimer countDownTimer;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_the_virus_game);
        ButterKnife.bind(this);
        virusColors = getApplicationContext().getResources().getIntArray(R.array.kill_virus_colors);
        initialSetup();
        translateSyringe = AnimationUtils.loadAnimation(this, R.animator.translate);
        translateSyringe.setDuration(50);
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
        virus1Animator = animateVirus(imgVirus1, TimeUnit.SECONDS.toMillis(5), 0, 359);
        virus2Animator = animateVirus(imgVirus2, TimeUnit.SECONDS.toMillis(5), 45, 404);
        virus3Animator = animateVirus(imgVirus3, TimeUnit.SECONDS.toMillis(5), 90, 449);
        virus4Animator = animateVirus(imgVirus4, TimeUnit.SECONDS.toMillis(5), 135, 494);
        virus5Animator = animateVirus(imgVirus5, TimeUnit.SECONDS.toMillis(5), 180, 539);
        virus6Animator = animateVirus(imgVirus6, TimeUnit.SECONDS.toMillis(5), 225, 584);
        virus7Animator = animateVirus(imgVirus7, TimeUnit.SECONDS.toMillis(5), 270, 629);
        virus8Animator = animateVirus(imgVirus8, TimeUnit.SECONDS.toMillis(5), 315, 674);

        virus1Animator.start();
        virus2Animator.start();
        virus3Animator.start();
        virus4Animator.start();
        virus5Animator.start();
        virus6Animator.start();
        virus7Animator.start();
        virus8Animator.start();

        countDownTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                txtTime.setText(String.valueOf(seconds));
            }

            public void onFinish() {
                gameEnd(score);
            }
        }.start();
    }

    /**
     * Increases the score and makes the killed virus invisible
     * @param virusNumber - determines the virus that was shot
     */
    public void rightHit(int virusNumber) {
        correctHit = true;
        switch (virusNumber) {
            case PowerUpUtils.VIRUS_1:
                imgVirus1.setVisibility(View.GONE);
                break;
            case PowerUpUtils.VIRUS_2:
                imgVirus2.setVisibility(View.GONE);
                break;
            case PowerUpUtils.VIRUS_3:
                imgVirus3.setVisibility(View.GONE);
                break;
            case PowerUpUtils.VIRUS_4:
                imgVirus4.setVisibility(View.GONE);
                break;
            case PowerUpUtils.VIRUS_5:
                imgVirus5.setVisibility(View.GONE);
                break;
            case PowerUpUtils.VIRUS_6:
                imgVirus6.setVisibility(View.GONE);
                break;
            case PowerUpUtils.VIRUS_7:
                imgVirus7.setVisibility(View.GONE);
                break;
            case PowerUpUtils.VIRUS_8:
                imgVirus8.setVisibility(View.GONE);
                break;
            default:
                imgVirus1.setVisibility(View.GONE);
                break;
        }
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
        gameEnd(score);
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
}