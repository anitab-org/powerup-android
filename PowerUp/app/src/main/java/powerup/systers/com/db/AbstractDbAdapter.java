/**
* @desc holds functions to allow game features to be inserted into the database by reading
* CSV files. Examples include insertPoints() and insertDBScenario().
*/

package powerup.systers.com.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public abstract class AbstractDbAdapter {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "PowerUpDB";
    protected static SQLiteDatabase mDb;
    private static BufferedReader in;
    private static AssetManager assetManager;
    protected final Context mCtx;
    protected DatabaseHelper mDbHelper;

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

    protected static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
	
	/**
	* @desc enter the default avatar settings into the database.
	* @param db - the database the avatar will be put into
	*/
        public void insertAvatar(SQLiteDatabase db) {
            ContentValues values = new ContentValues();
            values.put(PowerUpContract.AvatarEntry.COLUMN_ID, 1);
            values.put(PowerUpContract.AvatarEntry.COLUMN_FACE, 1);
            values.put(PowerUpContract.AvatarEntry.COLUMN_CLOTHES, 1);
            values.put(PowerUpContract.AvatarEntry.COLUMN_HAIR, 1);
            values.put(PowerUpContract.AvatarEntry.COLUMN_EYES, 1);
            values.put(PowerUpContract.AvatarEntry.COLUMN_HAT, 0);
            values.put(PowerUpContract.AvatarEntry.COLUMN_GLASSES, 0);
            values.put(PowerUpContract.AvatarEntry.COLUMN_ACCESSORY, 0);
            values.put(PowerUpContract.AvatarEntry.COLUMN_NECKLACE, 0);
            db.insert(PowerUpContract.AvatarEntry.TABLE_NAME, null, values);
        }
	
	/**
	* @desc enter the lowest possible values of power/health bars and karma points
	* into the database.
	* @param db - the database the scores will be put into
	*/
        public void insertPoints(SQLiteDatabase db) {
            ContentValues values = new ContentValues();
            values.put(PowerUpContract.PointEntry.COLUMN_ID, 1);
            values.put(PowerUpContract.PointEntry.COLUMN_HEALING, 1);
            values.put(PowerUpContract.PointEntry.COLUMN_INVISIBILITY, 1);
            values.put(PowerUpContract.PointEntry.COLUMN_STRENGTH, 1);
            values.put(PowerUpContract.PointEntry.COLUMN_TELEPATHY, 1);
            values.put(PowerUpContract.PointEntry.COLUMN_USER_POINTS, 0);
            db.insert(PowerUpContract.PointEntry.TABLE_NAME, null, values);
        }

	/**
	* @desc enter the questions into the database.
	* @param db - the database the questions will be put into
	* @param rowData - the array to read question information from
	*/
        public void insertDBQuestion(SQLiteDatabase db, String[] rowData) {
            ContentValues values = new ContentValues();
            // if (rowData.length == 3) {
            values.put(PowerUpContract.QuestionEntry.COLUMN_QUESTION_ID, rowData[0]);
            values.put(PowerUpContract.QuestionEntry.COLUMN_SCENARIO_ID, rowData[1]);
            values.put(PowerUpContract.QuestionEntry.COLUMN_QUESTION_DESCRIPTION, rowData[2]);
            db.insert(PowerUpContract.QuestionEntry.TABLE_NAME, null, values);
            /*
             * } else { throw new
			 * Error("Incorrect Question CSV Format! Use QID," +
			 * "ScenarioID, QDes at line: " + rowData.toString()); }
			 */
        }

	/**
	* @desc enter the answers into the database.
	* @param db - the database the answers will be put into
	* @param rowData - the array to read answer information from
	*/
        public void insertDBAnswer(SQLiteDatabase db, String[] rowData) {
            ContentValues values = new ContentValues();
            if (rowData.length == 5) {
                values.put(PowerUpContract.AnswerEntry.COLUMN_ANSWER_ID, rowData[0]);
                values.put(PowerUpContract.AnswerEntry.COLUMN_QUESTION_ID, rowData[1]);
                values.put(PowerUpContract.AnswerEntry.COLUMN_ANSWER_DESCRIPTION, rowData[2]);
                values.put(PowerUpContract.AnswerEntry.COLUMN_NEXT_ID, rowData[3]);
                values.put(PowerUpContract.AnswerEntry.COLUMN_POINTS, rowData[4]);
                db.insert(PowerUpContract.AnswerEntry.TABLE_NAME, null, values);
            } else {
                throw new Error(
                        "Incorrect Answer CSV Format! Use AID, QID, ADes,"
                                + "NextID, Points at line: "
                                + Arrays.toString(rowData));
            }
        }

	/**
	* @desc enter the dialogue scenarios into the database.
	* @param db - the database the scenarios will be put into
	* @param rowData - the array to read scenario information from
	*/
        public void insertDBScenario(SQLiteDatabase db, String[] rowData) {
            ContentValues values = new ContentValues();
            if (rowData.length == 7) {
                values.put(PowerUpContract.ScenarioEntry.COLUMN_ID, rowData[0]);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_SCENARIO_NAME, rowData[1]);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_TIMESTAMP, rowData[2]);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_ASKER, rowData[3]);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_AVATAR, rowData[4]);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_FIRST_QUESTION_ID, rowData[5]);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_COMPLETED, 0);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_NEXT_SCENARIO_ID, rowData[6]);
                values.put(PowerUpContract.ScenarioEntry.COLUMN_REPLAYED, 0);
                db.insert(PowerUpContract.ScenarioEntry.TABLE_NAME, null, values);
            } else {
                throw new Error("Incorrect Scenario CSV Format! Use ID,"
                        + "ScenarioName, Timestamp, Asker, Avatar, FirstQID,"
                        + " NextScenarioID at line: " + Arrays.toString(rowData));
            }
        }

	/**
	* @desc enter the clothing information into the database.
	* @param db - the database the clothes will be put into
	* @param rowData - the array to read clothing information from
	*/
        public void insertDBClothes(SQLiteDatabase db, String[] rowData) {
            ContentValues values = new ContentValues();
            if (rowData.length == 3) {
                values.put(PowerUpContract.ClothesEntry.COLUMN_ID, rowData[0]);
                values.put(PowerUpContract.ClothesEntry.COLUMN_CLOTH_NAME, rowData[1]);
                values.put(PowerUpContract.ClothesEntry.COLUMN_POINTS, rowData[2]);
                values.put(PowerUpContract.ClothesEntry.COLUMN_PURCHASED, 0);
                db.insert(PowerUpContract.ClothesEntry.TABLE_NAME, null, values);
            } else {
                throw new Error("Incorrect Clothes CSV Format! Use ID,"
                        + "ClothName, Points" + Arrays.toString(rowData));
            }
        }

	/**
	* @desc enter the hair information into the database.
	* @param db - the database the hair will be put into
	* @param rowData - the array to read hair information from
	*/
        public void insertDBHair(SQLiteDatabase db, String[] rowData) {
            ContentValues values = new ContentValues();
            if (rowData.length == 3) {
                values.put(PowerUpContract.HairEntry.COLUMN_ID, rowData[0]);
                values.put(PowerUpContract.HairEntry.COLUMN_HAIR_NAME, rowData[1]);
                values.put(PowerUpContract.HairEntry.COLUMN_POINTS, rowData[2]);
                values.put(PowerUpContract.HairEntry.COLUMN_PURCHASED, 0);
                db.insert(PowerUpContract.HairEntry.TABLE_NAME, null, values);
            } else {
                throw new Error("Incorrect Hair CSV Format! Use ID,"
                        + "HairName, Points" + Arrays.toString(rowData));
            }
        }

	/**
	* @desc enter the accessory information into the database.
	* @param db - the database the accessory will be put into
	* @param rowData - the array to read accessory information from
	*/
        public void insertDBAccessories(SQLiteDatabase db, String[] rowData) {
            ContentValues values = new ContentValues();
            if (rowData.length == 3) {
                values.put(PowerUpContract.AccessoryEntry.COLUMN_ID, rowData[0]);
                values.put(PowerUpContract.AccessoryEntry.COLUMN_ACCESSORY_NAME, rowData[1]);
                values.put(PowerUpContract.AccessoryEntry.COLUMN_POINTS, rowData[2]);
                values.put(PowerUpContract.AccessoryEntry.COLUMN_PURCHASED, 0);
                db.insert(PowerUpContract.AccessoryEntry.TABLE_NAME, null, values);
            } else {
                throw new Error("Incorrect Accessory CSV Format! Use ID,"
                        + "AccessoryName, Points" + Arrays.toString(rowData));
            }
        }

	/**
	* @desc read CSV file and insert the question it contains into database.
	* @param db - the database the question will be put into
	* @param filename - the file to extract question information from
	*/
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

	/**
	* @desc read CSV file and insert the answer it contains into database.
	* @param db - the database the answer will be put into
	* @param filename - the file to extract answer information from
	*/
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

	/**
	* @desc read CSV file and insert the scenario it contains into database.
	* @param db - the database the scenario will be put into
	* @param filename - the file to extract scenario information from
	*/
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

	/**
	* @desc read CSV file and insert the clotes it contains into database.
	* @param db - the database the clothes will be put into
	* @param filename - the file to extract clothing information from
	*/
        public void readCSVClothes(SQLiteDatabase db, String filename) throws IOException {
            in = new BufferedReader(new InputStreamReader(assetManager.open(filename)));
            String reader;
            while ((reader = in.readLine()) != null) {
                String[] RowData = reader.split(",");
                insertDBClothes(db, RowData);
            }
            in.close();
        }

	/**
	* @desc read CSV file and insert the hair it contains into database.
	* @param db - the database the hair will be put into
	* @param filename - the file to extract hair information from
	*/
        public void readCSVHair(SQLiteDatabase db, String filename) throws IOException {
            in = new BufferedReader(new InputStreamReader(assetManager.open(filename)));
            String reader;
            while ((reader = in.readLine()) != null) {
                String[] RowData = reader.split(",");
                insertDBHair(db, RowData);
            }
            in.close();
        }

	/**
	* @desc read CSV file and insert the accessores it contains into database.
	* @param db - the database the accessories will be put into
	* @param filename - the file to extract accessory information from
	*/
        public void readCSVAccessories(SQLiteDatabase db, String filename) throws IOException {
            in = new BufferedReader(new InputStreamReader(assetManager.open(filename)));
            String reader;
            while ((reader = in.readLine()) != null) {
                String[] RowData = reader.split(",");
                insertDBAccessories(db, RowData);
            }
            in.close();
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_QUESTION_TABLE = "CREATE TABLE " + PowerUpContract.QuestionEntry.TABLE_NAME + "(" +
                    PowerUpContract.QuestionEntry.COLUMN_QUESTION_ID + " INTEGER PRIMARY KEY ," +
                    PowerUpContract.QuestionEntry.COLUMN_SCENARIO_ID + " INTEGER," +
                    PowerUpContract.QuestionEntry.COLUMN_QUESTION_DESCRIPTION + " TEXT" +
                    ")";

            String CREATE_ANSWER_TABLE = "CREATE TABLE " + PowerUpContract.AnswerEntry.TABLE_NAME + "(" +
                    PowerUpContract.AnswerEntry.COLUMN_ANSWER_ID + " INTEGER PRIMARY KEY," +
                    PowerUpContract.AnswerEntry.COLUMN_QUESTION_ID + " INTEGER," +
                    PowerUpContract.AnswerEntry.COLUMN_ANSWER_DESCRIPTION + " TEXT, " +
                    PowerUpContract.AnswerEntry.COLUMN_NEXT_ID + " INTEGER, " +
                    PowerUpContract.AnswerEntry.COLUMN_POINTS + " INTEGER" +
                    ")";

            String CREATE_SCENARIO_TABLE = "CREATE TABLE " + PowerUpContract.ScenarioEntry.TABLE_NAME + "(" +
                    PowerUpContract.ScenarioEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    PowerUpContract.ScenarioEntry.COLUMN_SCENARIO_NAME + " TEXT, " +
                    PowerUpContract.ScenarioEntry.COLUMN_TIMESTAMP + " TEXT," +
                    PowerUpContract.ScenarioEntry.COLUMN_ASKER + " TEXT, " +
                    PowerUpContract.ScenarioEntry.COLUMN_AVATAR + " INTEGER, " +
                    PowerUpContract.ScenarioEntry.COLUMN_FIRST_QUESTION_ID + " INTEGER, " +
                    PowerUpContract.ScenarioEntry.COLUMN_COMPLETED + " INTEGER, " +
                    PowerUpContract.ScenarioEntry.COLUMN_NEXT_SCENARIO_ID + " INTEGER, " +
                    PowerUpContract.ScenarioEntry.COLUMN_REPLAYED + " INTEGER" +
                    ")";

            String CREATE_POINT_TABLE = "CREATE TABLE " + PowerUpContract.PointEntry.TABLE_NAME + "(" +
                    PowerUpContract.PointEntry.COLUMN_ID + " INTEGER, " +
                    PowerUpContract.PointEntry.COLUMN_STRENGTH + " INTEGER," +
                    PowerUpContract.PointEntry.COLUMN_INVISIBILITY + " INTEGER, " +
                    PowerUpContract.PointEntry.COLUMN_HEALING + " INTEGER, " +
                    PowerUpContract.PointEntry.COLUMN_TELEPATHY + " INTEGER, " +
                    PowerUpContract.PointEntry.COLUMN_USER_POINTS + " INTEGER" +
                    ")";

            String CREATE_AVATAR_TABLE = " CREATE TABLE " + PowerUpContract.AvatarEntry.TABLE_NAME + "(" +
                    PowerUpContract.AvatarEntry.COLUMN_ID + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_FACE + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_CLOTHES + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_HAIR + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_EYES + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_HAT + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_ACCESSORY + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_GLASSES + " INTEGER, " +
                    PowerUpContract.AvatarEntry.COLUMN_NECKLACE + " INTEGER" +
                    ")";

            String CREATE_CLOTHES_TABLE = " CREATE TABLE " + PowerUpContract.ClothesEntry.TABLE_NAME + "(" +
                    PowerUpContract.ClothesEntry.COLUMN_ID + " INTEGER, " +
                    PowerUpContract.ClothesEntry.COLUMN_CLOTH_NAME + " TEXT, " +
                    PowerUpContract.ClothesEntry.COLUMN_POINTS + " INTEGER, " +
                    PowerUpContract.ClothesEntry.COLUMN_PURCHASED + " INTEGER " +
                    ")";

            String CREATE_HAIR_TABLE = " CREATE TABLE " + PowerUpContract.HairEntry.TABLE_NAME + "(" +
                    PowerUpContract.HairEntry.COLUMN_ID + " INTEGER, " +
                    PowerUpContract.HairEntry.COLUMN_HAIR_NAME + " TEXT, " +
                    PowerUpContract.HairEntry.COLUMN_POINTS + " INTEGER, " +
                    PowerUpContract.HairEntry.COLUMN_PURCHASED + " INTEGER " +
                    ")";

            String CREATE_ACCESSORY_TABLE = " CREATE TABLE " + PowerUpContract.AccessoryEntry.TABLE_NAME + "(" +
                    PowerUpContract.AccessoryEntry.COLUMN_ID + " INTEGER, " +
                    PowerUpContract.AccessoryEntry.COLUMN_ACCESSORY_NAME + " TEXT, " +
                    PowerUpContract.AccessoryEntry.COLUMN_POINTS + " INTEGER, " +
                    PowerUpContract.AccessoryEntry.COLUMN_PURCHASED + " INTEGER " +
                    ")";

            db.execSQL(CREATE_QUESTION_TABLE);
            db.execSQL(CREATE_ANSWER_TABLE);
            db.execSQL(CREATE_SCENARIO_TABLE);
            db.execSQL(CREATE_POINT_TABLE);
            db.execSQL(CREATE_AVATAR_TABLE);
            db.execSQL(CREATE_CLOTHES_TABLE);
            db.execSQL(CREATE_HAIR_TABLE);
            db.execSQL(CREATE_ACCESSORY_TABLE);
            try {
                readCSVQuestion(db, "Question.csv");
                readCSVAnswer(db, "Answer.csv");
                readCSVScenario(db, "Scenario.csv");
                readCSVClothes(db, "Clothes.csv");
                readCSVHair(db, "Hair.csv");
                readCSVAccessories(db, "Accessories.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }
            insertAvatar(db);
            insertPoints(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.QuestionEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.AnswerEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.ScenarioEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.PointEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.AvatarEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.ClothesEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.HairEntry.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + PowerUpContract.AccessoryEntry.TABLE_NAME);
            onCreate(db);
        }
    }

}
