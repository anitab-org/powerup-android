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
    public void setUpActivity(){
        killTheVirusGame = new KillTheVirusGame();
    }

    @Test
    public void checkInitialValues(){
        assertEquals(0, killTheVirusGame.score);
        assertEquals(false, killTheVirusGame.correctHit);
    }

    @Test
    public void increasePoints(){
        int points = 5;
        points = killTheVirusGame.increasePoint(points);
        assertEquals(6,points);
    }

    @Test
    public void checkCorrectHit(){
        killTheVirusGame.rightHit();
        assertEquals(true, killTheVirusGame.correctHit);
    }

    @Test
    public void checkWrongHit(){
        killTheVirusGame.wrongHit();
        assertEquals(false, killTheVirusGame.correctHit);
    }
}

