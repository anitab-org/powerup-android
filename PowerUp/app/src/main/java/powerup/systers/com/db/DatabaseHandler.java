package powerup.systers.com.db;


import android.content.Context;
import android.database.Cursor;

import java.util.List;

import powerup.systers.com.datamodel.Answer;
import powerup.systers.com.datamodel.Question;
import powerup.systers.com.datamodel.Scenario;
import powerup.systers.com.datamodel.SessionHistory;

public class DatabaseHandler extends AbstractDbAdapter {

	public DatabaseHandler(Context ctx) {
		super(ctx);
		ctx.getAssets();
	}

	public boolean gameOver() {
		String selectQuery = "SELECT  * FROM Scenario WHERE Completed = 0";
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			return false;
		}
		cursor.close();
		return true;
	}

	public void getAllAnswer(List<Answer> answers, Integer qId) {
		String selectQuery = "SELECT  * FROM Answer WHERE QID = " + qId;
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		answers.clear();
		if (cursor.moveToFirst()) {
			do {
				Answer ans = new Answer();
				ans.setAnswerID(cursor.getInt(0));
				ans.setQuestionID(cursor.getInt(1));
				ans.setAnswerDescription(cursor.getString(2));
				ans.setNextQuestionID(cursor.getInt(3));
				ans.setPoints(cursor.getInt(4));
				answers.add(ans);
			} while (cursor.moveToNext());
		}
		cursor.close();
	}

	public Question getCurrentQuestion() {
		String selectQuery = "SELECT  * FROM Question WHERE QID = "
				+ SessionHistory.currQID;
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			Question question = new Question();
			question.setQuestionID(cursor.getInt(0));
			question.setScenarioID(cursor.getInt(1));
			question.setQuestionDescription(cursor.getString(2));
			return question;
		}
		cursor.close();
		return null;
	}

	public Scenario getScenario() {
		String selectQuery = "SELECT  * FROM Scenario WHERE ID = "
				+ SessionHistory.currSessionID;
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			Scenario scene = new Scenario();
			scene.setId(cursor.getInt(0));
			scene.setScenarioName(cursor.getString(1));
			scene.setTimestamp(cursor.getString(2));
			scene.setAsker(cursor.getString(3));
			scene.setAvatar(cursor.getInt(4));
			scene.setFirstQuestionID(cursor.getInt(5));
			scene.setCompleted(cursor.getInt(6));
			scene.setNextScenarioID(cursor.getInt(7));
			scene.setReplayed(cursor.getInt(8));
			return scene;
		}
		cursor.close();
		return null;
	}

	public boolean setSessionId(CharSequence ScenarioName) {
		String selectQuery = "SELECT  * FROM Scenario WHERE ScenarioName = "
				+ "\"" + ScenarioName + "\"";
		Cursor cursor = mDb.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			// If the scene is already completed
			if (cursor.getInt(6) == 1) {
				return false;
			}
			SessionHistory.currSessionID = cursor.getInt(0);
			SessionHistory.currQID = cursor.getInt(5);
			return true;
		}
		cursor.close();
		// Scenario not Found
		return false;
	}

	public void setCompletedScenario(CharSequence ScenarioName) {
		String updateQuery = "UPDATE  Scenario SET Completed=1 WHERE"
				+ " ScenarioName = " + "\"" + ScenarioName + "\"";
		mDb.execSQL(updateQuery);
	}

	public void setReplayedScenario(CharSequence ScenarioName) {
		String updateQuery = "UPDATE  Scenario SET Replayed=1 WHERE"
				+ " ScenarioName = " + "\"" + ScenarioName + "\"";
		mDb.execSQL(updateQuery);
	}

	public void setAvatarEye(Integer eye) {
		String query = "UPDATE Avatar SET Eyes = " + eye + " WHERE" + " ID = 1";
		mDb.execSQL(query);
	}

	public void setAvatarFace(Integer face) {
		String query = "UPDATE Avatar SET Face = " + face + " WHERE"
				+ " ID = 1";
		mDb.execSQL(query);
	}

	public void setAvatarCloth(Integer cloth) {
		String query = "UPDATE Avatar SET Clothes = " + cloth + " WHERE"
				+ " ID = 1";
		mDb.execSQL(query);
	}

	public void setAvatarHair(Integer hair) {
		String query = "UPDATE Avatar SET Hair = " + hair + " WHERE"
				+ " ID = 1";
		mDb.execSQL(query);
	}

	public Integer getAvatarFace() {
		String query = "Select * from Avatar WHERE ID = 1";
		Cursor cursor = mDb.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(1);
		}
		cursor.close();
		return 1;
	}

	public int getAvatarEye() {
		String query = "Select * from Avatar WHERE ID = 1";
		Cursor cursor = mDb.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(4);
		}
		cursor.close();
		return 1;
	}

	public int getAvatarCloth() {
		String query = "Select * from Avatar WHERE ID = 1";
		Cursor cursor = mDb.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(2);
		}
		cursor.close();
		return 1;
	}

	public int getAvatarHair() {
		String query = "Select * from Avatar WHERE ID = 1";
		Cursor cursor = mDb.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			return cursor.getInt(3);
		}
		cursor.close();
		return 1;

	}
}