package powerup.systers.com.kill_the_virus_game;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class KillTheVirusSessionManager {

    private final String GAME_OPENED = "IS_KILL_VIRUS_OPEN";
    private final String CURR_SCORE = "currScore";
    private final String TIME_LEFT = "timeLeft";
    private final String LIVES_LEFT = "livesLeft";
    private final String VIRUS_SPEED = "virusSpeed";
    private final String PREF_NAME = "KILL_THE_VIRUS_PREFERENCE";
    private final int PRIVATE_MODE = 0;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public KillTheVirusSessionManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveData(int score, long timeLeft, int livesLeft, long speed){
        editor.putInt(CURR_SCORE, score);
        editor.putLong(TIME_LEFT, timeLeft);
        editor.putInt(LIVES_LEFT, livesLeft);
        editor.putLong(VIRUS_SPEED, speed);
        Log.v("KillVirusSessionManager","SAVING DATA");
        Log.v("Score", "  "+score);
        Log.v("Time Left", "  "+timeLeft);
        Log.v("Lives Left", "  "+livesLeft);
        Log.v("Duration ", "  "+speed);
        editor.commit();
    }

    public int getScore(){
        return pref.getInt(CURR_SCORE,0);
    }

    public long getTime(){
        return pref.getLong(TIME_LEFT, 0);
    }

    public int getLives(){
        return  pref.getInt(LIVES_LEFT, 0);
    }

    public long getSpeed(){
        return pref.getLong(VIRUS_SPEED, 0);
    }

    public boolean isKillTheVirusOpened(){
        return pref.getBoolean(GAME_OPENED, false);
    }

    public void saveKillTheVirusOpenedStatus(boolean isOpened){
        editor.putBoolean(GAME_OPENED, isOpened);
        editor.clear();
        editor.commit();
    }
}