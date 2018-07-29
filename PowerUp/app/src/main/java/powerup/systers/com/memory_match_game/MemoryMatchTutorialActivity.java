package powerup.systers.com.memory_match_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.utils.PowerUpUtils;

public class MemoryMatchTutorialActivity extends Activity {

    @BindView(R.id.img_tile_2)
    public ImageView imgTile2;
    @BindView(R.id.img_tile_1)
    public ImageView imgTile1;
    @BindView(R.id.img_score)
    public ImageView imgScore;
    @BindView(R.id.txt_score_memory)
    public TextView txtScore;
    @BindView(R.id.txt_time_label)
    public TextView txtTimeLabel;
    @BindView(R.id.txt_time_memory)
    public TextView txtTime;
    @BindView(R.id.btn_yes)
    public Button btnYes;
    @BindView(R.id.btn_no)
    public Button btnNo;
    @BindView(R.id.btn_start)
    public ImageView btnStart;
    @BindView(R.id.txt_label_memory)
    public TextView txtMemoryLabel;
    @BindView(R.id.txt_memory_tutorial_1)
    public TextView txtTutorial1;
    @BindView(R.id.txt_memory_tutorial_2)
    public TextView txtTutorial2;
    @BindView(R.id.txt_memory_tutorial_3)
    public TextView txtTutorial3;
    public int tutorialCount;
    private final float visible = 1, notVisible = (float) 0.7, gone = 0;
    public boolean startFromActivity = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_match_tutorial);
        ButterKnife.bind(this);
        initializeViews();
    }

    @OnClick(R.id.layout_memory_match)
    public void clickScreen() {
        if (tutorialCount == 1) {
            showTutorial2();
        } else if (tutorialCount == 2) {
            showTutorial3();
        }
        else{
            Intent intent = new Intent(MemoryMatchTutorialActivity.this, MemoryMatchGameActivity.class);
            intent.putExtra(PowerUpUtils.CALLED_BY, true);
            startActivity(intent);
            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        }
    }

    //Initialize the visibility of design elements according to first tutorial
    public void initializeViews() {
        if(startFromActivity) {
            txtTutorial1.setAlpha(visible);
            txtTutorial2.setAlpha(gone);
            txtTutorial3.setAlpha(gone);
            imgScore.setAlpha(notVisible);
            txtScore.setAlpha(notVisible);
            txtMemoryLabel.setAlpha(notVisible);
            txtTime.setAlpha(notVisible);
            txtTimeLabel.setAlpha(notVisible);
            imgTile1.setAlpha(visible);
            imgTile2.setAlpha(notVisible);
            btnYes.setAlpha(notVisible);
            btnNo.setAlpha(notVisible);
            btnStart.setAlpha(gone);
        }
        tutorialCount = 1;
    }

    //Initialize the visibility of design elements according to second tutorial
    public void showTutorial2() {
        if(startFromActivity) {
            txtTutorial1.setAlpha(gone);
            txtTutorial2.setAlpha(visible);
            imgTile1.setAlpha(notVisible);
            btnYes.setAlpha(visible);
            btnNo.setAlpha(visible);
        }
        tutorialCount = 2;
    }

    //Initialize the visibility of design elements according to third tutorial
    public void showTutorial3() {
        if(startFromActivity) {
            txtTutorial2.setAlpha(gone);
            txtTutorial3.setAlpha(visible);
            imgScore.setAlpha(notVisible);
            txtScore.setAlpha(notVisible);
            txtMemoryLabel.setAlpha(notVisible);
            txtTime.setAlpha(visible);
            txtTimeLabel.setAlpha(visible);
            btnYes.setAlpha(notVisible);
            btnNo.setAlpha(notVisible);
        }
        tutorialCount = 3;
    }
}