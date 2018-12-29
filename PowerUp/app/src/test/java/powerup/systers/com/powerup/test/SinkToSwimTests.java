package powerup.systers.com.powerup.test;

import android.content.Intent;
import android.os.Build;

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
import powerup.systers.com.utils.PowerUpUtils;
import powerup.systers.com.sink_to_swim_game.SinkToSwimEndActivity;
import powerup.systers.com.sink_to_swim_game.SinkToSwimGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by sachinaggarwal on 02/07/17.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
public class SinkToSwimTests {

    SinkToSwimGame activity;

    @Before
    public void setUp() {
        activity = Robolectric.setupActivity(SinkToSwimGame.class);
    }

    @Test
    public void shouldNotBeNull() {
        assertNotNull(activity);
    }

    @Test
    public void gameEndStartsSinkToSwimEndActivity() {
        Class SinkToSwimEnd = SinkToSwimEndActivity.class;
        Intent expectedIntent = new Intent(activity, SinkToSwimEnd);

        activity.gameEnd();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(expectedIntent.filterEquals(actualIntent));
    }

    @Test
    public void bringsPointerToInitialPosition() {
        activity.height = 1000;
        float expectedPosition = 100;

        activity.bringPointerAndBoatToInitial();

        float actualPosition = activity.pointer.getY();
        assertEquals(expectedPosition, actualPosition, 0f);
    }

    @Test
    public void bringsAvatarToInitialPosition() {
        activity.height = 1000;
        float expectedPosition = -50;

        activity.bringPointerAndBoatToInitial();

        float actualPosition = activity.boat.getY();
        assertEquals(expectedPosition, actualPosition, 0f);
    }

    @Test
    public void setButtonsEnabled() {
        boolean expected = false;

        activity.setButtonsEnabled(false);

        assertEquals(expected, activity.skipOption.isClickable());
        assertEquals(expected, activity.trueOption.isClickable());
        assertEquals(expected, activity.falseOption.isClickable());
    }

    @Test
    public void setButtonsEnabled2() {
        boolean expected = true;

        activity.setButtonsEnabled(true);

        assertEquals(expected, activity.skipOption.isClickable());
        assertEquals(expected, activity.trueOption.isClickable());
        assertEquals(expected, activity.falseOption.isClickable());
    }

    @Test
    public void TimerPausesCorrectly() {
        activity.onPause();
        assertEquals(null, activity.countDownTimer);
    }

    @Test
    public void bringsPointerUp1() {
        activity.height = 1000;
        float expectedPosition = 199;

        activity.pointer.setY(199);
        activity.animator = activity.boat.animate().setDuration(1000);
        activity.bringPointerAndAvatarUp();

        float actualPosition = activity.pointer.getY();
        assertEquals(expectedPosition, actualPosition, 0f);
    }

    @Test
    public void bringsAvatarUp1() {
        activity.height = 1000;
        float expectedPosition = activity.boat.getY();

        activity.pointer.setY(199);
        activity.animator = activity.pointer.animate().setDuration(1000);
        activity.bringPointerAndAvatarUp();

        float actualPosition = activity.boat.getY();
        assertEquals(expectedPosition, actualPosition, 0f);
    }

    @Test
    public void bringsPointerUp2() {
        activity.height = 1000;
        float expectedPosition = 101;

        activity.pointer.setY(201);
        activity.animator = activity.pointer.animate().setDuration(1000);
        activity.bringPointerAndAvatarUp();

        float actualPosition = activity.pointer.getY();
        assertEquals(expectedPosition, actualPosition, 0f);
    }

    @Test
    public void bringsAvatarUp2() {
        activity.height = 1000;
        float expectedPosition = activity.boat.getY() - 66;

        activity.pointer.setY(201);
        activity.animator = activity.pointer.animate().setDuration(1000);
        activity.bringPointerAndAvatarUp();

        float actualPosition = activity.boat.getY();
        assertEquals(expectedPosition, actualPosition, 1f);
    }

    @Test
    public void bringsPointerDown() {
        activity.height = 1000;
        activity.speed = 2;
        float expectedPosition = activity.pointer.getY() + 20;

        activity.bringPointerAndAvatarDown();

        float actualPosition = activity.pointer.getY();
        assertEquals(expectedPosition, actualPosition, 1f);
    }

    @Test
    public void bringsAvatarDown() {
        activity.height = 1000;
        activity.speed = 3;
        float expectedPosition = activity.boat.getY() + 20;

        activity.bringPointerAndAvatarDown();

        float actualPosition = activity.boat.getY();
        assertEquals(expectedPosition, actualPosition, 1f);
    }

    @Test
    public void gameOverOnQuestionsFinished() {
        activity.curQuestion = PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS.length - 1;
        Class SinkToSwimEnd = SinkToSwimEndActivity.class;
        Intent expectedIntent = new Intent(activity, SinkToSwimEnd);

        activity.showNextQuestion();

        ShadowActivity shadowActivity = Shadows.shadowOf(activity);
        Intent actualIntent = shadowActivity.getNextStartedActivity();
        assertTrue(expectedIntent.filterEquals(actualIntent));
    }

    @Test
    public void setScoreIncrementsForCorrectAnswer() {
        activity.curQuestion = 0;
        activity.score = 0;
        String correctAnswer = "F";

        activity.answerChosen(activity.findViewById(R.id.false_option));

        assertEquals("Score: 1", activity.scoreView.getText().toString());
    }

    @Test
    public void scoreNotIncrementsOnWrongAnswer() {
        activity.curQuestion = 0;
        activity.score = 0;
        String correctAnswer = "F";

        activity.answerChosen(activity.findViewById(R.id.true_option));

        assertEquals("Score: 0", activity.scoreView.getText().toString());
    }

    @Test
    public void nextQuestionShowsUpOnAnswering() {
        activity.curQuestion = 0;

        activity.answerChosen(activity.findViewById(R.id.true_option));

        assertEquals(PowerUpUtils.SWIM_SINK_QUESTION_ANSWERS[1][0], activity.questionView.getText().toString());
    }

}
