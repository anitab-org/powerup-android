package powerup.systers.com.powerup.test;

import android.content.Intent;
import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import powerup.systers.com.BuildConfig;
import powerup.systers.com.R;
import powerup.systers.com.ui.scenario_over_screen.ScenarioOverActivity;
import powerup.systers.com.minesweeper.MinesweeperGameActivity;
import powerup.systers.com.minesweeper.MinesweeperSessionManager;
import powerup.systers.com.minesweeper.ProsAndConsActivity;
import powerup.systers.com.utils.PowerUpUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sachinaggarwal on 02/07/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class ProsConsTests {

    ProsAndConsActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(ProsAndConsActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void setPro1TextProperly() {
        activity.completedRounds = 2;
        activity.setTexts();
        TextView textView = (TextView) activity.findViewById(R.id.pro_one);
        assertEquals(textView.getText().toString(), PowerUpUtils.ROUNDS_PROS_CONS[1][0]);
    }

    @Test
    public void setCon1TextProperly() {
        activity.completedRounds = 2;
        activity.setTexts();
        TextView textView = (TextView) activity.findViewById(R.id.con_one);
        assertEquals(textView.getText().toString(), PowerUpUtils.ROUNDS_PROS_CONS[1][2]);
    }

    @Test
    public void setPro2TextProperly() {
        activity.completedRounds = 2;
        activity.setTexts();
        TextView textView = (TextView) activity.findViewById(R.id.pro_two);
        assertEquals(textView.getText().toString(), PowerUpUtils.ROUNDS_PROS_CONS[1][1]);
    }

    @Test
    public void continueshouldLaunchMineActivity() {
        activity.completedRounds = PowerUpUtils.NUMBER_OF_ROUNDS - 1;
        Class Minesweeper = MinesweeperGameActivity.class;
        Intent expectedIntent = new Intent(activity, Minesweeper);

        activity.findViewById(R.id.continue_button).callOnClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(expectedIntent.filterEquals(actualIntent));
    }

    @Test
    public void continueshouldLaunchGameActivity() {
        activity.completedRounds = PowerUpUtils.NUMBER_OF_ROUNDS;
        Class ScenarioOver = ScenarioOverActivity.class;
        Intent expectedIntent = new Intent(activity, ScenarioOver);

        activity.findViewById(R.id.continue_button).callOnClick();
        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(expectedIntent.filterEquals(actualIntent));
    }

    @Test
    public void continueshouldUpdateGameOpenedState() {
        activity.completedRounds = PowerUpUtils.NUMBER_OF_ROUNDS;

        activity.findViewById(R.id.continue_button).callOnClick();

        assertEquals(false, new MinesweeperSessionManager(activity).isMinesweeperOpened());
    }

    @Test
    public void checkCompletedRounds() {
        activity.completedRounds = PowerUpUtils.NUMBER_OF_ROUNDS; //should fail for PowerUpUtils.NUMBER_OF_ROUNDS + 1

        assertTrue(activity.completedRounds <= PowerUpUtils.NUMBER_OF_ROUNDS);
    }
}
