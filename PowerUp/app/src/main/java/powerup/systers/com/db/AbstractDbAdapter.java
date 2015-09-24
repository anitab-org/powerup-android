package powerup.systers.com.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class AbstractDbAdapter {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "PowerUpDB";

	protected DatabaseHelper mDbHelper;
	protected static SQLiteDatabase mDb;

	private static BufferedReader in;

	protected final Context mCtx;
	private static AssetManager assetManager;

	protected static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		public void insertAvatar(SQLiteDatabase db) {
			ContentValues values = new ContentValues();
			values.put("ID", 1);
			values.put("Face", 1);
			values.put("Clothes", 1);
			values.put("Hair", 1);
			values.put("Eyes", 1);
			db.insert("Avatar", null, values);
		}

		public void insertDBQuestion(SQLiteDatabase db, String[] rowData) {
			ContentValues values = new ContentValues();
			// if (rowData.length == 3) {
			values.put("QID", rowData[0]);
			values.put("ScenarioID", rowData[1]);
			values.put("QDes", rowData[2]);
			db.insert("Question", null, values);
			/*
			 * } else { throw new
			 * Error("Incorrect Question CSV Format! Use QID," +
			 * "ScenarioID, QDes at line: " + rowData.toString()); }
			 */
		}

		public void insertDBAnswer(SQLiteDatabase db, String[] rowData) {
			ContentValues values = new ContentValues();
			if (rowData.length == 5) {
				values.put("AID", rowData[0]);
				values.put("QID", rowData[1]);
				values.put("ADes", rowData[2]);
				values.put("NextID", rowData[3]);
				values.put("Points", rowData[4]);
				db.insert("Answer", null, values);
			} else {
				throw new Error(
						"Incorrect Answer CSV Format! Use AID, QID, ADes,"
								+ "NextID, Points at line: "
								+ rowData.toString());
			}
		}

		public void insertDBScenario(SQLiteDatabase db, String[] rowData) {
			ContentValues values = new ContentValues();
			if (rowData.length == 7) {
				values.put("ID", rowData[0]);
				values.put("ScenarioName", rowData[1]);
				values.put("Timestamp", rowData[2]);
				values.put("Asker", rowData[3]);
				values.put("Avatar", rowData[4]);
				values.put("FirstQID", rowData[5]);
				values.put("Completed", 0);
				values.put("NextScenarioID", rowData[6]);
				values.put("Replayed", 0);
				db.insert("Scenario", null, values);
			} else {
				throw new Error("Incorrect Scenario CSV Format! Use ID,"
						+ "ScenarioName, Timestamp, Asker, Avatar, FirstQID,"
						+ " NextScenarioID at line: " + rowData.toString());
			}
		}

		public void readCSVQuestion(SQLiteDatabase db, String filename)
				throws IOException {
			in = new BufferedReader(new InputStreamReader(
					assetManager.open(filename)));
			String reader;
			while ((reader = in.readLine()) != null) {
				String[] RowData = reader.split(",");
				insertDBQuestion(db, RowData);
			}
			in.close();
		}

		public void readCSVAnswer(SQLiteDatabase db, String filename)
				throws IOException {
			in = new BufferedReader(new InputStreamReader(
					assetManager.open(filename)));
			String reader;
			while ((reader = in.readLine()) != null) {
				String[] RowData = reader.split(",");
				insertDBAnswer(db, RowData);
			}
			in.close();
		}

		public void readCSVScenario(SQLiteDatabase db, String filename)
				throws IOException {
			in = new BufferedReader(new InputStreamReader(
					assetManager.open(filename)));
			String reader;
			while ((reader = in.readLine()) != null) {
				String[] RowData = reader.split(",");
				insertDBScenario(db, RowData);
			}
			in.close();
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			String CREATE_QUESTION_TABLE = "CREATE TABLE Question(QID INTEGER"
					+ "PRIMARY KEY , ScenarioID INTEGER, QDes TEXT)";
			String CREATE_ANSWER_TABLE = "CREATE TABLE Answer(AID INTEGER PRIMARY"
					+ "KEY, QID INTEGER, ADes TEXT, NextID INTEGER, Points INTEGER"
					+ ")";
			String CREATE_SCENARIO_TABLE = "CREATE TABLE Scenario(ID INTEGER"
					+ "PRIMARY KEY, ScenarioName TEXT, Timestamp TEXT,"
					+ "Asker TEXT, Avatar INTEGER, FirstQID INTEGER, Completed"
					+ " INTEGER, NextScenarioID INTEGER, Replayed INTEGER)";
			String CREATE_POINT_TABLE = "CREATE TABLE Point(Strength INTEGER,"
					+ "Invisibility INTEGER, Healing INTEGER, Telepathy INTEGER"
					+ ")";
			String CREATE_AVATAR_TABLE = " CREATE TABLE Avatar(ID INTEGER, Face"
					+ " INTEGER, Clothes INTEGER, Hair INTEGER, Eyes INTEGER)";

			db.execSQL(CREATE_QUESTION_TABLE);
			db.execSQL(CREATE_ANSWER_TABLE);
			db.execSQL(CREATE_SCENARIO_TABLE);
			db.execSQL(CREATE_POINT_TABLE);
			db.execSQL(CREATE_AVATAR_TABLE);
			try {
				readCSVQuestion(db, "Question.csv");
				readCSVAnswer(db, "Answer.csv");
				readCSVScenario(db, "Scenario.csv");
			} catch (IOException e) {
				e.printStackTrace();
			}
			insertAvatar(db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			db.execSQL("DROP TABLE IF EXISTS Question");
			db.execSQL("DROP TABLE IF EXISTS Answer");
			db.execSQL("DROP TABLE IF EXISTS Scenario");
			onCreate(db);
		}
	}

	public AbstractDbAdapter(Context ctx) {
		this.mCtx = ctx;
		assetManager = ctx.getAssets();
		mDbHelper = new DatabaseHelper(mCtx);
	}

	public AbstractDbAdapter open() throws SQLException {
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		if (mDbHelper != null) {
			mDbHelper.close();
		}
	}

}
