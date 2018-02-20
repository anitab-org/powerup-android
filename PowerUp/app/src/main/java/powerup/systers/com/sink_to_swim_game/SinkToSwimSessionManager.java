package powerup.systers.com.sink_to_swim_game;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fidel on 12/30/2017.
 */

public class SinkToSwimSessionManager {
    private final String GAME_OPENED = "IS_SINK_TO_SWIM_OPENED";
    private static final String CURR_SCORE = "currScore";
    private static final String TIME_LEFT = "timeLeft";
    private static final String CURR_QUESTION = "currQuestion";
    private static final String WRONG_ANSWERS = "wrongAnswers";
    private static final String CORRECT_ANSWERS = "correctAnswers";
    private static final String SPEED = "speed";
    private static final String BOAT_HEIGHT = "boatHeight";
    private static final String POINTER_HEIGHT = "pointerHeight";
    private final String PREF_NAME = "SINK_TO_SWIM_PREFERENCE";
    private final int PRIVATE_MODE = 0;

    private SharedPreferences pref;
    private Context context;
    private SharedPreferences.Editor editor;
    public SinkToSwimSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void saveData(int score, long timeLeft, int currQuestion, int wrongAns,
                         int corAns, int speed, float boatHeight, float pointerHeight) {
        editor.putInt(CURR_SCORE, score);
        editor.putLong(TIME_LEFT, timeLeft);
        editor.putInt(CURR_QUESTION, currQuestion);
        editor.putInt(WRONG_ANSWERS, wrongAns);
        editor.putInt(CORRECT_ANSWERS, corAns);
        editor.putInt(SPEED, speed);
        editor.putFloat(BOAT_HEIGHT, boatHeight);
        editor.putFloat(POINTER_HEIGHT, pointerHeight);
        editor.commit();
    }
    //these function will not be called if it's the first time mini game is opened
    public int getCurrScore() {return pref.getInt(CURR_SCORE,0);}
    public int getCurrQuestion() {return pref.getInt(CURR_QUESTION,0);}
    public int getWrongAnswer() {return pref.getInt(WRONG_ANSWERS,0);}
    public int getCorrectAnswer() {return pref.getInt(CORRECT_ANSWERS,0);}
    public int getSpeed() {return pref.getInt(SPEED,0);}
    public float getBoatHeight() {return pref.getFloat(BOAT_HEIGHT,0);}
    public float getPointerHeight() {return pref.getFloat(POINTER_HEIGHT,0);}
    public long getTimeLeft() {return pref.getLong(TIME_LEFT,0);}

    public boolean isSinkToSwimOpened() {
        return pref.getBoolean(GAME_OPENED, false);
    }

    public void saveSinkToSwimOpenedStatus(boolean isOpened) {
        editor.putBoolean(GAME_OPENED, isOpened);
        editor.clear();
        editor.commit();
    }
}
