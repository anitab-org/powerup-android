package powerup.systers.com.powerup.test;

import android.content.Intent;
import android.os.Build;
import android.widget.ImageView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import powerup.systers.com.BuildConfig;
import powerup.systers.com.R;
import powerup.systers.com.ui.scenario_over_screen.ScenarioOverActivity;
import powerup.systers.com.utils.PowerUpUtils;
import powerup.systers.com.vocab_match_game.VocabMatchEndActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sachinaggarwal on 21/08/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class VocabMatchEndTests {
    
    private ActivityController<VocabMatchEndActivity> controller;
    private VocabMatchEndActivity activity;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(VocabMatchEndActivity.class);
    }


    private void createWithIntent(int extra) {
        Intent intent = new Intent(RuntimeEnvironment.application, VocabMatchEndActivity.class);
        intent.putExtra(PowerUpUtils.SCORE, extra);
        activity = controller
                .withIntent(intent)
                .create()
                .start()
                .resume()
                .visible()
                .get();
    }


    @Test
    public void createsAndDestroysActivity() {
        createWithIntent(12);
    }


    @After
    public void tearDown() {
        controller
                .pause()
                .stop()
                .destroy();
    }

    @Test
    public void shouldNotBeNull() {

        createWithIntent(12);

        assertNotNull(activity);
    }

    @Test
    public void scoreDisplayedCorrectly(){

       int expectedScore = 10;
        createWithIntent(expectedScore);

        String actualScore = activity.scoreView.getText().toString();
        assertEquals("10",actualScore);
    }

    @Test
    public void correctAnswerDisplayedCorrectly(){

        int expectedCorrect = 5;
        createWithIntent(expectedCorrect);

        String actualCorrect = activity.correctView.getText().toString();
        assertEquals("5",actualCorrect);
    }

    @Test
    public void wrongAnswerDisplayedCorrectly(){

        int expectedScore = 2;
        int total = PowerUpUtils.VOCAB_TILES_IMAGES.length;
        int expectedWrong = total - expectedScore;
        createWithIntent(expectedScore);

        String actualWrong = activity.wrongView.getText().toString();
        assertEquals(""+expectedWrong,actualWrong);
    }

    @Test
    public void launchesNextActivityCorrectly(){
        createWithIntent(5);
        Class ScenarioOver = ScenarioOverActivity.class;
        Intent expectedIntent = new Intent(activity, ScenarioOver);

        ImageView continueButton = (ImageView) activity.findViewById(R.id.continue_button);
        continueButton.callOnClick();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(expectedIntent.filterEquals(actualIntent));
    }
}

