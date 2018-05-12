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
        preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        hasPreviouslyStarted = preferences.getBoolean(getString(R.string.preferences_has_previously_started), false);
        hasPreviouslyCustomized = preferences.getBoolean(getString(R.string.preferences_has_previously_customized), false);
        newUserButton = (Button) findViewById(R.id.newUserButtonFirstPage);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPreviouslyStarted) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
                    builder.setTitle(context.getResources().getString(R.string.start_title_message))
                            .setMessage(getResources().getString(R.string.start_dialog_message));
                    builder.setPositiveButton(getString(R.string.start_confirm_message), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new MinesweeperSessionManager(StartActivity.this)
                                .saveMinesweeperOpenedStatus(false);
                            new SinkToSwimSessionManager(StartActivity.this)
                                .saveSinkToSwimOpenedStatus(false);
                            new VocabMatchSessionManager(StartActivity.this)
                                .saveVocabMatchOpenedStatus(false);
                            startActivityForResult(new Intent(StartActivity.this, AvatarRoomActivity.class), 0);
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
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
                } else{
                    Intent intent = new Intent(getApplicationContext(),AvatarRoomActivity.class);
                    startActivity(intent);
                }
                SessionHistory.hasPreviouslyCustomized = false;
            }
        });

        startButton = (Button) findViewById(R.id.startButtonMain);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasPreviouslyStarted) {
                    startActivity(new Intent(StartActivity.this, MapActivity.class));
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                } else {
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

        aboutButton = (Button) findViewById(R.id.aboutButtonMain);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, AboutActivity.class));
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
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            super.onBackPressed();
            return;
        }
        this.backAlreadyPressed = true;
        Toast.makeText(this, getResources().getString(R.string.toast_confirm_exit_message), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backAlreadyPressed=false;
            }
        }, 2000);
    }
}
