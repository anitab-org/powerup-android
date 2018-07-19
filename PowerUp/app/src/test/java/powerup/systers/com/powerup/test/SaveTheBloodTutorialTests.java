package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.save_the_blood_game.SaveTheBloodTutorialActivity;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class SaveTheBloodTutorialTests {

    @Mock
    private SaveTheBloodTutorialActivity saveTheBloodTutorialActivity;

    @Before
    public void setSaveTheBloodTutorialActivity(){
        saveTheBloodTutorialActivity = new SaveTheBloodTutorialActivity();
    }

    @Test
    public void tutorial1Test() {
        saveTheBloodTutorialActivity.startFromActivity = false;
        assertEquals(0, saveTheBloodTutorialActivity.tutorialCount);
    }

    @Test
    public void tutorial2Test() {
        saveTheBloodTutorialActivity.startFromActivity = false;
        saveTheBloodTutorialActivity.initializeViews();
        assertEquals(1, saveTheBloodTutorialActivity.tutorialCount);
    }

    @Test
    public void tutorial3Test() {
        saveTheBloodTutorialActivity.startFromActivity = false;
        saveTheBloodTutorialActivity.showTutorial2();
        assertEquals(2, saveTheBloodTutorialActivity.tutorialCount);
    }

    @Test
    public void tutorialEndTest() {
        saveTheBloodTutorialActivity.startFromActivity = false;
        saveTheBloodTutorialActivity.showTutorial3();
        assertEquals(3, saveTheBloodTutorialActivity.tutorialCount);
    }
}
