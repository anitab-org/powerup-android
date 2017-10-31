package powerup.systers.com.powerup;

import android.graphics.drawable.Drawable;

import powerup.systers.com.R;

/**
 * Created by sachinaggarwal on 16/06/17.
 */

public class PowerUpUtils {

    public static final int NUMBER_OF_ROUNDS = 1;
    public static final int NUMBER_OF_CELLS = 25;
    public static final int MAXIMUM_FLIPS_ALLOWED = 5;
    public static final int RED_BANNER = 1;
    public static final int GREEN_BANNER = 0;

    public static final String SCORE = "score";
    public static final String CORRECT_ANSWERS = "correct";
    public static final String WRONG_ANSWER = "wrong";
    public static final String MAP = "map";
    public static final String SOURCE = "source";


    public static final String CALLED_BY = "GAME_ACTIVITY";
    public static final String ID_REFERENCE = "powerup.systers.com.powerup:id/imageView";
    public static final String ID_REF = "powerup.systers.com:id/imageView";
    public static final String MINESWEEP_PREVIOUS_SCENARIO = "School";

    public static final int[] ROUNDS_FAILURE_PERCENTAGES = {18, 20, 25};
    public static final int[] ROUND_BACKGROUNDS = {R.drawable.minesweeper_condom_background, R.drawable.minesweeper_condom_background, R.drawable.minesweeper_condom_background};
    public static final int[] minesViews = {R.id.imageView12, R.id.imageView0, R.id.imageView7, R.id.imageView22, R.id.imageView16, R.id.imageView19, R.id.imageView1,
            R.id.imageView2, R.id.imageView20, R.id.imageView13, R.id.imageView18, R.id.imageView23, R.id.imageView4, R.id.imageView8, R.id.imageView15, R.id.imageView21,
            R.id.imageView24, R.id.imageView3, R.id.imageView10, R.id.imageView17, R.id.imageView14, R.id.imageView5, R.id.imageView9, R.id.imageView6, R.id.imageView11};

    public static final String[][] ROUNDS_PROS_CONS = {{"=> Better Success Rate ", "=> Cheapest Contraceptive", "=> No protection against STDs"},
            {"=> Better Success Rate2", "=> Cheapest Contraceptive2", "=> No protection against STDs2"},
            {"=> Better Success Rate3", "=> Cheapest Contraceptive3", "=> No protection against STDs3"}};

    public static volatile boolean sPauseTest = true;
    public static final String[][] SWIM_SINK_QUESTION_ANSWERS = {{"Sex is good","F"},{"We should use condom","T"},{"Sachin designed this game","T"},{"Sachin is working on Conference Android Project","F"},{"Google is future's past","F"}};
    public static final int[] SWIM_TUTS = {R.drawable.swim_tut1,R.drawable.swim_tut2,R.drawable.swim_tut3};
    public static final int[] SCENARIO_BACKGROUNDS = {R.drawable.background,R.drawable.background,R.drawable.background,R.drawable.classroom,R.drawable.dressing_room,R.drawable.hospital,R.drawable.library};

    public static final int[] VOCAB_TILES_IMAGES = {R.drawable.vocab_tile1,R.drawable.vocab_tile2,R.drawable.vocab_tile3,R.drawable.vocab_cramping_tile,R.drawable.vocab_deo_tile,R.drawable.vocab_depression_tile,R.drawable.vocab_fat,R.drawable.vocab_skinny,R.drawable.vocab_tampon};
    public static final String[] VOCAB_MATCHES_BOARDS_TEXTS = {"Periods","Pimples","Bra","Cramping","Deo","Depression","Fat","Skinny","Tampon"};
    public static final int[] VOCAB_MATCH_TUTS = {R.drawable.vocab_tut1,R.drawable.vocab_tut2};
}
