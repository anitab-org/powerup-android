package powerup.systers.com.sink_to_swim_game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.utils.PowerUpUtils;

public class SinkToSwimTutorials extends AppCompatActivity {

    ImageView tutorialView;
    int curTutorialImage;
    ImageView startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sink_to_swim_tutorials);
        tutorialView = (ImageView) findViewById(R.id.tut);
        startButton = (ImageView) findViewById(R.id.start_button);
        startButton.setEnabled(false);
        curTutorialImage = 1;
        tutorialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curTutorialImage == PowerUpUtils.SWIM_TUTS.length){
                    //do nothing as start button has to be chosen
                } else if (curTutorialImage == PowerUpUtils.SWIM_TUTS.length-1){
                    tutorialView.setImageDrawable(getResources().getDrawable(PowerUpUtils.SWIM_TUTS[curTutorialImage]));
                    curTutorialImage++;
                    startButton.setVisibility(View.VISIBLE);
                    startButton.setEnabled(true);
                } else {
                    tutorialView.setImageDrawable(getResources().getDrawable(PowerUpUtils.SWIM_TUTS[curTutorialImage]));
                    curTutorialImage++;
                }
            }
        });
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.isEnabled()){
                    Intent intent = new Intent(SinkToSwimTutorials.this,SinkToSwimGame.class)
                            .putExtra(PowerUpUtils.CALLED_BY, true);
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                }
            }
        });
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(SinkToSwimTutorials.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
