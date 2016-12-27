package powerup.systers.com;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void aboutGamePressed(View view){
        TextView section = (TextView) findViewById(R.id.about_the_game);
        if(section.getVisibility() == View.GONE){
            section.setVisibility(View.VISIBLE);
        } else {
            section.setVisibility(View.GONE);
        }
    }

    public void aboutUrgencyPressed(View view){
        TextView section = (TextView) findViewById(R.id.about_the_urgency);
        if(section.getVisibility() == View.GONE){
            section.setVisibility(View.VISIBLE);
        } else {
            section.setVisibility(View.GONE);
        }
    }

    public void aboutHelpingByPressed(View view){
        TextView section = (TextView) findViewById(R.id.about_helping_by);
        if(section.getVisibility() == View.GONE){
            section.setVisibility(View.VISIBLE);
        } else {
            section.setVisibility(View.GONE);
        }
    }

    public void pressHomeButton(View view){
        finish();
    }
}
