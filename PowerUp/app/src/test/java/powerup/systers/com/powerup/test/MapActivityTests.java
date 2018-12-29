package powerup.systers.com.powerup.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.util.ActivityController;

import powerup.systers.com.BuildConfig;
import powerup.systers.com.ui.game_activity.GameActivity;
import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.ui.scenario_over_screen.ScenarioOverActivity;
import powerup.systers.com.ui.StartActivity;
import powerup.systers.com.ui.store_screen.StoreActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@LargeTest
public class MapActivityTests {
    private ActivityController<MapActivity> controller;
    private MapActivity activity;
    private DatabaseHandler databaseHandler;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(MapActivity.class);
        activity = controller.get();

        databaseHandler = new DatabaseHandler(activity);
        databaseHandler.open();

        resetState();
    }

    @After
    public void tearDown() {
        databaseHandler.close();

        controller = null;
        activity = null;
        databaseHandler = null;
    }

    @Test
    public void shouldStartStore() {
        activity = controller.create().get();

        activity.findViewById(R.id.store).performClick();
        checkStartedActivity(StoreActivity.class);
    }

    @Test
    public void shouldStartHome() {
        activity = controller.create().get();

        activity.findViewById(R.id.home_button).performClick();
        checkStartedActivity(StartActivity.class);
    }

    @Test
    public void shouldStartGameActivity() {
        activity = controller.create().get();

        activity.findViewById(R.id.house).performClick();
        checkStartedActivity(GameActivity.class);
    }

    @Test
    public void shouldStartScenarioOverActivity(){
        setScenarioCompleted(4);
        activity = controller.create().get();

        activity.findViewById(R.id.house).performClick();
        checkStartedActivity(ScenarioOverActivity.class);
    }

    @Test
    public void shouldEnableScenariosOnProgress() {
        setScenarioCompleted(4);
        activity = controller.create().get();

        View school = activity.findViewById(R.id.school);

        assertTrue(school.isEnabled());

        school.performClick();
        checkStartedActivity(GameActivity.class);
    }

    @Test
    public void shouldDisableNotReachedScenario() {
        activity = controller.create().get();

        View school = activity.findViewById(R.id.school);
        school.performClick();

        assertFalse(school.isEnabled());
        assertNull(getStartedActivity());
    }

    private void checkStartedActivity(Class<? extends Activity> activityClass) {
        Intent expected = new Intent(activity, activityClass);
        Intent actual = getStartedActivity();

        assertEquals(expected.getComponent(), actual.getComponent());
    }

    private Intent getStartedActivity() {
        ShadowActivity shadowActivity = shadowOf(activity);

        Intent actual = shadowActivity.getNextStartedActivity();
        if (actual == null) {
            ShadowActivity.IntentForResult forResult = shadowActivity.getNextStartedActivityForResult();

            if (forResult != null)
                actual = forResult.intent;
        }

        return actual;
    }

    private void setScenarioCompleted(int scenarioId) {
        databaseHandler.open();
        databaseHandler.setCompletedScenario(scenarioId);
    }

    private void resetState() {
        databaseHandler.updateComplete();
    }
}