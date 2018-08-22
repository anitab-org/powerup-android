package powerup.systers.com.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import powerup.systers.com.R;
import powerup.systers.com.ui.map_screen_level2.MapLevel2Activity;

public class Level2TransitionActivity extends Activity {

    public Animation animationRight, animationLeft;
    @BindView(R.id.txt_msg_1)
    public TextView txtMsg1;
    @BindView(R.id.txt_msg_2)
    public TextView txtMsg2;
    @BindView(R.id.character_1)
    public ImageView imgCharacter1;
    @BindView(R.id.character_2)
    public ImageView imgCharacter2;
    @BindView(R.id.layout_map)
    public FrameLayout mapLayout;
    private AlphaAnimation fadeIn;
    private AlphaAnimation fadeIn2;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2_transition);
        ButterKnife.bind(this);

        //Setting up the initial views
        txtMsg1.setAlpha(0);
        txtMsg2.setAlpha(0);
        imgCharacter2.setVisibility(View.GONE);
        mapLayout.setAlpha((float) 0.5);

        animationRight = AnimationUtils.loadAnimation(Level2TransitionActivity.this, R.animator.translate_right);
        animationLeft = AnimationUtils.loadAnimation(Level2TransitionActivity.this, R.animator.translate_left);

        //Initializing fade in animations
        fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000);
        fadeIn.setFillAfter(true);
        fadeIn.setStartOffset(300);

        fadeIn2 = new AlphaAnimation(0.0f, 1.0f);
        fadeIn2.setDuration(2000);
        fadeIn2.setFillAfter(true);
        fadeIn2.setStartOffset(300);

        character1RightAnimation();
    }

    //Right movement of first character
    public void character1RightAnimation(){
        imgCharacter1.startAnimation(animationRight);
        animationRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                character2LeftAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }

    //Left movement of second character
    public void character2LeftAnimation(){
        imgCharacter2.setVisibility(View.VISIBLE);
        imgCharacter2.startAnimation(animationLeft);
        animationLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showMsg1();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
    }

    //Fade In animation of first text message
    public void showMsg1(){
        txtMsg1.startAnimation(fadeIn);
        txtMsg1.setAlpha(1);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                showMsg2();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    //Face In animation for second text message
    public void showMsg2(){
        txtMsg2.startAnimation(fadeIn2);
        txtMsg2.setAlpha(1);
        fadeIn2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Level2TransitionActivity.this, MapLevel2Activity.class));
                        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                    }
                }, 1000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

    }
}