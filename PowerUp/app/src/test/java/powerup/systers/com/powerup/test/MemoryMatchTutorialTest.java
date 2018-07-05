package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.memory_match_game.MemoryMatchTutorialActivity;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MemoryMatchTutorialTest {

    @Mock
    private
    MemoryMatchTutorialActivity memoryMatchTutorialActivity;

    @Before
    public void setMemoryMatchTutorialActivity() {
        memoryMatchTutorialActivity = new MemoryMatchTutorialActivity();
    }

    @Test
    public void tutorial1Test() {
        memoryMatchTutorialActivity.startFromActivity = false;
        assertEquals(0, memoryMatchTutorialActivity.tutorialCount);
    }

    @Test
    public void tutorial2Test() {
        memoryMatchTutorialActivity.startFromActivity = false;
        memoryMatchTutorialActivity.initializeViews();
        assertEquals(1, memoryMatchTutorialActivity.tutorialCount);
    }

    @Test
    public void tutorial3Test() {
        memoryMatchTutorialActivity.startFromActivity = false;
        memoryMatchTutorialActivity.showTutorial2();
        assertEquals(2, memoryMatchTutorialActivity.tutorialCount);
    }

    @Test
    public void tutorialEndTest() {
        memoryMatchTutorialActivity.startFromActivity = false;
        memoryMatchTutorialActivity.showTutorial3();
        assertEquals(3, memoryMatchTutorialActivity.tutorialCount);
    }
}