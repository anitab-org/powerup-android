/**
 * @desc sets up the map screen.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.minesweeper.MinesweeperGameActivity;
import powerup.systers.com.minesweeper.MinesweeperSessionManager;

public class MapActivity extends Activity {

    private DatabaseHandler mDbHandler;
    private OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Button scenarioChooser = (Button) v;
            if (getmDbHandler().setSessionId(scenarioChooser.getText().toString())) {
                startActivityForResult(new Intent(MapActivity.this, GameActivity.class), 0);
            } else if (new MinesweeperSessionManager(MapActivity.this).isMinesweeperOpened()) { //if minesweeper game was left incomplete
                startActivity(new Intent(MapActivity.this, MinesweeperGameActivity.class));
            } else {
                startActivityForResult(new Intent(MapActivity.this, CompletedSceneActivity.class), 0);
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.gamemap);

        Button school = (Button) findViewById(R.id.HouseButton);
        school.setOnClickListener(onClickListener);

        Button home = (Button) findViewById(R.id.BoyfriendButton);
        home.setOnClickListener(onClickListener);

        Button hospital = (Button) findViewById(R.id.HospitalButton);
        hospital.setOnClickListener(onClickListener);

        Button library = (Button) findViewById(R.id.SchoolButton);
        library.setOnClickListener(onClickListener);

        Button storeButton = (Button) findViewById(R.id.storeButton);
        storeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MapActivity.this, DressingRoomActivity.class));
            }
        });

        Button homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
