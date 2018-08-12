
package powerup.systers.com.save_the_blood_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.ui.scenario_over_screen_level2.ScenarioOverLevel2Activity;
import powerup.systers.com.utils.PowerUpUtils;

public class SaveTheBloodEndActivity extends Activity {

    @BindView(R.id.txt_save_blood_score)
    public TextView txtScore;
    @BindView(R.id.txt_save_blood_correct)
    public TextView txtCorrect;
    @BindView(R.id.txt_save_blood_wrong)
    public TextView txtWrong;
    public int score, correct, wrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_blood_end);
        ButterKnife.bind(this);
        getValues();
        updateViews();
    }

    @OnClick(R.id.btn_continue_save_blood)
    public void clickContinue(){
        new SaveTheBloodSessionManager(this).saveSaveBloodOpenedStatus(false);
        Intent intent = new Intent(SaveTheBloodEndActivity.this, ScenarioOverLevel2Activity.class);
        intent.putExtra(PowerUpUtils.IS_FINAL_SCENARIO_EXTRA, true);
        startActivity(intent);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    public void updateViews(){
        txtScore.setText(""+score);
        txtCorrect.setText(""+correct);
        txtWrong.setText(""+wrong);
    }

    public void getValues(){
        score = getIntent().getIntExtra("score", 0);
        correct = getIntent().getIntExtra("correct",0);
        wrong = getIntent().getIntExtra("wrong", 0);
    }
}