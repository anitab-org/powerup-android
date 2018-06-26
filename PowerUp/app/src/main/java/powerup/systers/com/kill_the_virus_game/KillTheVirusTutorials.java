package powerup.systers.com.kill_the_virus_game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;

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
    public ImageView imgSyringe;
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
    public int tutorialCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_the_virus_tutorial);
        ButterKnife.bind(this);
        initializeViews();
    }

    @OnClick(R.id.constraint_layout_kill)
    public void clickScreen() {
        if (tutorialCount == 1)
            showTutorial2();
        else if (tutorialCount == 2)
            showTutorial3();
        //else
        //Start the game
    }

    public void initializeViews() {
        //TODO: Initialize the visibility of the tutorials
    }

    public void showTutorial2() {
        //TODO: Update the visibility of elements
    }

    public void showTutorial3() {
        //TODO: Update the visibility of elements
    }
}