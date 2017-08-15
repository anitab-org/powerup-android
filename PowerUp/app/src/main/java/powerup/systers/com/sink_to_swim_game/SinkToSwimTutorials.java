package powerup.systers.com.sink_to_swim_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import powerup.systers.com.R;
import powerup.systers.com.powerup.PowerUpUtils;

public class SinkToSwimTutorials extends AppCompatActivity {

    ImageView tutorialView;
    int curTutorialImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sink_to_swim_tutorials);
        tutorialView = (ImageView) findViewById(R.id.tut);
        curTutorialImage = 1;
        tutorialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curTutorialImage == PowerUpUtils.SWIM_TUTS.length){
                    Intent intent = new Intent(SinkToSwimTutorials.this,SinkToSwimGame.class);
                    finish();
                    startActivity(intent);
                }else {
                    tutorialView.setImageDrawable(getResources().getDrawable(PowerUpUtils.SWIM_TUTS[curTutorialImage]));
                    curTutorialImage++;
                }
            }
        });
    }
}
