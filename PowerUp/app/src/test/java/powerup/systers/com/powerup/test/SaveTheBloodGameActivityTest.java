package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.save_the_blood_game.SaveTheBloodGameActivity;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SaveTheBloodGameActivityTest {

    @Mock
    private
    SaveTheBloodGameActivity saveTheBloodGameActivity;

    @Before
    public void setSaveTheBloodGameActivityTest() {
        saveTheBloodGameActivity = new SaveTheBloodGameActivity();
    }

    @Test
    public void testCorrectAnswer1() {
        saveTheBloodGameActivity.calledFromActivity = false;
        saveTheBloodGameActivity.count = 0;
        saveTheBloodGameActivity.checkAnswer(2);
        assertEquals(1, saveTheBloodGameActivity.correctAnswer);
        assertEquals(0, saveTheBloodGameActivity.wrongAnswer);
    }

    @Test
    public void testCorrectAnswer2() {
        saveTheBloodGameActivity.calledFromActivity = false;
        saveTheBloodGameActivity.count = 1;
        saveTheBloodGameActivity.checkAnswer(2);
        assertEquals(1, saveTheBloodGameActivity.correctAnswer);
        assertEquals(0, saveTheBloodGameActivity.wrongAnswer);
    }

    @Test
    public void testCorrectAnswer3() {
        saveTheBloodGameActivity.calledFromActivity = false;
        saveTheBloodGameActivity.count = 2;
        saveTheBloodGameActivity.checkAnswer(4);
        assertEquals(1, saveTheBloodGameActivity.correctAnswer);
        assertEquals(0, saveTheBloodGameActivity.wrongAnswer);
    }

    @Test
    public void testWrongAnswer1() {
        saveTheBloodGameActivity.calledFromActivity = false;
        saveTheBloodGameActivity.count = 3;
        saveTheBloodGameActivity.checkAnswer(4);
        assertEquals(0, saveTheBloodGameActivity.correctAnswer);
        assertEquals(1, saveTheBloodGameActivity.wrongAnswer);
    }

    @Test
    public void testWrongAnswer2() {
        saveTheBloodGameActivity.calledFromActivity = false;
        saveTheBloodGameActivity.count = 4;
        saveTheBloodGameActivity.checkAnswer(6);
        assertEquals(0, saveTheBloodGameActivity.correctAnswer);
        assertEquals(1, saveTheBloodGameActivity.wrongAnswer);
    }

    @Test
    public void testWrongAnswer3() {
        saveTheBloodGameActivity.calledFromActivity = false;
        saveTheBloodGameActivity.count = 5;
        saveTheBloodGameActivity.checkAnswer(4);
        assertEquals(0, saveTheBloodGameActivity.correctAnswer);
        assertEquals(1, saveTheBloodGameActivity.wrongAnswer);
    }
}