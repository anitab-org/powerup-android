package powerup.systers.com;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import powerup.systers.com.datamodel.Answer;
import powerup.systers.com.datamodel.Question;
import powerup.systers.com.datamodel.Scenario;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;

@SuppressLint("NewApi")
public class Game extends Activity {

	private DatabaseHandler mDbHandler;
	private List<Answer> answers;
	private Scenario scene;
	private TextView questionTextView;
	private TextView scenarioNameTextView;
	private Button replay;
	private Button goToMap;
	private ArrayAdapter<String> listAdapter;
	private ImageView eyeImageView;
	private ImageView hairImageView;
	private ImageView clothImageView;
	private ImageView faceImageView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setmDbHandler(new DatabaseHandler(this));
		getmDbHandler().open();
		setContentView(R.layout.pgame);
		// Find the ListView resource.
		ListView mainListView = (ListView) findViewById(R.id.mainListView);
		questionTextView = (TextView) findViewById(R.id.questionView);
		scenarioNameTextView = (TextView) findViewById(R.id.scenarioNameEditText);
		listAdapter = new ArrayAdapter<>(this, R.layout.simplerow,
				new ArrayList<String>());
		answers = new ArrayList<>();
		goToMap = (Button) findViewById(R.id.continueButtonGoesToMap);
		replay = (Button) findViewById(R.id.redoButton);

		eyeImageView = (ImageView) findViewById(R.id.eyeImageView);
		faceImageView = (ImageView) findViewById(R.id.faceImageView);
		hairImageView = (ImageView) findViewById(R.id.hairImageView);
		clothImageView = (ImageView) findViewById(R.id.clothImageView);

		String eyeImageName = "eye";
		eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();;
		R.drawable ourRID = new R.drawable();
		java.lang.reflect.Field photoNameField;
		try {
			photoNameField = ourRID.getClass().getField(eyeImageName);
			eyeImageView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String faceImageName = "face";
		faceImageName = faceImageName + getmDbHandler().getAvatarFace();;
		try {
			photoNameField = ourRID.getClass().getField(faceImageName);
			faceImageView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String clothImageName = "cloth";
		clothImageName = clothImageName + getmDbHandler().getAvatarCloth();;
		try {
			photoNameField = ourRID.getClass().getField(clothImageName);
			clothImageView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String hairImageName = "hair";
		hairImageName = hairImageName + getmDbHandler().getAvatarHair();;
		try {
			photoNameField = ourRID.getClass().getField(hairImageName);
			hairImageView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
									scene.getScenarioName());
							SessionHistory.currScenePoints = 0;
							updateScenario();
						}
					}
				});
	}

	private void updatePoints(int position) {
		// Update the Scene Points
		SessionHistory.currScenePoints += answers.get(position).getPoints();
		// Update Total Points
		SessionHistory.totalPoints += answers.get(position).getPoints();
	}

	private void updateScenario() {
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
					Intent myIntent = new Intent(Game.this, MapActivity.class);
					startActivityForResult(myIntent, 0);
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
					// Incase the user move back to map in between a running
					// Scenario.

					SessionHistory.totalPoints -= SessionHistory.currScenePoints;
					replay.setClickable(false);
					Intent myIntent = new Intent(Game.this, Game.class);
					startActivityForResult(myIntent, 0);
					getmDbHandler()
							.setReplayedScenario(scene.getScenarioName());
					goToMap.setAlpha((float) 0.0);
					replay.setAlpha((float) 0.0);
				}
			});
		}
		// If completed check if it is last scene
		if (scene.getCompleted() == 1) {
			if (scene.getNextScenarioID() == -1) {
				Intent myIntent = new Intent(Game.this, GameOver.class);
				startActivityForResult(myIntent, 0);
			} else {
				SessionHistory.currSessionID = scene.getNextScenarioID();
				updateScenario();
			}
		}
		SessionHistory.currQID = scene.getFirstQuestionID();
		scenarioNameTextView.setText(scene.getScenarioName());
		updateQA();
	}

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