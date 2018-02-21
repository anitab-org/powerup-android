package powerup.systers.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {

    private boolean isAboutGameOpen = false;
    private boolean isAboutUrgencyOpen = false;
    private boolean isAboutHelpingOpen = false;
    private static String isGameOpen = "ABOUT_GAME_OPEN";
    private static String isUrgencyOpen = "ABOUT_URGENCY_OPEN";
    private static String isHelpingOpen = "ABOUT_HELPING_OPEN";
    private TextView aboutGameSection, aboutUrgencySection, aboutHelpingSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if (savedInstanceState != null){
            isAboutGameOpen = savedInstanceState.getBoolean(isGameOpen);
            isAboutUrgencyOpen = savedInstanceState.getBoolean(isUrgencyOpen);
            isAboutHelpingOpen = savedInstanceState.getBoolean(isHelpingOpen);
        }
        aboutGameSection = (TextView) findViewById(R.id.about_the_game);
        aboutUrgencySection = (TextView) findViewById(R.id.about_the_urgency);
        aboutHelpingSection = (TextView) findViewById(R.id.about_helping_by);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(isGameOpen,isAboutGameOpen);
        outState.putBoolean(isHelpingOpen,isAboutHelpingOpen);
        outState.putBoolean(isUrgencyOpen,isAboutUrgencyOpen);
    }
}
