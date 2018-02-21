package powerup.systers.com.vocab_match_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import powerup.systers.com.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.ScenarioOverActivity;
import powerup.systers.com.powerup.PowerUpUtils;
import powerup.systers.com.vocab_match_game.VocabMatchSessionManager;
import powerup.systers.com.sink_to_swim_game.SinkToSwimGame;

public class VocabMatchEndActivity extends AppCompatActivity {

    public TextView scoreView, correctView, wrongView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_match_end);
        Intent intent = getIntent();
        int score = intent.getExtras().getInt(PowerUpUtils.SCORE);
        int correctAnswers= score;
        int wrongAnswers= PowerUpUtils.VOCAB_TILES_IMAGES.length - score;
        scoreView = (TextView) findViewById(R.id.vocab_score);
        correctView = (TextView) findViewById(R.id.correct);
        wrongView = (TextView) findViewById(R.id.wrong);
        scoreView.setText(""+score);
        correctView.setText(""+correctAnswers);
        wrongView.setText(""+wrongAnswers);
    }

    public void continuePressed(View view){
        VocabMatchSessionManager session = new VocabMatchSessionManager(this);
        Intent intent = new Intent(VocabMatchEndActivity.this, ScenarioOverActivity.class);
        session.saveVocabMatchOpenedStatus(false);
        finish();
        startActivity(intent);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(VocabMatchEndActivity.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}