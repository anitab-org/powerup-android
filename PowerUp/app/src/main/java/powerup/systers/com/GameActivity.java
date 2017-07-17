/**
 * @desc presents the user with a dialogue scenario and updates the scenario
 * with more questions and answers as needed. Also updates power/health bars.
 */

package powerup.systers.com;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;

import java.util.ArrayList;
import java.util.List;

import powerup.systers.com.datamodel.Answer;
import powerup.systers.com.datamodel.Question;
import powerup.systers.com.datamodel.Scenario;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.minesweeper.MinesweeperGameActivity;
import powerup.systers.com.minesweeper.MinesweeperSessionManager;
import powerup.systers.com.powerup.PowerUpUtils;
import powerup.systers.com.sink_to_swim_game.SinkToSwimGame;
import powerup.systers.com.sink_to_swim_game.SinkToSwimTutorials;

@SuppressLint("NewApi")
public class GameActivity extends Activity {

    public Activity gameActivityInstance;
    private DatabaseHandler mDbHandler;
    private List<Answer> answers;
    private Scenario scene;
    private Scenario prevScene;
    private TextView questionTextView;
    private TextView scenarioNameTextView;
    private Button goToMap;
    private ArrayAdapter<String> listAdapter;
    private static boolean isStateChanged = false;

    public GameActivity() {
        gameActivityInstance = this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (new MinesweeperSessionManager(this).isMinesweeperOpened()) {
            startActivity(new Intent(GameActivity.this, MinesweeperGameActivity.class));
        }
        if (savedInstanceState != null) {
            isStateChanged = true;
        }
        super.onCreate(savedInstanceState);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.game_activity);

        // Find the ListView resource.
        ListView mainListView = (ListView) findViewById(R.id.mainListView);
        questionTextView = (TextView) findViewById(R.id.questionView);
        scenarioNameTextView = (TextView) findViewById(R.id.scenarioNameEditText);
        listAdapter = new ArrayAdapter<>(this, R.layout.simplerow, new ArrayList<String>());
        answers = new ArrayList<>();
        scene = getmDbHandler().getScenario();
        findViewById(R.id.root).setBackground(getResources().getDrawable(PowerUpUtils.SCENARIO_BACKGROUNDS[scene.getId()-1]));
        goToMap = (Button) findViewById(R.id.continueButtonGoesToMap);
        SessionHistory.currScenePoints = 0;
        ImageView eyeImageView = (ImageView) findViewById(R.id.eyeImageView);
        ImageView faceImageView = (ImageView) findViewById(R.id.faceImageView);
        ImageView hairImageView = (ImageView) findViewById(R.id.hairImageView);
        ImageView clothImageView = (ImageView) findViewById(R.id.clothImageView);

        String eyeImageName = getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            eyeImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String faceImageName = getResources().getString(R.string.face);
        faceImageName = faceImageName + getmDbHandler().getAvatarFace();
        try {
            photoNameField = ourRID.getClass().getField(faceImageName);
            faceImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String clothImageName = getResources().getString(R.string.cloth);
        clothImageName = clothImageName + getmDbHandler().getAvatarCloth();
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            clothImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String hairImageName = getResources().getString(R.string.hair);
        hairImageName = hairImageName + getmDbHandler().getAvatarHair();
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            hairImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        // Update Scene
        updateScenario(0);
        updateQA();
        if (scene.getReplayed() == 1) {
            goToMap.setAlpha((float) 0.0);
        }
        // Set the ArrayAdapter as the ListView's adapter.
        mainListView.setAdapter(listAdapter);
        mainListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        if (answers.get(position).getNextQuestionID() > 0) {
                            // Next Question
                            SessionHistory.currQID = answers.get(position)
                                    .getNextQuestionID();
                            updatePoints(position);
                            updateQA();
                        } else if (answers.get(position).getNextQuestionID() == -1) {
                            updatePoints(position);
                            getmDbHandler().setCompletedScenario(scene.getId());
                            updateScenario(-1);
                        } else if (answers.get(position).getNextQuestionID() == -2) {
                            updatePoints(position);
                            getmDbHandler().setCompletedScenario(scene.getId());
                            updateScenario(-2);
                        } else {
                            if (SessionHistory.currSessionID == -1) {
                                // Check to make sure all scenes are completed
                                SessionHistory.currSessionID = 1;
                            }
                            updatePoints(position);
                            getmDbHandler().setCompletedScenario(scene.getId());
                            updateScenario(0);
                        }
                    }
                });
    }

    /**
     * Add karma points to the session.
     *
     * @param position the current question user is on
     */
    private void updatePoints(int position) {
        // Update the Scene Points
        SessionHistory.currScenePoints += answers.get(position).getPoints();
        // Update Total Points
        SessionHistory.totalPoints += answers.get(position).getPoints();
    }

    /**
     * Finish, replay, or go to another scenario as needed. Updates the
     * question and answer if the last scenario has not yet been reached.
     * @param type coding scheme for .csv files, -1 means minesweeper game, 0 means scenario completion
     */
    private void updateScenario(int type) {
        if (ScenarioOverActivity.scenarioActivityDone == 1)
            new ScenarioOverActivity().scenarioOverActivityInstance.finish();
        if (scene != null)
            prevScene = getmDbHandler().getScenarioFromID(scene.getId());
        scene = getmDbHandler().getScenario();
        // Replay a scenario
        if (scene.getReplayed() == 0) {
            // goToMap Mechanics
            goToMap.setAlpha((float) 1.0);
            goToMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Incase the user move back to map in between a running
                    // Scenario.
                    SessionHistory.totalPoints -= SessionHistory.currScenePoints;
                    goToMap.setClickable(false);
                    Intent intent = new Intent(GameActivity.this, MapActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 0);
                    getmDbHandler()
                            .setReplayedScenario(scene.getScenarioName());
                    goToMap.setAlpha((float) 0.0);
                }
            });
        }
        SessionHistory.currQID = scene.getFirstQuestionID();
        scenarioNameTextView.setText(scene.getScenarioName());
        // If completed check if it is last scene
        if (prevScene != null && prevScene.getCompleted() == 1) {
                SessionHistory.prevSessionID = scene.getId();
                SessionHistory.currSessionID = scene.getNextScenarioID();
                if (type == 0) {
                    Intent intent = new Intent(GameActivity.this, ScenarioOverActivity.class);
                    intent.putExtra(String.valueOf(R.string.scene), prevScene.getScenarioName());
                    startActivity(intent);
                } else if (type == -1) {
                    new MinesweeperSessionManager(this).saveMinesweeperOpenedStatus(true); //marks minesweeper game as opened and incompleted
                    startActivity(new Intent(GameActivity.this, MinesweeperGameActivity.class).putExtra(PowerUpUtils.CALLED_BY, true));
                } else if (type == -2) {
                startActivity(new Intent(GameActivity.this, SinkToSwimTutorials.class));
            }
        }

    }

    /**
     * Replace the current scenario with another question/answer.
     */
    private void updateQA() {
        listAdapter.clear();
        getmDbHandler().getAllAnswer(answers, SessionHistory.currQID);
        for (Answer ans : answers) {
            listAdapter.add(ans.getAnswerDescription());
        }
        Question questions = getmDbHandler().getCurrentQuestion();
        questionTextView.setText(questions.getQuestionDescription());
    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
