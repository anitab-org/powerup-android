package powerup.systers.com.memory_match_game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.ui.scenario_over_screen.ScenarioOverActivity;


public class MemoryMatchEndActivity extends Activity {

    @BindView(R.id.txt_memory_score)
    public TextView txtScore;
    @BindView(R.id.txt_memory_correct)
    public TextView txtMemoryCorrect;
    @BindView(R.id.txt_memory_wrong)
    public TextView txtMemoryWrong;
    public int score, correct, wrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_match_end);
        ButterKnife.bind(this);
        getValues();
        updateViews();
    }

    @OnClick(R.id.continue_btn_memory)
    public void clickContinue(){
        new MemoryMatchSessionManager(this).saveMemoryMatchOpenedStatus(false);
        startActivity(new Intent(MemoryMatchEndActivity.this, ScenarioOverActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    public void updateViews(){
        txtScore.setText(""+score);
        txtMemoryCorrect.setText(""+correct);
        txtMemoryWrong.setText(""+wrong);
    }

    public void getValues(){
        score = getIntent().getIntExtra("score",0);
        correct = getIntent().getIntExtra("correct",0);
        wrong = getIntent().getIntExtra("wrong",0);
    }
}