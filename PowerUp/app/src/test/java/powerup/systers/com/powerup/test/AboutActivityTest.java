package powerup.systers.com.powerup.test;

import android.os.Build;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import powerup.systers.com.ui.AboutActivity;
import powerup.systers.com.BuildConfig;
import powerup.systers.com.R;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class AboutActivityTest {
    private AboutActivity activity;

    @Before
    public void setUp() {
        activity = Robolectric.buildActivity(AboutActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void clickingHomeButtonShouldFinishActivity() {
        activity.findViewById(R.id.homeButton).performClick();
        ShadowActivity activityShadow = shadowOf(activity);
        assertTrue(activityShadow.isFinishing());
    }

    @Test
    public void checkingTitleIsShownCorrectly() {
        TextView textView = (TextView) activity.findViewById(R.id.about_title);
        assertEquals(textView.getText().toString(),
                activity.getResources().getString(R.string.about_title));
    }

}
