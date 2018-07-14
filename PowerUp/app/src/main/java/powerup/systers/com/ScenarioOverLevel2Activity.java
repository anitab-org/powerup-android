/**
 * @desc finishes dialogue situation when the “continue” or “back” button is pressed.
 * Brings user to ending screen displaying current progress of power/health
 * bars and karma points.
 */

package powerup.systers.com;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.datamodel.Scenario;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.powerup.PowerUpUtils;


public class ScenarioOverLevel2Activity extends AppCompatActivity {

    public static int scenarioActivityDone;
    private final String PREF_NAME_SCENARIO = "SCENARIO_OVER_DIALOG";
    private final int PRIVATE_MODE_SCENARIO = 0;
    private final String GAME_OPENED_SCENARIO = "IS_GAME_REPLAYED";
    public Activity scenarioOverActivityInstance;
    public Scenario scene;
    public SharedPreferences sharedPreferences_scenario;
    public Context context_scenario;
    public SharedPreferences.Editor editor_scenario;
    @BindView(R.id.continueButton)
    public ImageView continueButton;
    @BindView(R.id.currentScenarioName)
    public TextView currentScenarioName;
    @BindView(R.id.karmaPoints)
    public TextView karmaPoints;
    private DatabaseHandler mDbHandler;

    public ScenarioOverLevel2Activity() {
        scenarioOverActivityInstance = this;
    }

    public ScenarioOverLevel2Activity(Context context) {
        this.context_scenario = context;
        sharedPreferences_scenario = context.getSharedPreferences(PREF_NAME_SCENARIO, PRIVATE_MODE_SCENARIO);
        editor_scenario = sharedPreferences_scenario.edit();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.activity_scenario_over);
        ButterKnife.bind(this);
        scene = getmDbHandler().getScenario();
        final Scenario prevScene = getmDbHandler().getScenarioFromID(SessionHistory.prevSessionID); //Fetching Scenario
        scenarioActivityDone = 1;
        //If not launched from map then only dialogMaker() is called
        if (!new ScenarioOverLevel2Activity(this).isActivityOpened() && (!(getIntent().getExtras() != null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE))))) {
            dialogMaker();
        }

        //Initializing and setting Text for currentScenarioName
        if (getIntent().getExtras() != null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE)) && getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME) != null)
            currentScenarioName.setText(getResources().getString(R.string.current_scenario_name, getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME)));
        else
            currentScenarioName.setText(getResources().getString(R.string.current_scenario_name, prevScene.getScenarioName()));

        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));

        if (getIntent().getExtras() != null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE))) {
            continueButton.setVisibility(View.GONE);
            continueButton.setOnClickListener(null);
        }
    }

    @OnClick(R.id.mapButton)
    public void clickMapButton(){
        finish();
        startActivity(new Intent(ScenarioOverLevel2Activity.this, MapLevel2Activity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.continueButton)
    public void clickContinueButton(){
        new GameLevel2Activity().gameActivityInstance.finish();
        if (getIntent().getBooleanExtra(PowerUpUtils.IS_FINAL_SCENARIO_EXTRA, false)) {
            startActivity(new Intent(ScenarioOverLevel2Activity.this, GameOverActivity.class));
            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        } else {
            startActivity(new Intent(ScenarioOverLevel2Activity.this, GameLevel2Activity.class));
            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        }
    }

    @OnClick(R.id.replayButton)
    public void clickReplayButton(){
        if (getIntent().getExtras() != null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE)) && getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME) != null) {
            String scenario = getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME);
            int id;
            switch (scenario) {
                case "Home Level 2":
                    id = 8;
                    SessionHistory.sceneHomeLevel2IsReplayed = true;
                    break;
                case "School Level 2":
                    id = 9;
                    SessionHistory.sceneSchoolLevel2IsReplayed = true;
                    break;
                case "Hospital Level 2":
                    id = 10;
                    SessionHistory.sceneHospitalLevel2IsReplayed = true;
                    break;
                case "Library Level 2":
                    id = 11;
                    SessionHistory.sceneLibraryLevel2IsReplayed = true;
                    break;
                default:
                    id = 8;
                    break;
            }
            SessionHistory.currSessionID = id;
        } else {
            SessionHistory.currSessionID = SessionHistory.prevSessionID;
            scenarioActivityDone = 0;
        }                //Check that reducing points does not lead to negetive value
        if (SessionHistory.totalPoints - SessionHistory.currScenePoints >= 0)
            SessionHistory.totalPoints -= SessionHistory.currScenePoints;
        SessionHistory.currScenePoints = 0;
        scenarioActivityDone = 0;
        DatabaseHandler dbHandler = new DatabaseHandler(ScenarioOverLevel2Activity.this);
        dbHandler.resetCompleted(SessionHistory.currSessionID);
        dbHandler.resetReplayed(SessionHistory.currSessionID);
        scenarioOverActivityInstance.finish();
        startActivity(new Intent(ScenarioOverLevel2Activity.this, GameLevel2Activity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed() {
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(ScenarioOverLevel2Activity.this, MapLevel2Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void dialogMaker() {
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.43);
        String scenario_over_dialog_message = getResources().getString(R.string.scenario_over_dialog_message1);
        String scenario_over_dialog_message2 = getResources().getString(R.string.scenario_over_dialog_message2);
        scenario_over_dialog_message += SessionHistory.currScenePoints + scenario_over_dialog_message2;
        TextView message2 = new TextView(this);
        message2.setGravity(Gravity.CENTER);
        message2.setText(getResources().getString(R.string.scenario_over_title_message));
        message2.setTextSize(20);
        message2.setTypeface(message2.getTypeface(), Typeface.BOLD);
        message2.setPadding(0, 20, 0, 0);
        message2.setTextColor(getResources().getColor(R.color.powerup_black));
        AlertDialog.Builder builder = new AlertDialog.Builder(ScenarioOverLevel2Activity.this);
        builder.setMessage("Message");
        builder.setPositiveButton((getString(R.string.scenario_over_confirm_message)), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.setCustomTitle(message2);
        ColorDrawable drawable = new ColorDrawable(Color.WHITE);
        drawable.setAlpha(200);
        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);   //Setting custom dimens
            window.setBackgroundDrawable(drawable);
        }
        final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        LinearLayout parent = (LinearLayout) positiveButton.getParent();
        View leftSpacer = parent.getChildAt(1);
        TextView message1 = (TextView) dialog.findViewById(android.R.id.message);
        message1.setText(scenario_over_dialog_message);
        message1.setGravity(Gravity.CENTER);
        positiveButton.setTextSize(15);
        positiveButton.setClickable(true);
        parent.setGravity(Gravity.CENTER_HORIZONTAL);
        leftSpacer.setVisibility(View.GONE);
    }

    public boolean isActivityOpened() {
        return sharedPreferences_scenario.getBoolean(GAME_OPENED_SCENARIO, false);
    }

    public void saveActivityOpenedStatus(boolean isOpened) {
        editor_scenario.putBoolean(GAME_OPENED_SCENARIO, isOpened);
        editor_scenario.clear();
        editor_scenario.commit();
    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}