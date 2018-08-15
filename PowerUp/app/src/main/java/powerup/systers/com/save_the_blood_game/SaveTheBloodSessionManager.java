package powerup.systers.com.save_the_blood_game;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SaveTheBloodSessionManager {

    private final String GAME_OPENED = "IS_SAVE_BLOOD_OPENED";
    private static final String CURR_SCORE = "currScore";
    private static final String TIME_LEFT = "timeLeft";
    private static final String CURR_ROUND = "currRound";
    private static final String WRONG_ANSWERS = "wrongAnswers";
    private static final String CORRECT_ANSWERS = "correctAnswers";
    private static final String CURR_PROGRESS = "currProgress";
    private final String PREF_NAME = "SAVE_BLOOD_PREFERENCE";
    private final int PRIVATE_MODE = 0;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public SaveTheBloodSessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveData(int score, long timeLeft,int currRound, int correctAnswers, int wrongAnswers, int progress) {
        editor.putInt(CURR_SCORE, score);
        editor.putLong(TIME_LEFT, timeLeft);
        editor.putInt(CURR_ROUND, currRound);
        editor.putInt(CORRECT_ANSWERS, correctAnswers);
        editor.putInt(WRONG_ANSWERS, wrongAnswers);
        editor.putInt(CURR_PROGRESS, progress);
        Log.v("SaveBloodSessionManager","Saving Data");
        Log.v("SaveBloodSessionManager", "Score: " + score);
        Log.v("SaveBloodSessionManager", "Time Left: " + timeLeft);
        Log.v("SaveBloodSessionManager", "Current Round: " + currRound);
        Log.v("SaveBloodSessionManager", "  " + correctAnswers);
        Log.v("SaveBloodSessionManager", "  " + wrongAnswers);
        Log.v("SaveBloodSessionManager", "Current Progress: " + progress);
        editor.commit();
    }

    public int getCurrScore() {
        return pref.getInt(CURR_SCORE,0);
    }

    public long getTimeLeft() {
        return pref.getLong(TIME_LEFT,0);
    }

    public int getCurrRound(){
        return pref.getInt(CURR_ROUND, 0);
    }

    public int getWrongAnswer() {
        return pref.getInt(WRONG_ANSWERS,0);
    }

    public int getCorrectAnswer() {
        return pref.getInt(CORRECT_ANSWERS,0);
    }

    public int getCurrentProgress() {
        return pref.getInt(CURR_PROGRESS,0);
    }

    public boolean isSaveBloodOpened() {
        return pref.getBoolean(GAME_OPENED, false);
    }

    public void saveSaveBloodOpenedStatus(boolean isOpened) {
        editor.putBoolean(GAME_OPENED, isOpened);
        editor.clear();
        editor.commit();
    }
}