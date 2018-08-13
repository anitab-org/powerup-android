package powerup.systers.com.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import powerup.systers.com.R;

public class AboutActivity extends Activity {

    private boolean isAboutGameOpen = false;
    private boolean isAboutUrgencyOpen = false;
    private boolean isAboutHelpingOpen = false;

    @BindView(R.id.about_the_game)
    public TextView aboutGameSection;
    @BindView(R.id.about_the_urgency)
    public TextView aboutUrgencySection;
    @BindView(R.id.about_helping_by)
    public TextView aboutHelpingSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        if (isAboutGameOpen){
           aboutGameSection.setVisibility(View.VISIBLE);
        }
        if (isAboutUrgencyOpen){
            aboutUrgencySection.setVisibility(View.VISIBLE);
        }
        if (isAboutHelpingOpen){
            aboutHelpingSection.setVisibility(View.VISIBLE);
        }

    }

    public void aboutGamePressed(View view){
        if (aboutGameSection.getVisibility() == View.GONE){
            aboutGameSection.setVisibility(View.VISIBLE);
            isAboutGameOpen = true;
        } else {
            aboutGameSection.setVisibility(View.GONE);
            isAboutGameOpen = false;
        }
    }

    public void aboutUrgencyPressed(View view){
        if (aboutUrgencySection.getVisibility() == View.GONE){
            aboutUrgencySection.setVisibility(View.VISIBLE);
            isAboutUrgencyOpen = true;
        } else {
            aboutUrgencySection.setVisibility(View.GONE);
            isAboutUrgencyOpen = false;
        }
    }

    public void aboutHelpingByPressed(View view){
        if (aboutHelpingSection.getVisibility() == View.GONE){
            aboutHelpingSection.setVisibility(View.VISIBLE);
            isAboutHelpingOpen = true;
        } else {
            aboutHelpingSection.setVisibility(View.GONE);
            isAboutHelpingOpen = false;
        }
    }

    public void pressHomeButton(View view){
        finish();
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }
}
