/**
 * @desc finishes dialogue situation when the “continue” or “back” button is pressed.
 * Brings user to ending screen displaying current progress of power/health
 * bars and karma points.
 */

package powerup.systers.com.ui.scenario_over_screen;

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

import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.entities.Scenario;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.ui.Level2TransitionActivity;
import powerup.systers.com.ui.game_activity.GameActivity;
import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.utils.InjectionClass;
import powerup.systers.com.utils.PowerUpUtils;


public class ScenarioOverActivity extends AppCompatActivity implements ScenarioOverContract.IScenarioOverView{

    public Activity scenarioOverActivityInstance;
    public static int scenarioActivityDone;
    private DataSource dataSource;
    public Scenario scene, prevScene;

    //Todo check preferences and shift it to pref file
    private final String PREF_NAME_SCENARIO = "SCENARIO_OVER_DIALOG";
    private final int PRIVATE_MODE_SCENARIO = 0;
    private final String GAME_OPENED_SCENARIO = "IS_GAME_REPLAYED";
    private SharedPreferences sharedPreferences_scenario;
    private SharedPreferences.Editor editor_scenario;
    private ScenarioOverPresenter presenter;
    public ScenarioOverActivity() {
        scenarioOverActivityInstance = this;
    }
    public ScenarioOverActivity(Context context){
        sharedPreferences_scenario = context.getSharedPreferences(PREF_NAME_SCENARIO, PRIVATE_MODE_SCENARIO);
        editor_scenario = sharedPreferences_scenario.edit();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = InjectionClass.provideDataSource(scenarioOverActivityInstance);
        setContentView(R.layout.activity_scenario_over);
        ButterKnife.bind(this);
        presenter = new ScenarioOverPresenter(this, dataSource);

        init();

        scenarioActivityDone = 1;
        //If not launched from map then only dialogMaker() is called
        if(!new ScenarioOverActivity(this).isActivityOpened() && (!(getIntent().getExtras()!=null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE))))){
            dialogMaker();
        }

        // init view of continue button and karma points
        ImageView continueButton = findViewById(R.id.continueButton);
        TextView karmaPoints = findViewById(R.id.karmaPoints);

        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GameActivity().gameActivityInstance.finish();
                if (getIntent().getBooleanExtra(PowerUpUtils.IS_FINAL_SCENARIO_EXTRA, false)) {
                    SessionHistory.level1Completed = true;
                    startActivity(new Intent(ScenarioOverActivity.this, Level2TransitionActivity.class));
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

    }

    private void init() {
        presenter.loadScenario();
    }

    // open MapActivity on map button click
    @OnClick(R.id.mapButton)
    public void mapButtonListener (View view) {
        finish();
        startActivity(new Intent(ScenarioOverActivity.this, MapActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.replayButton)
    public void replayButtonListener (View view) {
        if(getIntent().getExtras() !=null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE)) && getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME) != null) {
            String scenario = getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME);
            int id;
            switch (scenario) {
                case "Home":
                    id = 4;
                    SessionHistory.sceneHomeIsReplayed = true;
                    break;
                case "School":
                    id = 5;
                    SessionHistory.sceneSchoolIsReplayed = true;
                    break;
                case "Hospital":
                    id = 6;
                    SessionHistory.sceneHospitalIsReplayed = true;
                    break;
                case "Library":
                    id = 7;
                    SessionHistory.sceneLibraryIsReplayed = true;
                    break;
                default:
                    id = 4;
                    break;
            }
            SessionHistory.currSessionID = id;
        } else {
            SessionHistory.currSessionID = SessionHistory.prevSessionID;
            scenarioActivityDone = 0;
        }                //Check that reducing points does not lead to negetive value
        if(SessionHistory.totalPoints - SessionHistory.currScenePoints >= 0)
            SessionHistory.totalPoints -= SessionHistory.currScenePoints;
        SessionHistory.currScenePoints = 0;
        scenarioActivityDone = 0;
        dataSource.resetCompleted(SessionHistory.currSessionID);
        dataSource.resetReplayed(SessionHistory.currSessionID);
        scenarioOverActivityInstance.finish();
        startActivity(new Intent(ScenarioOverActivity.this, GameActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
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

    @Override
    public void setCurrentScenario(Scenario scenario) {
        scene = scenario;
        if(scene != null)
            SessionHistory.currQID = scene.getFirstQuestionID();
    }

    @Override
    public void setPrevScenario(Scenario scenario) {
        prevScene = scenario;
        //Initializing and setting Text for currentScenarioName
        final TextView currentScenarioName = findViewById(R.id.currentScenarioName);
        // set scenario name
        if(getIntent().getExtras() !=null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE)) && getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME) != null)
            currentScenarioName.setText(getResources().getString(R.string.current_scenario_name,getIntent().getStringExtra(PowerUpUtils.SCENARIO_NAME)));
        else
            currentScenarioName.setText(getResources().getString(R.string.current_scenario_name,prevScene.getScenarioName()));

    }
}
