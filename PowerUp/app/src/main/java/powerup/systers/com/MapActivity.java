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

import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.minesweeper.MinesweeperGameActivity;
import powerup.systers.com.minesweeper.MinesweeperSessionManager;
import powerup.systers.com.powerup.PowerUpUtils;

public class MapActivity extends Activity {

    private DatabaseHandler mDbHandler;
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView scenarioChooser = (ImageView) v;
            if (v.isEnabled()){
            if (getmDbHandler().setSessionId(getScenarioName(scenarioChooser.getId()))) {
                startActivityForResult(new Intent(MapActivity.this, GameActivity.class), 0);
            } else if (new MinesweeperSessionManager(MapActivity.this).isMinesweeperOpened()) { //if minesweeper game was left incomplete
                startActivity(new Intent(MapActivity.this, MinesweeperGameActivity.class));
            } else {
                Intent intent = new Intent(MapActivity.this, ScenarioOverActivity.class);
                intent.putExtra(PowerUpUtils.SOURCE,PowerUpUtils.MAP);
                startActivityForResult(intent, 0);
            }
            finish();
        }}
    };

    private String getScenarioName(int id) {
        switch (id){
            case R.id.school : return "School";
            case R.id.library : return "Library";
            case R.id.house : return "Home";
            case R.id.hospital : return "Hospital";

            default: return "Home";
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.gamemap);

        ImageView schoolBuilding = (ImageView) findViewById(R.id.school_building);
        ImageView hospitalBuilding = (ImageView) findViewById(R.id.hospital_building);
        ImageView libraryBuilding = (ImageView) findViewById(R.id.library_building);

        Button homeButton = (Button) findViewById(R.id.home_button);
        homeButton.setHeight(homeButton.getWidth());

        ImageView school = (ImageView) findViewById(R.id.school);
        school.setOnClickListener(onClickListener);

        ImageView house = (ImageView) findViewById(R.id.house);
        house.setOnClickListener(onClickListener);

        ImageView hospital = (ImageView)  findViewById(R.id.hospital);
        hospital.setOnClickListener(onClickListener);

        ImageView library = (ImageView) findViewById(R.id.library);
        library.setOnClickListener(onClickListener);

        school.setEnabled(false);
        hospital.setEnabled(false);
        library.setEnabled(false);

        Button storeButton = (Button) findViewById(R.id.store);
        storeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, SelectFeaturesActivity.class));
            }
        });

        homeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MapActivity.this,StartActivity.class));
            }
        });

        //changes the Map building's greyscale color and locks according to the scenarios completions
        if (getmDbHandler().getScenarioFromID(4).getCompleted() == 1){
            schoolBuilding.setImageDrawable(getResources().getDrawable(R.drawable.school_colored));
            school.setEnabled(true);
        }
        if (getmDbHandler().getScenarioFromID(5).getCompleted() == 1){
            hospitalBuilding.setImageDrawable(getResources().getDrawable(R.drawable.hospital_colored));
            hospital.setEnabled(true);
        }
        if (getmDbHandler().getScenarioFromID(6).getCompleted() == 1){
            libraryBuilding.setImageDrawable(getResources().getDrawable(R.drawable.library_colored));
            library.setEnabled(true);
        }

    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
