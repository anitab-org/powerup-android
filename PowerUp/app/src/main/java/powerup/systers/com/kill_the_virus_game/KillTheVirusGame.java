package powerup.systers.com.kill_the_virus_game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;

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
    @BindView(R.id.img_syringe)
    public ImageView imgSyringe;
    @BindView(R.id.txt_time)
    public TextView txtTime;
    @BindView(R.id.txt_score)
    public TextView txtScore;
    @BindView(R.id.constraint_layout_kill)
    public ConstraintLayout constraintLayout;
    public int score = 0;
    public boolean correctHit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_the_virus_game);
        ButterKnife.bind(this);
    }

    @OnClick (R.id.constraint_layout_kill)
    public void clickScreen(){
        //TODO: Linear Animation of the syringe
        //TODO: Check for right/wrong hit
    }

    public void initialSetup(){
        //TODO: Initialize the views at the start
    }

    public void rightHit(){
        //TODO: Increase the value of score by one
        //TODO: The Virus should disappear
    }

    public void wrongHit(){
        //TODO: end the game
    }

    public int increasePoint(int currentScore){
        return currentScore +1;
    }

    public void gameEnd(){
        //TODO: Update the total points
        //TODO: Migrate to game completion screen
    }
}
