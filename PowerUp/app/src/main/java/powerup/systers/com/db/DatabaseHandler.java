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

    public boolean gameOver() {
        String selectQuery = "SELECT  * FROM " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_COMPLETED + " = 0";
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            return false;
        }
        cursor.close();
        return true;
    }

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

    public void setCompletedScenario(CharSequence ScenarioName) {
        String updateQuery = "UPDATE  " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_COMPLETED + "=1" +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_SCENARIO_NAME + " = " +
                "\"" + ScenarioName + "\"";
        mDb.execSQL(updateQuery);
    }

    public void setReplayedScenario(CharSequence ScenarioName) {
        String updateQuery = "UPDATE  " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_REPLAYED + "=1" +
                " WHERE " + PowerUpContract.ScenarioEntry.COLUMN_SCENARIO_NAME + " = " +
                "\"" + ScenarioName + "\"";
        mDb.execSQL(updateQuery);
    }

    public Integer getAvatarFace() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(1);
        }
        cursor.close();
        return 1;
    }

    public void setAvatarFace(Integer face) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_FACE + " = " + face +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

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

    public void setAvatarEye(Integer eye) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_EYES + " = " + eye +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

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

    public void setAvatarCloth(Integer cloth) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_CLOTHES + " = " + cloth +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

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

    public void setAvatarHair(Integer hair) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_HAIR + " = " + hair +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getAvatarBag() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(6);
        }
        cursor.close();
        return 0;
    }

    public void setAvatarBag(Integer bag) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_BAG + " = " + bag +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getAvatarGlasses() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(7);
        }
        cursor.close();
        return 0;
    }

    public void setAvatarGlasses(Integer glasses) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_GLASSES + " = " + glasses +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getAvatarHat() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(5);
        }
        cursor.close();
        return 0;
    }

    public void setAvatarHat(Integer hat) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_HAT + " = " + hat +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getAvatarNeckalce() {
        String query = "Select * from " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(8);
        }
        cursor.close();
        return 0;
    }

    public void setAvatarNecklace(Integer necklace) {
        String query = "UPDATE " + PowerUpContract.AvatarEntry.TABLE_NAME +
                " SET " + PowerUpContract.AvatarEntry.COLUMN_NECKLACE + " = " + necklace +
                " WHERE " + PowerUpContract.AvatarEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getHealing() {
        String query = "Select * from " + PowerUpContract.PointEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 1;
    }

    public void setHealing(Integer healing) {
        String query = "UPDATE " + PowerUpContract.PointEntry.TABLE_NAME +
                " SET " + PowerUpContract.PointEntry.COLUMN_HEALING + " = " + healing +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
        Log.i("heal", getHealing() + " ");
    }

    public int getStrength() {
        String query = "Select * from " + PowerUpContract.PointEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(1);
        }
        cursor.close();
        return 1;
    }

    public void setStrength(Integer strength) {
        String query = "UPDATE " + PowerUpContract.PointEntry.TABLE_NAME +
                " SET " + PowerUpContract.PointEntry.COLUMN_STRENGTH + " = " + strength +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getTelepathy() {
        String query = "Select * from " + PowerUpContract.PointEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(4);
        }
        cursor.close();
        return 1;
    }

    public void setTelepathy(Integer telepathy) {
        String query = "UPDATE " + PowerUpContract.PointEntry.TABLE_NAME +
                " SET " + PowerUpContract.PointEntry.COLUMN_TELEPATHY + " = " + telepathy +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getInvisibility() {
        String query = "Select * from " + PowerUpContract.PointEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(2);
        }
        cursor.close();
        return 1;
    }

    public void setInvisibility(Integer invisibility) {
        String query = "UPDATE " + PowerUpContract.PointEntry.TABLE_NAME +
                " SET " + PowerUpContract.PointEntry.COLUMN_INVISIBILITY + " = " + invisibility +
                " WHERE " + PowerUpContract.PointEntry.COLUMN_ID + " = 1";
        mDb.execSQL(query);
    }

    public int getPointsClothes(int id) {
        String query = "Select * from " + PowerUpContract.ClothesEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ClothesEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(2);
        }
        cursor.close();
        return 1;
    }

    public int getPointsHair(int id) {
        String query = "Select * from " + PowerUpContract.HairEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.HairEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(2);
        }
        cursor.close();
        return 1;
    }

    public int getPointsAccessories(int id) {
        String query = "Select * from " + PowerUpContract.AccessoryEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AccessoryEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(2);
        }
        cursor.close();
        return 1;
    }

    public int getPurchasedClothes(int id) {
        String query = "Select * from " + PowerUpContract.ClothesEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.ClothesEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 1;
    }

    public void setPurchasedClothes(int id) {
        String query = "UPDATE " + PowerUpContract.ClothesEntry.TABLE_NAME +
                " SET " + PowerUpContract.ClothesEntry.COLUMN_PURCHASED + " = 1"  +
                " WHERE " + PowerUpContract.ClothesEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    public int getPurchasedHair(int id) {
        String query = "Select * from " + PowerUpContract.HairEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.HairEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 1;
    }

    public void setPurchasedHair(int id) {
        String query = "UPDATE " + PowerUpContract.HairEntry.TABLE_NAME +
                " SET " + PowerUpContract.HairEntry.COLUMN_PURCHASED + " = 1"  +
                " WHERE " + PowerUpContract.HairEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    public int getPurchasedAccessories(int id) {
        String query = "Select * from " + PowerUpContract.AccessoryEntry.TABLE_NAME +
                " WHERE " + PowerUpContract.AccessoryEntry.COLUMN_ID + " = " + id;
        Cursor cursor = mDb.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(3);
        }
        cursor.close();
        return 1;
    }

    public void setPurchasedAccessories(int id) {
        String query = "UPDATE " + PowerUpContract.AccessoryEntry.TABLE_NAME +
                " SET " + PowerUpContract.AccessoryEntry.COLUMN_PURCHASED + " = 1"  +
                " WHERE " + PowerUpContract.AccessoryEntry.COLUMN_ID + " = " + id;
        mDb.execSQL(query);
    }

    public void updateComplete()
    {
        String query = "UPDATE " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_COMPLETED + " = 0";
        mDb.execSQL(query);
    }

    public void updateReplayed()
    {
        String query = "UPDATE " + PowerUpContract.ScenarioEntry.TABLE_NAME +
                " SET " + PowerUpContract.ScenarioEntry.COLUMN_REPLAYED + " = 0";
        mDb.execSQL(query);
    }
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
    }
}
