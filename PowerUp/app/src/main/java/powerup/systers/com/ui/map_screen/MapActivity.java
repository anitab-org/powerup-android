/**
 * @desc sets up the map screen.
 */

package powerup.systers.com.ui.map_screen;

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
import powerup.systers.com.ui.game_activity.GameActivity;
import powerup.systers.com.R;
import powerup.systers.com.ui.scenario_over_screen.ScenarioOverActivity;
import powerup.systers.com.ui.StartActivity;
import powerup.systers.com.ui.store_screen.StoreActivity;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.minesweeper.MinesweeperGameActivity;
import powerup.systers.com.minesweeper.MinesweeperSessionManager;
import powerup.systers.com.utils.InjectionClass;
import powerup.systers.com.sink_to_swim_game.SinkToSwimGame;
import powerup.systers.com.sink_to_swim_game.SinkToSwimSessionManager;
import powerup.systers.com.utils.PowerUpUtils;
import powerup.systers.com.vocab_match_game.VocabMatchGameActivity;
import powerup.systers.com.vocab_match_game.VocabMatchSessionManager;

public class MapActivity extends Activity implements MapContract.IMapView{

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

        // initialize presneter
        MapPresenter presenter = new MapPresenter(dataSource, this);

        //changes the Map building's greyscale color and locks according to the scenarios completions
        presenter.checkCompletion();
    }

    // open StoreActivity on store click
    @OnClick(R.id.store)
    public void storeButtonListener(View view) {
        startActivity(new Intent(MapActivity.this, StoreActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    // home button click listener, open startActivity
    @OnClick(R.id.home_button)
    public void homeButtonListener(View view) {
        finish();
        startActivity(new Intent(MapActivity.this, StartActivity.class));
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
        startActivity(new Intent(MapActivity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
                // checks whether has been already completed & then open GameActivity if setSessionId returns true
                dataSource.setSessionId(getScenarioName(scenarioChooser.getId()), new IDataSource.LoadBooleanCallback() {
                    @Override
                    public void onResultLoaded(boolean value) {
                        if (value) {
                            startActivityForResult(new Intent(MapActivity.this, GameActivity.class), 0);
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                        } else if (new MinesweeperSessionManager(MapActivity.this).isMinesweeperOpened()) { //if minesweeper game was left incomplete
                            startActivity(new Intent(MapActivity.this, MinesweeperGameActivity.class));
                            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                        } else if (new SinkToSwimSessionManager(MapActivity.this).isSinkToSwimOpened()) {
                            startActivity(new Intent(MapActivity.this, SinkToSwimGame.class));
                        } else if (new VocabMatchSessionManager(MapActivity.this).isVocabMatchOpened()) {
                            startActivity(new Intent(MapActivity.this, VocabMatchGameActivity.class));
                        } else {
                            Intent intent = new Intent(MapActivity.this, ScenarioOverActivity.class);
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
                return "School";
            case R.id.library:
                return "Library";
            case R.id.house:
                return "Home";
            case R.id.hospital:
                return "Hospital";

            default:
                return "Home";
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
