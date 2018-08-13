package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.kill_the_virus_game.KillTheVirusGame;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class KillTheVirusGameTest {

    @Mock
    private
    KillTheVirusGame killTheVirusGame;

    @Before
    public void setUpActivity() {
        killTheVirusGame = new KillTheVirusGame();
    }

    @Test
    public void checkInitialValues() {
        assertEquals(0, killTheVirusGame.score);
        assertEquals(0, killTheVirusGame.hitCount);
        assertEquals(false, killTheVirusGame.correctHit);
    }

    @Test
    public void increasePoints() {
        int points = 5;
        points = killTheVirusGame.increasePoint(points);
        assertEquals(6, points);
    }

    @Test
    public void hitCountTest1() {
        killTheVirusGame.hitCount = 0;
        killTheVirusGame.incrementHitCount();
        assertEquals(1, killTheVirusGame.hitCount);
    }

    @Test
    public void hitCountTest2() {
        killTheVirusGame.hitCount = 1;
        killTheVirusGame.incrementHitCount();
        assertEquals(2, killTheVirusGame.hitCount);
    }

    @Test
    public void hitCountTest3() {
        killTheVirusGame.hitCount = 2;
        killTheVirusGame.incrementHitCount();
        assertEquals(3, killTheVirusGame.hitCount);
    }

    @Test
    public void hitCountTest4() {
        killTheVirusGame.hitCount = 3;
        killTheVirusGame.incrementHitCount();
        assertEquals(0, killTheVirusGame.hitCount);
    }
}