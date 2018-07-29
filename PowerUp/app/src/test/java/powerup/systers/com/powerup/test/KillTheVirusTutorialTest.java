package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.kill_the_virus_game.KillTheVirusTutorials;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class KillTheVirusTutorialTest {

    @Mock
    private KillTheVirusTutorials mKillTheVirusTutorials;

    @Before
    public void setupActivity() {
        mKillTheVirusTutorials = new KillTheVirusTutorials();
    }

    @Test
    public void tutorial1Test() {
        assertEquals(mKillTheVirusTutorials.tutorialCount, 0);
    }

    @Test
    public void tutorial2Test() {
        mKillTheVirusTutorials.initializeViews();
        assertEquals(mKillTheVirusTutorials.tutorialCount, 1);
    }

    @Test
    public void tutorial3Test() {
        mKillTheVirusTutorials.showTutorial2();
        assertEquals(mKillTheVirusTutorials.tutorialCount, 2);
    }
}