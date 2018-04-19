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

import powerup.systers.com.datamodel.Scenario;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.powerup.PowerUpUtils;


public class ScenarioOverActivity extends AppCompatActivity {

    public Activity scenarioOverActivityInstance;
    public static int scenarioActivityDone;
    private DatabaseHandler mDbHandler;
    public Scenario scene;
    private final String PREF_NAME_SCENARIO = "SCENARIO_OVER_DIALOG";
    private final int PRIVATE_MODE_SCENARIO = 0;
    private final String GAME_OPENED_SCENARIO = "IS_GAME_REPLAYED";
    SharedPreferences sharedPreferences_scenario;
    Context context_scenario;
    SharedPreferences.Editor editor_scenario;

    public ScenarioOverActivity() {
        scenarioOverActivityInstance = this;
    }
    public ScenarioOverActivity(Context context){
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
        scene = getmDbHandler().getScenario();
        Scenario prevScene = getmDbHandler().getScenarioFromID(SessionHistory.prevSessionID); //Fetching Scenario
        scenarioActivityDone = 1;
        if(!new ScenarioOverActivity(this).isActivityOpened()){
            dialogMaker();
        }
        ImageView replayButton = (ImageView) findViewById(R.id.replayButton);
        ImageView continueButton = (ImageView) findViewById(R.id.continueButton);
        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ScenarioOverActivity.this, MapActivity.class));
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });

        //Initializing and setting Text for currentScenarioName
        TextView currentScenarioName = (TextView) findViewById(R.id.currentScenarioName);
        currentScenarioName.setText(getResources().
                getString(R.string.current_scenario_name,prevScene.getScenarioName()));
        TextView karmaPoints = (TextView) findViewById(R.id.karmaPoints);
        
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameActivity().gameActivityInstance.finish();
                if (getIntent().getBooleanExtra(PowerUpUtils.IS_FINAL_SCENARIO_EXTRA, false)) {
                    startActivity(new Intent(ScenarioOverActivity.this, GameOverActivity.class));
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                } else {
                    startActivity(new Intent(ScenarioOverActivity.this, GameActivity.class));
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                }
            }
        });
        if (getIntent().getExtras()!=null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE))){
            continueButton.setVisibility(View.GONE);
            continueButton.setOnClickListener(null);
        }


        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionHistory.currSessionID = SessionHistory.prevSessionID;
                SessionHistory.totalPoints -= SessionHistory.currScenePoints;
                SessionHistory.currScenePoints = 0;
                scenarioActivityDone = 0;
                DatabaseHandler dbHandler = new DatabaseHandler(ScenarioOverActivity.this);
                dbHandler.resetCompleted(SessionHistory.currSessionID);
                dbHandler.resetReplayed(SessionHistory.currSessionID);
                scenarioOverActivityInstance.finish();
                startActivity(new Intent(ScenarioOverActivity.this, GameActivity.class));
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(ScenarioOverActivity.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    public void dialogMaker() {
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.43);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ScenarioOverActivity.this);
        builder.setMessage("Message");
        builder.setPositiveButton((getString(R.string.scenario_over_confirm_message)), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss(); }
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
