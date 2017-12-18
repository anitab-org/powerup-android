package powerup.systers.com.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import powerup.systers.com.R;
import powerup.systers.com.powerup.PowerUpUtils;

public class MinesweeperTutorials extends AppCompatActivity {

    ImageView tutorialView;
    int curTutorialImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper_tutorials);
        tutorialView = (ImageView) findViewById(R.id.tut);
        curTutorialImage = 1;
        tutorialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curTutorialImage == PowerUpUtils.MINES_TUTS.length){
                    Intent intent = new Intent(MinesweeperTutorials.this,MinesweeperGameActivity.class).putExtra(PowerUpUtils.CALLED_BY, true);
                    finish();
                    startActivity(intent);
                }else {
                    tutorialView.setImageDrawable(getResources().getDrawable(PowerUpUtils.MINES_TUTS[curTutorialImage]));
                    curTutorialImage++;
                }
            }
        });
    }
    }

