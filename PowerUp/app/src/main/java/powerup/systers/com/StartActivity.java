/**
 * @desc brings user to map if previous session is being opened. Otherwise,
 * a new user will be brought to the “Avatar Room” to customize avatar
 * upon starting the app.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    private SharedPreferences preferences;
    private boolean hasPreviouslyStarted;
    private boolean hasPreviouslyCustomized;
    private Button startButton;
    private Button newUserButton;
    private Button aboutButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = StartActivity.this;
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        hasPreviouslyStarted = preferences.getBoolean(getString(R.string.preferences_has_previously_started), false);
        hasPreviouslyCustomized = preferences.getBoolean(getString(R.string.preferences_has_previously_customized), false);
        newUserButton = (Button) findViewById(R.id.newUserButtonFirstPage);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                builder.setTitle(context.getResources().getString(R.string.start_title_message))
                        .setMessage(getResources().getString(R.string.start_dialog_message));
                builder.setPositiveButton(getString(R.string.start_confirm_message), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean(getString(R.string.preferences_has_previously_customized), Boolean.FALSE);
                        edit.apply();
                        startActivityForResult(new Intent(StartActivity.this, AvatarRoomActivity.class), 0);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                ColorDrawable drawable = new ColorDrawable(Color.WHITE);
                drawable.setAlpha(200);
                dialog.getWindow().setBackgroundDrawable(drawable);
                dialog.show();
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
