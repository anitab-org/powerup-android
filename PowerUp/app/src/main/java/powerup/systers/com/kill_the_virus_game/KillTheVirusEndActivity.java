package powerup.systers.com.kill_the_virus_game;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;

public class KillTheVirusEndActivity extends Activity {

    @BindView(R.id.txt_score)
    public TextView txtScoreEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kill_the_virus_end);
        ButterKnife.bind(this);
        //TODO: Update the score text view to display scored points
    }

    @OnClick
    public void clickContinue() {
        //TODO: Migrate to scene completion screen
    }
}