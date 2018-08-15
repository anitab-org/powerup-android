package powerup.systers.com.minesweeper;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Random;

import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.ui.MinesweeperSound;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.utils.PowerUpUtils;

/**
 * Created by sachinaggarwal on 25/06/17.
 */

public class MinesweeperGameActivity extends AppCompatActivity {

    public HashSet<String> mines;
    public int score = 0;
    public int gameRound = 0;
    public int numRedMines;
    public int numSelectionsLeft;
    public TextView scoreTextView;
    public ImageView banner;
    public ImageView continueButton;
    public PorterDuffColorFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper_game);
        scoreTextView = (TextView) findViewById(R.id.minesweeper_score);
        banner = (ImageView) findViewById(R.id.banner);
        continueButton = (ImageView) findViewById(R.id.continue_button);
        continueButton.setClickable(false);
        mines = new HashSet<>();
        setUpGame();
    }

    /**
     * @desc
     * set up the score and number of completed rounds
     * sets the background image corresponding to contraceptive method for current round
     * sets red mines at random positions in board based on success percentage of contraceptive method of current round
     */
    public void setUpGame() {
        boolean calledByTutorialsActivity = getIntent().getBooleanExtra(PowerUpUtils.CALLED_BY, false); //tells if game is called for the first time
        numSelectionsLeft = PowerUpUtils.MAXIMUM_FLIPS_ALLOWED;

        if (!calledByTutorialsActivity) { //if called by previous round of minesweeper game

            //fetch previous round score and rounds completed from session database
            MinesweeperSessionManager session = new MinesweeperSessionManager(this);
            score = session.getScore();
            scoreTextView.setText("Score: " + score);
            gameRound = session.getCompletedRounds();
            ImageView background = (ImageView) findViewById(R.id.mine_background); //set background according to current contracpetive method
            background.setImageDrawable(getResources().getDrawable(PowerUpUtils.ROUND_BACKGROUNDS[gameRound]));
            mines.clear();
        }
        gameRound++;
        numRedMines = (PowerUpUtils.ROUNDS_FAILURE_PERCENTAGES[gameRound - 1] * PowerUpUtils.NUMBER_OF_CELLS) / 100;
        while (mines.size() != numRedMines) { //add red mines randomly and store them in mines
            Random random = new Random();
            mines.add(PowerUpUtils.ID_REFERENCE + Math.abs(random.nextInt() % 25));
        }
    }

    /**
     * @desc
     * called when user opens any mine from the board
     * flips animation on the opened mine
     * @param view mine which is opened
     */
    public void openMine(final View view) {
        final ImageView imageView = (ImageView) view;

        //flip animation
        imageView.setRotationY(0f);
        imageView.animate().setDuration(100).rotationY(90f).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mines.contains(view.getResources().getResourceName(view.getId()))) { //red mine is opened
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.red_star));
                    openedRedMine();
                } else {
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.green_star));
                    openedGreenMine();
                }
                imageView.setRotationY(270f);
                imageView.animate().rotationY(360f).setListener(null);
                imageView.setClickable(false);
                PowerUpUtils.sPauseTest = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    /**
     * @desc the opened mine is red, show failure banner
     */
    public void openedRedMine() {
        playSound(MinesweeperSound.TYPE_INCORRECT);
        showBanner(1);
    }

    /**
     * @desc
     * opened mine is green, increment the score
     * includes zoom in and out bounce animation on score
     */
    public void openedGreenMine() {
        playSound(MinesweeperSound.TYPE_CORRECT);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.zoom_in);
        scoreTextView.startAnimation(animation);
        score++;
        scoreTextView.setText("Score: " + score);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.zoom_out);
        scoreTextView.startAnimation(animation);
        numSelectionsLeft--; //decrement the number of selection left
        if (numSelectionsLeft == 0) {
            showBanner(0); //if no more selections left, round completes, show success banner
        }
    }

    /**
     * @desc
     * shows the failure or success banner based on last selection
     * includes fade in animation on the banner and continue button
     * @param type type of banner, 1 signify failure, 0 signify success
     */
    public void showBanner(int type) {
        banner = (ImageView) findViewById(R.id.banner);
        Drawable drawable = type == 1 ? getResources().getDrawable(R.drawable.failure_banner) : getResources().getDrawable(R.drawable.success_banner);
        banner.setImageDrawable(drawable);
        banner.animate().setDuration(1500).alpha(0.95f).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                continueButton.setAlpha(1f);
                PowerUpUtils.sPauseTest = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
        continueButton.setClickable(true);
        showOriginalMines();//shows original placement of red and green mines to user with grey out animation
    }

    /**
     * @desc
     * shows the original placement of red mines and green mines to user
     */
    public void showOriginalMines() {
        ImageView mine;
        Drawable drawable;
        for (int id : PowerUpUtils.minesViews) {
            mine = (ImageView) findViewById(id);
            if (mines.contains(getResources().getResourceName(id))) {
                drawable = getResources().getDrawable(R.drawable.red_star);
            } else {
                drawable = getResources().getDrawable(R.drawable.green_star);
                setImageButtonEnabled(this, false, mine, drawable); //calls grey out animation on all green mines
            }
            mine.setImageDrawable(drawable);
            mine.setEnabled(false);
        }
    }

    /**
     * @desc Adds a greying out animation on the green mines when round completes
     * reduces the alpha value and multiplies RGB components of image colors with grey color
     * @param enabled false if mine is disabled i.e. greyed out animation
     * @param item mine imageview which has to be greyed out
     */
    public void setImageButtonEnabled(Context ctxt, boolean enabled, ImageView item, Drawable originalIcon) {
        item.setAlpha(0.8f);
        Drawable res = originalIcon.mutate();
        if (enabled) {
            res.setColorFilter(null);
        } else {
            filter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            res.setColorFilter(filter);
        }
    }

    /**
     * @desc
     * called when continue button is pressed in minesweeper game
     * updated the data from this round into session database and opens pros and cons screen
     * includes fade in and fade out activity transition
     */
    public void continuePressed(View view) {
        MinesweeperSessionManager session = new MinesweeperSessionManager(this);
        session.saveData(score, gameRound);
        SessionHistory.totalPoints += score;
        SessionHistory.currScenePoints += score;
        finish();
        startActivity(new Intent(MinesweeperGameActivity.this, ProsAndConsActivity.class));
        overridePendingTransition(R.animator.fade_in, R.animator.fade_out);
    }

    private void playSound(int sound) {
        Intent intent = new Intent(this, MinesweeperSound.class)
                .putExtra(MinesweeperSound.SOUND_TYPE_EXTRA, sound);
        startService(intent);
    }

  /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(MinesweeperGameActivity.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}




