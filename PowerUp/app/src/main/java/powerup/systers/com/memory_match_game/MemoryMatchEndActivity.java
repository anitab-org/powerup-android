package powerup.systers.com.memory_match_game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import powerup.systers.com.R;

public class MemoryMatchEndActivity extends Activity {

    @BindView(R.id.txt_memory_score)
    public TextView txtScore;
    @BindView(R.id.txt_memory_correct)
    public TextView txtMemoryCorrect;
    @BindView(R.id.txt_memory_wrong)
    public TextView txtMemoryWrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_match_end);
        ButterKnife.bind(this);
    }
}