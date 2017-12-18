/**
* @desc this class holds functions for updating answers, questions, and avatar
* features. Examples include getAvatarFace() and setCompletedScenario()
*/

package powerup.systers.com.db;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
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

    /**
    * @desc adds all answer options to an ArrayList.
    * @param answers - the ArrayList to add answers to
    * @param qId - the question ID
    */
    public void getAllAnswer(List<Answer> answers, Integer qId) {
        String selectQuery = "SELECT  * FROM " + PowerUpContract.AnswerEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AnswerEntry.COLUMN_QUESTION_ID + " = " + qId;
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

    /**
    * @desc as long as the game is not over, the current question will be returned 
    * (otherwise return null)
    * @return Question - the question of the current session
    */
    public Question getCurrentQuestion() {
        String selectQuery = "SELECT  * FROM " + PowerUpContract.QuestionEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.QuestionEntry.COLUMN_QUESTION_ID + " = "
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

    /** 
    * @desc as long as the game is not over, the current scenario will be returned 
    * (otherwise return null)
    * @return Scenario - the scenario of the current session
    */
    public Scenario getScenario() {
        String selectQuery = "SELECT  * FROM " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_ID + " = "
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

    /**
    * @desc simply get a scenario by searching with its ID.
    * @param id - the ID of the scenario you are looking for
    * @return Scenario - the scenario of the passed ID
    */
    public Scenario getScenarioFromID(int id) {
        String selectQuery = "SELECT  * FROM " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_ID + " = "
                + id;
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

    /** 
    * @desc prevents a previously completed scenario from being repeated again. Sets
    * scene ID to the scenario name being passed.
    * @param ScenarioName - the scenario being checked for completion
    * @return boolean - if the scenario has been completed or not
    */
    public boolean setSessionId(CharSequence ScenarioName) {
        String selectQuery = "SELECT  * FROM " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_SCENARIO_NAME + " = "
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

    /**
    * @desc records completed scenarios in the database.
    * @param id - the scenario ID to record completion
    */
    public void setCompletedScenario(int id) {
        String updateQuery = "UPDATE  " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_COMPLETED + "=1" +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(updateQuery);
        String selectQuery = "SELECT  * FROM " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_ID + " = "
                + id;
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            Log.i("Completed : ", String.valueOf(cursor.getInt(6)));
        }
        cursor.close();
    }

    /**
    * @desc records "replayed" variable for a scenario into the database.
    * @param ScenarioName - the name of scenario to record "replayed"
    */
    public void setReplayedScenario(CharSequence ScenarioName) {
        String updateQuery = "UPDATE  " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_REPLAYED + "=1" +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_SCENARIO_NAME + " = " +
                "\"" + ScenarioName + "\"";
        mDb.execSQL(updateQuery);
    }

    /**
    * @desc get the current avatar face to display.
    * @return Integer - the number of the current face. Default is 1.
    */
    public Integer getAvatarSkin() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(1);
        }
        cursor.close();
        return 1;
    }

    /**
    * @desc change the avatar's face.
    * @param face - the number of the requested face
    */
    public void setAvatarSkin(Integer face) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_FACE + " = " + face +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    /**
    * @desc get the current avatar eye to display.
    * @return int - the number of the current eye. Default is 1.
    */
    public int getAvatarEye() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(4);
        }
        cursor.close();
        return 1;
    }

    /** 
    * @desc change the avatar's eyes.
    * @param eye - the number of the requested eye
    */
    public void setAvatarEye(Integer eye) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_EYES + " = " + eye +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    /**
    * @desc get the current avatar clothing to display.
    * @return int - the number of the current clothes. Default is 1.
    */
    public int getAvatarCloth() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(2);
        }
        cursor.close();
        return 1;
    }

    /**
    * @desc change the avatar's clothes.
    * @param cloth - the number of the requested clothing
    */
    public void setAvatarCloth(Integer cloth) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_CLOTHES + " = " + cloth +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    /**
    * @desc get the current avatar hair to display.
    * @return int - the number of the current hair. Default is 1.
    */
    public int getAvatarHair() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 1;
    }

    /**
    * @desc change the avatar's hair.
    * @param hair - the number of the requested hair
    */
    public void setAvatarHair(Integer hair) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_HAIR + " = " + hair +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }


    /**
    * @desc confirms that a piece of clothing has been bought to lay "purchased" text
    * on top.
    * @param id - the ID of the clothing to check
    * @return int - if the clothes have been purchased or not (return 1 if purchased)
    */
    public int getPurchasedClothes(int id) {
        String query = "Select * from " + PowerUpContract.ClothesEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ClothesEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 0;
    }

    /**
    * @desc updates a piece of clothing to know that it has been purchased.
    * @param id - the ID of the clothing to record purchased status
    */
    public void setPurchasedClothes(int id) {
        String query = "UPDATE " + PowerUpContract.ClothesEntry.TABLE_NAME +
                " SET " + PowerUpContract.ClothesEntry.COLUMN_PURCHASED + " = 1"  +
                " WHERE " + PowerUpContract.ClothesEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    /**
    * @desc confirms that a hair has been bought to lay "purchased" text on top.
    * @param id - the ID of the hair to check
    * @return int - if the hair been purchased or not (return 1 if purchased)
    */
    public int getPurchasedHair(int id) {
        String query = "Select * from " + PowerUpContract.HairEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.HairEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 0;
    }

    /**
    * @desc updates a hair to know that it has been purchased.
    * @param id - the ID of the hair to record purchased status
    */
    public void setPurchasedHair(int id) {
        String query = "UPDATE " + PowerUpContract.HairEntry.TABLE_NAME +
                " SET " + PowerUpContract.HairEntry.COLUMN_PURCHASED + " = 1"  +
                " WHERE " + PowerUpContract.HairEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    /**
    * @desc confirms that an accessory has been bought to lay "purchased" text on top.
    * @param id - the ID of the accessory to check
    * @return int - if the accessory been purchased or not (return 1 if purchased)
    */
    public int getPurchasedAccessories(int id) {
        String query = "Select * from " + PowerUpContract.AccessoryEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AccessoryEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 0;
    }

    /**
    * @desc updates an accessory to know that it has been purchased.
    * @param id - the ID of the accessory to record purchased status
    */
    public void setPurchasedAccessories(int id) {
        String query = "UPDATE " + PowerUpContract.AccessoryEntry.TABLE_NAME +
                " SET " + PowerUpContract.AccessoryEntry.COLUMN_PURCHASED + " = 1"  +
                " WHERE " + PowerUpContract.AccessoryEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    /**
    * @desc sets scenarios back to default (not complete).
    */
    public void updateComplete()
    {
        String query = "UPDATE " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_COMPLETED + " = 0";
        mDb.execSQL(query);
    }

    /**
    * @desc sets scenarios back so that they can be replayed.
    */
    public void updateReplayed()
    {
        String query = "UPDATE " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_REPLAYED + " = 0";
        mDb.execSQL(query);
    }
    
    /**
    * @desc sets purchases to default so that nothing has been bought.
    */
    public void resetPurchase()
    {
        String query = "UPDATE " + PowerUpContract.ClothesEntry.TABLE_NAME +
                " SET " + PowerUpContract.ClothesEntry.COLUMN_PURCHASED + " = 0";
        mDb.execSQL(query);

        String query2 = "UPDATE " + PowerUpContract.HairEntry.TABLE_NAME +
                " SET " + PowerUpContract.HairEntry.COLUMN_PURCHASED + " = 0";
        mDb.execSQL(query2);

        String query3 = "UPDATE " + PowerUpContract.AccessoryEntry.TABLE_NAME +
                " SET " + PowerUpContract.AccessoryEntry.COLUMN_PURCHASED + " = 0";
        mDb.execSQL(query3);

        String query4 = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_ACCESSORY + " = 0";
        mDb.execSQL(query4);
    }

    public void resetReplayed(int id){
        String query = "UPDATE " + PowerUpContract.ScenarioEntry.TABLE_NAME
                + " SET " + PowerUpContract.ScenarioEntry.COLUMN_REPLAYED + " = 0"
                + " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    public void resetCompleted(int id){
        String query = "UPDATE " + PowerUpContract.ScenarioEntry.TABLE_NAME
                + " SET " + PowerUpContract.ScenarioEntry.COLUMN_COMPLETED + " = 0"
                + " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    /**
     * @desc returns the id of current accessory assigned to avatar
     */
    public int getAvatarAccessory() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(6); //i think it should be different
        }
        cursor.close();
        return 0;
    }

    /**
     * @desc changes the current accessory assigned to avatar to given accessoryId
     * @param accessoryId - the id of accessory to be set
     */
    public void setAvatarAccessory(Integer accessoryId) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_ACCESSORY + " = " + accessoryId +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }
}
