/**
 * @desc sets up the map screen.
 */

package powerup.systers.com;

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
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.powerup.PowerUpUtils;

public class MapLevel2Activity extends Activity {

    @BindView(R.id.school_building_level2)
    public ImageView schoolBuilding;
    @BindView(R.id.hospital_building_level2)
    public ImageView hospitalBuilding;
    @BindView(R.id.library_building_level2)
    public ImageView libraryBuilding;
    @BindView(R.id.home_button_level2)
    public Button homeButton;
    @BindView(R.id.school_level2)
    public ImageView school;
    @BindView(R.id.library_level2)
    public ImageView library;
    @BindView(R.id.hospital_level2)
    public ImageView hospital;
    @BindView(R.id.house_level2)
    public ImageView house;

    private DatabaseHandler mDbHandler;
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView scenarioChooser = (ImageView) v;
            if (v.isEnabled()) {
                if (getmDbHandler().setSessionId(getScenarioName(scenarioChooser.getId()))) {
                    startActivityForResult(new Intent(MapLevel2Activity.this, GameLevel2Activity.class), 0);
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                } else {
                    Intent intent = new Intent(MapLevel2Activity.this, ScenarioOverLevel2Activity.class);
                    intent.putExtra(PowerUpUtils.SOURCE, PowerUpUtils.MAP);
                    intent.putExtra(PowerUpUtils.SCENARIO_NAME, getScenarioName(scenarioChooser.getId()));
                    startActivityForResult(intent, 0);
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                }
                finish();
            }
        }
    };

    private String getScenarioName(int id) {
        switch (id) {
            case R.id.school_level2:
                return "School Level 2";
            case R.id.library_level2:
                return "Library Level 2";
            case R.id.house_level2:
                return "Home Level 2";
            case R.id.hospital_level2:
                return "Hospital Level 2";

            default:
                return "Home Level 2";
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.activity_map_level_2);
        ButterKnife.bind(this);

        homeButton.setHeight(homeButton.getWidth());

        school.setOnClickListener(onClickListener);
        house.setOnClickListener(onClickListener);
        hospital.setOnClickListener(onClickListener);
        library.setOnClickListener(onClickListener);

        school.setEnabled(false);
        hospital.setEnabled(false);
        library.setEnabled(false);

        //changes the Map building's greyscale color and locks according to the scenarios completions
        if (getmDbHandler().getScenarioFromID(8).getCompleted() == 1 || SessionHistory.sceneHomeLevel2IsReplayed) {
            schoolBuilding.setImageDrawable(getResources().getDrawable(R.drawable.school_colored));
            school.setEnabled(true);
        }
        if (getmDbHandler().getScenarioFromID(9).getCompleted() == 1 || SessionHistory.sceneSchoolLevel2IsReplayed) {
            hospitalBuilding.setImageDrawable(getResources().getDrawable(R.drawable.hospital_colored));
            hospital.setEnabled(true);
        }
        if (getmDbHandler().getScenarioFromID(10).getCompleted() == 1 || SessionHistory.sceneHospitalLevel2IsReplayed) {
            libraryBuilding.setImageDrawable(getResources().getDrawable(R.drawable.library_colored));
            library.setEnabled(true);
        }

    }

    @OnClick(R.id.store_level2)
    public void clickStoreButton(){
        startActivity(new Intent(MapLevel2Activity.this, StoreActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.home_button_level2)
    public void clickHomeButton(){
        finish();
        startActivity(new Intent(MapLevel2Activity.this, StartActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
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
}
