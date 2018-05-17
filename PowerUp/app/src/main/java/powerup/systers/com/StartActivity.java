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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.minesweeper.MinesweeperSessionManager;
import powerup.systers.com.sink_to_swim_game.SinkToSwimSessionManager;
import powerup.systers.com.vocab_match_game.VocabMatchSessionManager;

public class StartActivity extends Activity {

    private SharedPreferences preferences;
    private boolean hasPreviouslyStarted;
    private boolean hasPreviouslyCustomized;
    private Button startButton;
    private Button newUserButton;
    private Button aboutButton;
    Context context;
    boolean backAlreadyPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = StartActivity.this;

        //checking whether it's first time run of application or not
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        hasPreviouslyStarted = preferences.getBoolean(getString(R.string.preferences_has_previously_started), false);
        hasPreviouslyCustomized = preferences.getBoolean(getString(R.string.preferences_has_previously_customized), false);

        // onclick for New Game button
        newUserButton = (Button) findViewById(R.id.newUserButtonFirstPage);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPreviouslyStarted) {

                    // create a dialog to confirm new game action before losing saved data
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getResources().getString(R.string.start_title_message))
                            .setMessage(getResources().getString(R.string.start_dialog_message));

                    // deleting all saved data on confirm click & start AvatarRoom activity
                    builder.setPositiveButton(getString(R.string.start_confirm_message), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new MinesweeperSessionManager(context)
                                .saveMinesweeperOpenedStatus(false);
                            new SinkToSwimSessionManager(context)
                                .saveSinkToSwimOpenedStatus(false);
                            new VocabMatchSessionManager(context)
                                .saveVocabMatchOpenedStatus(false);
                            startActivityForResult(new Intent(context, AvatarRoomActivity.class), 0);
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                        }
                    });
                    // dismiss the dialog on cancel click
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

                    // customizing dialog with colordrawable & display dialog
                    AlertDialog dialog = builder.create();
                    ColorDrawable drawable = new ColorDrawable(Color.WHITE);
                    drawable.setAlpha(200);
                    dialog.getWindow().setBackgroundDrawable(drawable);
                    dialog.show();

                } else {
                    Intent intent = new Intent(context,AvatarRoomActivity.class);
                    startActivity(intent);
                }

                // setting previously customized avatar to false
                SessionHistory.hasPreviouslyCustomized = false;
            }
        });

        // load game button initialized
        startButton = (Button) findViewById(R.id.startButtonMain);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPreviouslyStarted) {
                    // load map activity if shared preferences return true
                    startActivity(new Intent(context, MapActivity.class));
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                } else {
                    // create dialog to use new game button instead & dismiss dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                    builder.setTitle(context.getResources().getString(R.string.start_title_message_load))
                            .setMessage(getResources().getString(R.string.start_dialog_message_load));
                    builder.setPositiveButton(getString(R.string.start_confirm_message_load), new DialogInterface.OnClickListener() {
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

            }
        });

        // starts the about activity
        aboutButton = (Button) findViewById(R.id.aboutButtonMain);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, AboutActivity.class));
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
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
    @Override
    public void onBackPressed() {

        if (backAlreadyPressed) {
            // close the application
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            super.onBackPressed();
            return;
        }
        // first back press should set the variable to true & show a Toast to press again to close application
        this.backAlreadyPressed = true;
        Toast.makeText(this, getResources().getString(R.string.toast_confirm_exit_message), Toast.LENGTH_SHORT).show();
        // set the variable to false if it takes more than 2sec for another back press
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backAlreadyPressed=false;
            }
        }, 2000);
    }
}
