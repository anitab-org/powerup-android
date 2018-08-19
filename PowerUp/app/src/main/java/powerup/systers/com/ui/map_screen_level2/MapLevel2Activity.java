/**
 * @desc sets up the map screen.
 */

package powerup.systers.com.ui.map_screen_level2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.kill_the_virus_game.KillTheVirusGame;
import powerup.systers.com.kill_the_virus_game.KillTheVirusSessionManager;
import powerup.systers.com.save_the_blood_game.SaveTheBloodGameActivity;
import powerup.systers.com.save_the_blood_game.SaveTheBloodSessionManager;
import powerup.systers.com.R;
import powerup.systers.com.ui.game_activity_level2.GameLevel2Activity;
import powerup.systers.com.ui.StartActivity;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.ui.scenario_over_screen_level2.ScenarioOverLevel2Activity;
import powerup.systers.com.ui.store_screen_level2.StoreLevel2Activity;
import powerup.systers.com.utils.InjectionClass;
import powerup.systers.com.utils.PowerUpUtils;

public class MapLevel2Activity extends Activity implements MapLevel2Contract.IMapLevel2View{

    private DataSource dataSource;
    @BindView(R.id.school_building)
    public ImageView schoolBuilding;
    @BindView(R.id.hospital_building)
    public ImageView hospitalBuilding;
    @BindView(R.id.library_building)
    public ImageView libraryBuilding;
    @BindView(R.id.school)
    public ImageView school;
    @BindView(R.id.house)
    public ImageView house;
    @BindView(R.id.hospital)
    public ImageView hospital;
    @BindView(R.id.library)
    public ImageView library;
    @BindView(R.id.home_button)
    public Button homeButton;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // making windows full screen & hiding title bar of activity screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.gamemap);
        ButterKnife.bind(this);

        // inject the database
        dataSource = InjectionClass.provideDataSource(this);

        //setup for activity
        init();

        // initialize presenter
        MapLevel2Presenter presenter = new MapLevel2Presenter(dataSource, this);

        //changes the Map building's greyscale color and locks according to the scenarios completions
        presenter.checkCompletion();
    }

    // open StoreLevel2Activity on store click
    @OnClick(R.id.store)
    public void storeButtonListener(View view) {
        startActivity(new Intent(MapLevel2Activity.this, StoreLevel2Activity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    // home button click listener, open startActivity
    @OnClick(R.id.home_button)
    public void homeButtonListener(View view) {
        finish();
        startActivity(new Intent(MapLevel2Activity.this, StartActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    private void init() {
        school.setOnClickListener(onClickListener);
        house.setOnClickListener(onClickListener);
        hospital.setOnClickListener(onClickListener);
        library.setOnClickListener(onClickListener);

        //disable onclick for imageview
        school.setEnabled(false);
        hospital.setEnabled(false);
        library.setEnabled(false);

        homeButton.setHeight(homeButton.getWidth());
    }

    /**
     * Goes back to the start menu when user presses back button
     */
    @Override
    public void onBackPressed() {
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(MapLevel2Activity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataSource.clearInstance();
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            final ImageView scenarioChooser = (ImageView) v;
            if (v.isEnabled()) {
                // checks whether has been already completed & then open GameLevel2Activity if setSessionId returns true
                dataSource.setSessionId(getScenarioName(scenarioChooser.getId()), new IDataSource.LoadBooleanCallback() {
                    @Override
                    public void onResultLoaded(boolean value) {
                        if (value) {
                            startActivityForResult(new Intent(MapLevel2Activity.this, GameLevel2Activity.class), 0);
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                        } else if(new KillTheVirusSessionManager(MapLevel2Activity.this).isKillTheVirusOpened()){
                            startActivity(new Intent(MapLevel2Activity.this, KillTheVirusGame.class));
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                        } else if(new SaveTheBloodSessionManager(MapLevel2Activity.this).isSaveBloodOpened()){
                            startActivity(new Intent(MapLevel2Activity.this, SaveTheBloodGameActivity.class));
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                        } else {
                            Intent intent = new Intent(MapLevel2Activity.this, ScenarioOverLevel2Activity.class);
                            intent.putExtra(PowerUpUtils.SOURCE, PowerUpUtils.MAP);
                            intent.putExtra(PowerUpUtils.SCENARIO_NAME, getScenarioName(scenarioChooser.getId()));
                            startActivityForResult(intent, 0);
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                        }
                    }
                });
                finish();
            }
        }
    };

    private String getScenarioName(int id) {
        switch (id) {
            case R.id.school:
                return "School Level 2";
            case R.id.library:
                return "Library Level 2";
            case R.id.house:
                return "Home Level 2";
            case R.id.hospital:
                return "Hospital Level 2";
            default:
                return "Home Level 2";
        }
    }

    @Override
    public void setSchool() {
        schoolBuilding.setImageDrawable(getResources().getDrawable(R.drawable.school_colored));
        school.setEnabled(true);
    }

    @Override
    public void setHospital() {
        hospitalBuilding.setImageDrawable(getResources().getDrawable(R.drawable.hospital_colored));
        hospital.setEnabled(true);
    }

    @Override
    public void setLibrary() {
        libraryBuilding.setImageDrawable(getResources().getDrawable(R.drawable.library_colored));
        library.setEnabled(true);
    }
}
