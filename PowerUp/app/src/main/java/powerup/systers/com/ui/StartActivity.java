/**
 * @desc brings user to map if previous session is being opened. Otherwise,
 * a new user will be brought to the “Avatar Room” to customize avatar
 * upon starting the app.
 */

package powerup.systers.com.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.minesweeper.MinesweeperSessionManager;
import powerup.systers.com.sink_to_swim_game.SinkToSwimSessionManager;
import powerup.systers.com.ui.avatar_room.AvatarRoomActivity;
import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.ui.map_screen_level2.MapLevel2Activity;
import powerup.systers.com.utils.InjectionClass;
import powerup.systers.com.vocab_match_game.VocabMatchSessionManager;

public class StartActivity extends Activity {

    @BindView(R.id.startButtonMain)
    public Button startButton;

    private boolean hasPreviouslyStarted;
    private Context context;
    private DataSource dataSource;
    private boolean backAlreadyPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = StartActivity.this;
        ButterKnife.bind(this);

        // instantiating datasource
        dataSource = InjectionClass.provideDataSource(context);

        //checking whether it's first time run of application or not
        hasPreviouslyStarted = dataSource.checkFirstTime();

        // populate database if it's first run of application
        if (hasPreviouslyStarted) {
            populateDatabase();
            dataSource.setFirstTime(false);
        }
    }

    // starts the about activity
    @OnClick(R.id.aboutButtonMain)
    public void aboutButtonListener(View view) {
        startActivity(new Intent(context, AboutActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.newUserButtonFirstPage)
    public void newUserButtonListener(View view) {
        if (dataSource.checkPreviouslyCustomized()) {

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
            //delete avatar data
            dataSource.setCurrentAccessoriesValue(0);
            dataSource.setCurrentClothValue(1);
            dataSource.setCurrentHairValue(1);
            dataSource.setCurrentSkinValue(1);
            dataSource.setCurrentEyeValue(1);
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
            Intent intent = new Intent(context, AvatarRoomActivity.class);
            startActivity(intent);
        }

        // setting previously customized avatar to false
        SessionHistory.hasPreviouslyCustomized = false;
    }

    @OnClick(R.id.startButtonMain)
    public void startButtonListener(View view) {
        // load map activity if shared preferences return true
        if (dataSource.checkPreviouslyCustomized()) {
            //load Level 2 if Level 2 is complete
            if (SessionHistory.level1Completed)
                startActivity(new Intent(context, MapLevel2Activity.class));
            else
                startActivity(new Intent(context, MapActivity.class));
            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        } else {
            // create dialog to use new game button instead & dismiss dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
            builder.setTitle(context.getResources().getString(R.string.start_title_message_load))
                    .setMessage(getResources().getString(R.string.start_dialog_message_load));
            builder.setPositiveButton(getString(R.string.start_confirm_message_load),
                    new DialogInterface.OnClickListener() {
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

    private void populateDatabase() {
        dataSource.readAndInsertCSVData();
        dataSource.insertPointsAndAvatarData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hasPreviouslyStarted = dataSource.checkFirstTime();
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
                backAlreadyPressed = false;
            }
        }, 2000);
    }
}
