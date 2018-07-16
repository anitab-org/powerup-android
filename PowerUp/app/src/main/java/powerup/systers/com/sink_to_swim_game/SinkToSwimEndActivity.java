package powerup.systers.com.sink_to_swim_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.ui.scenario_over_screen.ScenarioOverActivity;
import powerup.systers.com.utils.PowerUpUtils;

public class SinkToSwimEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sink_to_swim_end);
        Intent intent = getIntent();
        int wrongAnswers= intent.getExtras().getInt(PowerUpUtils.WRONG_ANSWER);
        int correctAnswers= intent.getExtras().getInt(PowerUpUtils.CORRECT_ANSWERS);
        int score = intent.getExtras().getInt(PowerUpUtils.SCORE);
        TextView  scoreView = (TextView) findViewById(R.id.swim_score);
        TextView  correctView = (TextView) findViewById(R.id.correct);
        TextView  wrongView = (TextView) findViewById(R.id.wrong);
        scoreView.setText(""+score);
        correctView.setText(""+correctAnswers);
        wrongView.setText(""+wrongAnswers);
    }

    public void continuePressed(View view){
        SinkToSwimSessionManager session = new SinkToSwimSessionManager(this);
        session.saveSinkToSwimOpenedStatus(false);
        Intent intent = new Intent(SinkToSwimEndActivity.this, ScenarioOverActivity.class);
        intent.putExtra(PowerUpUtils.IS_FINAL_SCENARIO_EXTRA, true);
        finish();
        startActivityForResult(intent, 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(SinkToSwimEndActivity.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}