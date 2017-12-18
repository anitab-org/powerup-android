package powerup.systers.com.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import powerup.systers.com.R;
import powerup.systers.com.ScenarioOverActivity;
import powerup.systers.com.powerup.PowerUpUtils;

public class ProsAndConsActivity extends AppCompatActivity {

    public int completedRounds;
    public int score;
    public TextView proOne, proTwo, conOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.minesweeper_pros_cons);
        proOne = (TextView) findViewById(R.id.pro_one);
        proTwo = (TextView) findViewById(R.id.pro_two);
        conOne = (TextView) findViewById(R.id.con_one);
        //get the current round from session to fetch it's pros and cons from PowerUtils
        MinesweeperSessionManager sessionManager = new MinesweeperSessionManager(this);
        completedRounds = sessionManager.getCompletedRounds();
        setTexts();
    }

    public void setTexts(){
        proOne.setText(PowerUpUtils.ROUNDS_PROS_CONS[completedRounds - 1][0]);
        proTwo.setText(PowerUpUtils.ROUNDS_PROS_CONS[completedRounds - 1][1]);
        conOne.setText(PowerUpUtils.ROUNDS_PROS_CONS[completedRounds - 1][2]);
    }

    /**
     * @desc called when current round of minesweeper game is completed
     * @param v view object of continue button
     */
    public void continuePressedProsAndCons(View v) {
        if (completedRounds < PowerUpUtils.NUMBER_OF_ROUNDS) { //calls next round if true
            startActivity(new Intent(ProsAndConsActivity.this, MinesweeperGameActivity.class).putExtra(PowerUpUtils.CALLED_BY, false));
        } else {
            new MinesweeperSessionManager(this).saveMinesweeperOpenedStatus(false); //marks minesweeper game as finished
            Intent intent = new Intent(ProsAndConsActivity.this, ScenarioOverActivity.class);
            intent.putExtra(String.valueOf(R.string.scene), PowerUpUtils.MINESWEEP_PREVIOUS_SCENARIO);
            startActivity(intent);
        }
    }

}
