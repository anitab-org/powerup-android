/**
 * @desc brings user to map if previous session is being opened. Otherwise,
 * a new user will be brought to the “Avatar Room” to customize avatar
 * upon starting the app.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity {

    private SharedPreferences preferences;
    private boolean hasPreviouslyStarted;
    private Button startButton;
    private Button newUserButton;
    private Button aboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        hasPreviouslyStarted = preferences.getBoolean(getString(R.string.preferences_has_previously_started), false);
        newUserButton = (Button) findViewById(R.id.newUserButtonFirstPage);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StartActivity.this, AvatarRoomActivity.class), 0);
            }
        });

        startButton = (Button) findViewById(R.id.startButtonMain);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPreviouslyStarted) {
                    startActivity(new Intent(StartActivity.this, MapActivity.class));
                } else {
                    startActivity(new Intent(StartActivity.this, AvatarRoomActivity.class));
                }

            }
        });

        aboutButton = (Button) findViewById(R.id.aboutButtonMain);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, AboutActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        hasPreviouslyStarted = preferences.getBoolean(getString(R.string.preferences_has_previously_started), false);
        if (hasPreviouslyStarted) {
            startButton.setText(getString(R.string.resume_text));
        }
    }
}
