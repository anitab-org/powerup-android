/**
 * @desc finishes dialogue situation when the “continue” or “back” button is pressed.
 * Brings user to ending screen displaying current progress of power/health
 * bars and karma points.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import powerup.systers.com.datamodel.Scenario;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.powerup.PowerUpUtils;

public class ScenarioOverActivity extends AppCompatActivity {

    public static int scenarioActivityDone;
    public Activity scenarioOverActivityInstance;
    public Scenario scene;
    private DatabaseHandler mDbHandler;

    public ScenarioOverActivity() {
        scenarioOverActivityInstance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.activity_scenario_over);
        scene = getmDbHandler().getScenario();
        scenarioActivityDone = 1;
        ImageView replayButton = (ImageView) findViewById(R.id.replayButton);
        ImageView continueButton = (ImageView) findViewById(R.id.continueButton);
        Button mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ScenarioOverActivity.this, MapActivity.class));
            }
        });

        TextView karmaPoints = (TextView) findViewById(R.id.karmaPoints);

        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishCurrentSituation();
                startActivity(new Intent(ScenarioOverActivity.this, GameActivity.class));
            }
        });
        if (isRedirectedFromMap()) {
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
                finishCurrentSituation();
                scenarioOverActivityInstance.finish();
                startActivity(new Intent(ScenarioOverActivity.this, GameActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishCurrentSituation();
    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }

    private boolean isRedirectedFromMap() {
        return getIntent().getExtras() != null && PowerUpUtils.MAP.equals(getIntent().getExtras().getString(PowerUpUtils.SOURCE));
    }

    private void finishCurrentSituation() {
        new GameActivity().gameActivityInstance.finish();
    }
}
