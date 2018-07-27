package powerup.systers.com.memory_match_game;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class MemoryMatchSessionManager {

    private final String GAME_OPENED = "IS_MEMORY_MATCH_OPENED";
    private static final String CURR_SCORE = "currScore";
    private static final String TIME_LEFT = "timeLeft";
    private static final String CURR_TILE = "currTile";
    private static final String PREVIOUS_TILE = "previousTile";
    private static final String WRONG_ANSWERS = "wrongAnswers";
    private static final String CORRECT_ANSWERS = "correctAnswers";
    private final String PREF_NAME = "MEMORY_MATCH_PREFERENCE";
    private final int PRIVATE_MODE = 0;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public MemoryMatchSessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveData(int score, long timeLeft,int currentTile, int previousTile, int correctAnswers, int wrongAnswers){
        editor.putInt(CURR_SCORE, score);
        editor.putLong(TIME_LEFT, timeLeft);
        editor.putInt(CURR_TILE, currentTile);
        editor.putInt(PREVIOUS_TILE, previousTile);
        editor.putInt(CORRECT_ANSWERS, correctAnswers);
        editor.putInt(WRONG_ANSWERS, wrongAnswers);
        Log.v("SESSION MANAGER","SAVING DATA");
        Log.v("Score", "  " + score);
        Log.v("Time Left", "  " + timeLeft);
        Log.v("Current Tile", "  " + currentTile);
        Log.v("Previous Tile", "  " + previousTile);
        Log.v("Correct Answers", "  " + correctAnswers);
        Log.v("Wrong Answer", "  " + wrongAnswers);
        editor.commit();
    }

    public int getCurrScore() {
        return pref.getInt(CURR_SCORE,0);
    }

    public long getTimeLeft() {
        return pref.getLong(TIME_LEFT,0);
    }

    public int getCurrTile() {
        return pref.getInt(CURR_TILE,0);
    }

    public int getPrevTile() {
        return pref.getInt(PREVIOUS_TILE,0);
    }

    public int getWrongAnswer() {
        return pref.getInt(WRONG_ANSWERS,0);
    }

    public int getCorrectAnswer() {
        return pref.getInt(CORRECT_ANSWERS,0);
    }

    public boolean isMemoryMatchOpened() {
        return pref.getBoolean(GAME_OPENED, false);
    }

    public void saveMemoryMatchOpenedStatus(boolean isOpened) {
        editor.putBoolean(GAME_OPENED, isOpened);
        editor.clear();
        editor.commit();
    }
}