/**
 * @desc presents the user with a dialogue scenario and updates the scenario
 * with more questions and answers as needed. Also updates power/health bars.
 */

package powerup.systers.com;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import java.util.ArrayList;
import java.util.List;
import powerup.systers.com.datamodel.Answer;
import powerup.systers.com.datamodel.Question;
import powerup.systers.com.datamodel.Scenario;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;

@SuppressLint("NewApi")
public class GameActivity extends Activity {

    public static Activity gameActivityInstance;
    private DatabaseHandler mDbHandler;
    private List<Answer> answers;
    private Scenario scene;
    private Scenario prevScene;
    private TextView questionTextView;
    private TextView scenarioNameTextView;
    private Button replay;
    private Button goToMap;
    private ArrayAdapter<String> listAdapter;
    private static boolean isStateChanged = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            isStateChanged = true;
        }
        super.onCreate(savedInstanceState);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.game_activity);
        gameActivityInstance = this;
        // Find the ListView resource.
        ListView mainListView = (ListView) findViewById(R.id.mainListView);
        questionTextView = (TextView) findViewById(R.id.questionView);
        scenarioNameTextView = (TextView) findViewById(R.id.scenarioNameEditText);
        listAdapter = new ArrayAdapter<>(this, R.layout.simplerow,
                new ArrayList<String>());
        answers = new ArrayList<>();
        goToMap = (Button) findViewById(R.id.continueButtonGoesToMap);
        replay = (Button) findViewById(R.id.redoButton);
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
        updateScenario();
        if (scene.getReplayed() == 1) {
            goToMap.setAlpha((float) 0.0);
            replay.setAlpha((float) 0.0);
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
                        } else {
                            SessionHistory.currSessionID = scene
                                    .getNextScenarioID();
                            if (SessionHistory.currSessionID == -1) {
                                // Check to make sure all scenes are completed
                                SessionHistory.currSessionID = 1;
                            }
                            updatePoints(position);
                            getmDbHandler().setCompletedScenario(
                                    scene.getId());
                            SessionHistory.currScenePoints = 0;
                            updateScenario();
                        }
                    }
                });

        IconRoundCornerProgressBar powerBarHealing = (IconRoundCornerProgressBar) findViewById(R.id.powerbarHealing);
        powerBarHealing.setIconImageResource(R.drawable.icon_healing);
        powerBarHealing.setIconBackgroundColor(R.color.powerup_purple_light);
        powerBarHealing.setProgress(mDbHandler.getHealing());

        IconRoundCornerProgressBar powerbarInvisibility = (IconRoundCornerProgressBar) findViewById(R.id.powerbarInvisibility);
        powerbarInvisibility.setIconImageResource(R.drawable.icon_invisibility);
        powerbarInvisibility.setProgress(mDbHandler.getInvisibility());

        IconRoundCornerProgressBar powerbarStrength = (IconRoundCornerProgressBar) findViewById(R.id.powerbarStrength);
        powerbarStrength.setIconImageResource(R.drawable.icon_strength);
        powerbarStrength.setProgress(mDbHandler.getStrength());

        IconRoundCornerProgressBar powerbarTelepathy = (IconRoundCornerProgressBar) findViewById(R.id.powerbarTelepathy);
        powerbarTelepathy.setIconImageResource(R.drawable.icon_telepathy);
        powerbarTelepathy.setProgress(mDbHandler.getTelepathy());
    }

    /**
     * Add karma points to the session.
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
     */
    private void updateScenario() {
        if (ScenarioOverActivity.scenarioActivityDone == 1)
            ScenarioOverActivity.scenarioOverActivityInstance.finish();
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
                    replay.setAlpha((float) 0.0);
                }
            });
            // Replay Mechanics
            replay.setAlpha((float) 1.0);
            replay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // In case the user moves back to map in between a running
                    // Scenario.

                    SessionHistory.totalPoints -= SessionHistory.currScenePoints;
                    replay.setClickable(false);
                    Intent intent = new Intent(GameActivity.this, GameActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intent, 0);
                    getmDbHandler()
                            .setReplayedScenario(scene.getScenarioName());
                    goToMap.setAlpha((float) 0.0);
                    replay.setAlpha((float) 0.0);
                }
            });
        }
        // If completed check if it is last scene
        if (prevScene != null && prevScene.getCompleted() == 1) {
            if (scene.getNextScenarioID() == -1) {
                Intent intent = new Intent(GameActivity.this, GameOverActivity.class);
                finish();
                startActivityForResult(intent, 0);
            } else {
                SessionHistory.currSessionID = scene.getNextScenarioID();
                Intent intent = new Intent(GameActivity.this, ScenarioOverActivity.class);
                intent.putExtra(String.valueOf(R.string.scene), prevScene.getScenarioName());
                startActivity(intent);
            }
        }
        if (isStateChanged == false){
            SessionHistory.currQID = scene.getFirstQuestionID();
        } else {
            isStateChanged = false;
        }
        scenarioNameTextView.setText(scene.getScenarioName());
        updateQA();
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
