package powerup.systers.com.memory_match_game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;

public class MemoryMatchGameActivity extends Activity {

    @BindView(R.id.img_tile_1)
    public ImageView imgTile1;
    @BindView(R.id.img_tile_2)
    public ImageView imgTile2;
    @BindView(R.id.img_tile_3)
    public ImageView imgTile3;
    @BindView(R.id.txt_score_memory)
    public TextView txtScore;
    @BindView(R.id.txt_time_memory)
    public TextView txtTime;
    public int[] arrayTile1, arrayTile2, arrayTile3;
    public int roundCount = 0, correctAnswer = 0, wrongAnswer = 0, totalScore = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_match_game);
        ButterKnife.bind(this);
    }

    public void initializeView() {
        //TODO: Set the initial state of Views
    }

    @OnClick(R.id.btn_start)
    public void clickStart() {
        //TODO: Change the views according to first round
    }

    @OnClick(R.id.btn_yes)
    public void clickYes() {
        //TODO: Check if the answers are correct
    }

    @OnClick(R.id.btn_no)
    public void clickNo() {
        //TODO: Check if the answers are wrong
    }

    public void updateViews() {
        //TODO: Animate and Update the views after each iteration
    }

    public void correctAnswer() {
        //TODO: Update the value of score and number of correct answers if the answer was correct
    }

    public void wrongAnswer() {
        //TODO: Update the value of number of wrong answers
    }

    public void gameEnd() {
        //TODO: Store the total points in preferences and end the game
    }
}