package powerup.systers.com.kill_the_virus_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.ui.StartActivity;
import powerup.systers.com.ui.scenario_over_screen_level2.ScenarioOverLevel2Activity;

public class KillTheVirusEndActivity extends Activity {

    @BindView(R.id.txt_score)
    public TextView txtScoreEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_the_virus_end);
        ButterKnife.bind(this);
        updateScore();
    }

    @OnClick(R.id.btn_continue_memory)
    public void clickContinue() {
        new KillTheVirusSessionManager(this).saveKillTheVirusOpenedStatus(false);
        startActivity(new Intent(KillTheVirusEndActivity.this, ScenarioOverLevel2Activity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    private void updateScore() {
        //getting the value of score from intent extra
        int score = getIntent().getIntExtra(getString(R.string.score_kill_the_virus), -1);
        txtScoreEnd.setText("" + score);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(KillTheVirusEndActivity.this, StartActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }
}