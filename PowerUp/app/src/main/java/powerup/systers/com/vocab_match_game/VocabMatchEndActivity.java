package powerup.systers.com.vocab_match_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import powerup.systers.com.GameActivity;
import powerup.systers.com.R;
import powerup.systers.com.powerup.PowerUpUtils;

public class VocabMatchEndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_match_end);
        Intent intent = getIntent();
        int score = intent.getExtras().getInt(PowerUpUtils.SCORE);
        int correctAnswers= score;
        int wrongAnswers= PowerUpUtils.VOCAB_TILES_IMAGES.length - score;
        TextView scoreView = (TextView) findViewById(R.id.vocab_score);
        TextView  correctView = (TextView) findViewById(R.id.correct);
        TextView  wrongView = (TextView) findViewById(R.id.wrong);
        scoreView.setText(""+score);
        correctView.setText(""+correctAnswers);
        wrongView.setText(""+wrongAnswers);
    }

    public void continuePressed(View view){
        Intent intent = new Intent(VocabMatchEndActivity.this, GameActivity.class);
        finish();
        startActivity(intent);
    }
}
