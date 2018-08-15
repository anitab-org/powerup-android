package powerup.systers.com.save_the_blood_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;

public class SaveTheBloodTutorialActivity extends Activity {

    @BindView(R.id.save_blood_txt_tutorial_1)
    public TextView txtTutorial1;
    @BindView(R.id.save_blood_txt_tutorial_2)
    public TextView txtTutorial2;
    @BindView(R.id.save_blood_txt_tutorial_3)
    public TextView txtTutorial3;
    @BindView(R.id.txt_score_save)
    public TextView txtScore;
    @BindView(R.id.img_score_save)
    public ImageView imgScore;
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
    @BindView(R.id.txt_time_label_save)
    public TextView txtTimeLabel;
    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;
    @BindView(R.id.img_option_1)
    public ImageView imgOption1;
    @BindView(R.id.img_option_2)
    public ImageView imgOption2;
    @BindView(R.id.img_option_3)
    public ImageView imgOption3;
    @BindView(R.id.img_option_4)
    public ImageView imgOption4;
    @BindView(R.id.img_option_5)
    public ImageView imgOption5;
    @BindView(R.id.img_option_6)
    public ImageView imgOption6;
    public int tutorialCount = 0;
    private final float visible = 1, notVisible = (float) 0.7, gone = 0;
    public boolean startFromActivity = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_save_blood_tutorial);
        ButterKnife.bind(this);
        initializeViews();
    }

    @OnClick(R.id.layout_save_blood)
    public void clickScreen(){
        if(tutorialCount == 1)
            showTutorial2();
        else if(tutorialCount == 2)
            showTutorial3();
        else {
            startActivity(new Intent(SaveTheBloodTutorialActivity.this, SaveTheBloodGameActivity.class));
            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        }
    }

    public void initializeViews(){
        if(startFromActivity) {
            txtTutorial1.setAlpha(visible);
            txtTutorial2.setAlpha(gone);
            txtTutorial3.setAlpha(gone);
            txtScore.setAlpha(notVisible);
            imgScore.setAlpha(notVisible);
            txtTime.setAlpha(notVisible);
            txtTimeLabel.setAlpha(notVisible);
            txtSituation.setAlpha(visible);
            progressBar.setAlpha(notVisible);
            imgOption1.setAlpha(notVisible);
            imgOption2.setAlpha(notVisible);
            imgOption3.setAlpha(notVisible);
            imgOption4.setAlpha(notVisible);
            imgOption5.setAlpha(notVisible);
            imgOption6.setAlpha(notVisible);
        }
        tutorialCount = 1;
    }

    public void showTutorial2(){
        if(startFromActivity) {
            txtTutorial1.setAlpha(gone);
            txtTutorial2.setAlpha(visible);
            txtSituation.setAlpha(notVisible);
            txtOption1.setAlpha(notVisible);
            txtOption2.setAlpha(notVisible);
            txtOption3.setAlpha(notVisible);
            txtOption4.setAlpha(notVisible);
            txtOption5.setAlpha(notVisible);
            txtOption6.setAlpha(notVisible);
            progressBar.setAlpha(visible);
        }
        tutorialCount = 2;
    }

    public void showTutorial3(){
        if(startFromActivity) {
            txtTutorial2.setAlpha(gone);
            txtTutorial3.setAlpha(visible);
            txtTime.setAlpha(visible);
            txtTimeLabel.setAlpha(visible);
            progressBar.setAlpha(notVisible);
        }
        tutorialCount = 3;
    }
}