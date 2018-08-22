package powerup.systers.com.minesweeper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by sachinaggarwal on 25/06/17.
 */

public class MinesweeperSessionManager {

    private final String SCORE = "MINESWEEPER_SCORE";
    private final String ROUNDS_COMPLETED = "MINESWEEPER_ROUND_COMPLETED";
    private final String PREF_NAME = "MINESWEEPER_PREFERENCE";
    private final int PRIVATE_MODE = 0;
    private final String GAME_OPENED = "IS_MINESWEEPER_OPENED";

    SharedPreferences pref;
    Context context;
    SharedPreferences.Editor editor;

    /**
     * @desc Returns the object of SessionManager through which session changes can be made
     * @param context - context of the calling activity
     */
    public MinesweeperSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * @desc updates the score and roundsCompleted in the session database
     * @param roundsCompleted - number of rounds completed till now
     * @param score - total current score
     */
    public void saveData(int score, int roundsCompleted) {
        editor.putInt(SCORE, score);
        editor.putInt(ROUNDS_COMPLETED, roundsCompleted);
        editor.commit();
    }

    public int getScore() {
        return pref.getInt(SCORE, 0);
    }

    public int getCompletedRounds() {
        return pref.getInt(ROUNDS_COMPLETED, 1);
    }

    /**
     * @desc used to know if minesweeper game was being played when user last left the app
     * @return true if app was closed without completing the minesweeper game
     */
    public boolean isMinesweeperOpened() {
        return pref.getBoolean(GAME_OPENED, false);
    }

    public void saveMinesweeperOpenedStatus(boolean isOpened) {
        editor.putBoolean(GAME_OPENED, isOpened);
        editor.clear();
        editor.commit();
    }
}
