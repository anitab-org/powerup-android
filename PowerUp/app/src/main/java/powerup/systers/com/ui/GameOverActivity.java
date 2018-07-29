/**
 * @desc finishes the game and shows a screen telling the user that the game
 * has completed.
 */

package powerup.systers.com.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.ui.map_screen_level2.MapLevel2Activity;

public class GameOverActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_game);

        //set the total points from sessionHistory
        TextView karmaPoints = findViewById(R.id.karmaPoints);
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
    }

    // backToMap starts MapActivity
    @OnClick(R.id.ContinueButtonMap)
    public void backToMapListener(View view) {
        Intent intent = new Intent(GameOverActivity.this,
                MapLevel2Activity.class);
        finish();
        startActivityForResult(intent, 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
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
