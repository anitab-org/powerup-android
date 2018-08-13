package powerup.systers.com.kill_the_virus_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.utils.PowerUpUtils;

public class KillTheVirusTutorials extends Activity {

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
    @BindView(R.id.img_syringe)
    public View imgSyringe;
    @BindView(R.id.txt_tutorial_1)
    public TextView txtTutorial1;
    @BindView(R.id.txt_tutorial_2)
    public TextView txtTutorial2;
    @BindView(R.id.txt_tutorial_3)
    public TextView txtTutorial3;
    @BindView(R.id.time_label)
    public TextView txtTimeLabel;
    @BindView(R.id.txt_time)
    public TextView txtTime;
    @BindView(R.id.img_score)
    public ImageView imgScore;
    @BindView(R.id.txt_score)
    public TextView txtScore;
    @BindView(R.id.txt_lives_label)
    public TextView txtLivesLabel;
    @BindView(R.id.txt_lives)
    public TextView txtLives;
    public int tutorialCount = 0;
    private final float visible = 1, notVisible = (float) 0.7, gone = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_the_virus_tutorial);
        ButterKnife.bind(this);

        //Resetting the array
        for(int i = 1; i<= 8; i++)
            PowerUpUtils.VIRUS_HIT[i] = false;
        initializeViews();
    }

    @OnClick(R.id.constraint_layout_kill)
    public void clickScreen() {
        if (tutorialCount == 1)
            showTutorial2();
        else if (tutorialCount == 2)
            showTutorial3();
        else {
            Intent intent = new Intent(KillTheVirusTutorials.this, KillTheVirusGame.class);
            intent.putExtra(PowerUpUtils.CALLED_BY, true);
            startActivity(intent);
            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        }
    }

    public void initializeViews() {
        imgSyringe.setAlpha(notVisible);
        txtTutorial2.setAlpha(gone);
        txtTutorial3.setAlpha(gone);
        txtScore.setAlpha(notVisible);
        imgScore.setAlpha(notVisible);
        txtTime.setAlpha(notVisible);
        txtTimeLabel.setAlpha(notVisible);
        txtLives.setAlpha(notVisible);
        txtLivesLabel.setAlpha(notVisible);
        tutorialCount = 1;
    }

    public void showTutorial2() {
        imgVirus1.setAlpha(notVisible);
        imgVirus2.setAlpha(notVisible);
        imgVirus3.setAlpha(notVisible);
        imgVirus4.setAlpha(notVisible);
        imgVirus5.setAlpha(notVisible);
        imgVirus6.setAlpha(notVisible);
        imgVirus7.setAlpha(notVisible);
        imgVirus8.setAlpha(notVisible);
        imgSyringe.setAlpha(visible);
        txtTutorial1.setAlpha(gone);
        txtTutorial2.setAlpha(visible);
        tutorialCount = 2;
    }

    public void showTutorial3() {
        imgSyringe.setAlpha(notVisible);
        txtTutorial2.setAlpha(gone);
        txtTutorial3.setAlpha(visible);
        txtScore.setAlpha(notVisible);
        imgScore.setAlpha(notVisible);
        txtTime.setAlpha(visible);
        txtTimeLabel.setAlpha(visible);
        tutorialCount = 3;
    }
}