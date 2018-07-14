/**
 * @desc finishes the game and shows a screen telling the user that the game
 * has completed.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import powerup.systers.com.datamodel.SessionHistory;

public class GameOverActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_game);
        Button backToMap = (Button) findViewById(R.id.ContinueButtonMap);
        backToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this,
                        MapLevel2Activity.class);
                finish();
                startActivityForResult(intent, 0);
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });
        TextView karmaPoints = (TextView) findViewById(R.id.karmaPoints);
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(GameOverActivity.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
