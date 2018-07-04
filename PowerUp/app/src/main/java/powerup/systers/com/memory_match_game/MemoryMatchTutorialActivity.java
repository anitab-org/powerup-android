package powerup.systers.com.memory_match_game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;

public class MemoryMatchTutorialActivity extends Activity {


    @BindView(R.id.img_tile_2)
    public ImageView imgTile2;
    @BindView(R.id.img_tile_1)
    public ImageView imgTile1;
    @BindView(R.id.img_score)
    public ImageView imgScore;
    @BindView(R.id.txt_score)
    public TextView txtScore;
    @BindView(R.id.txt_time_label)
    public TextView txtTimeLabel;
    @BindView(R.id.txt_time)
    public TextView txtTime;
    @BindView(R.id.btn_yes)
    public Button btnYes;
    @BindView(R.id.btn_no)
    public Button btnNo;
    @BindView(R.id.btn_start)
    public Button btnStart;
    @BindView(R.id.txt_label_memory)
    public TextView txtMemoryLabel;
    public int tutorialCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_match_tutorial);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.layout_memory_match)
    public void clickScreen() {
        if (tutorialCount == 1) {
            showTutorial2();
        } else if (tutorialCount == 2) {
            showTutorial3();
        }
        //        else {
        //            Start the game
        //        }
    }

    public void initializeViews() {
        //TODO: Set the visibility according to first tutorial
    }

    public void showTutorial2() {
        //TODO: Update the visibility of design elements
    }

    public void showTutorial3() {
        //TODO: Update the visibility of design elements
    }

}