package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import java.util.ArrayList;
import powerup.systers.com.memory_match_game.MemoryMatchGameActivity;
import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class MemoryMatchGameTest {

    @Mock
    private
    MemoryMatchGameActivity memoryMatchGameActivity;

    @Before
    public void setMemoryMatchGameActivity() {
        memoryMatchGameActivity = new MemoryMatchGameActivity();
    }

    //Testing the logic when values are same is correct and yes is clicked
    @Test
    public void answerCorrectTest1() {
        memoryMatchGameActivity.arrayTile = new ArrayList<>();
        memoryMatchGameActivity.arrayTile.add(0);
        memoryMatchGameActivity.arrayTile.add(0);
        memoryMatchGameActivity.arrayTile.add(0);
        memoryMatchGameActivity.positionCount = 2;
        memoryMatchGameActivity.calledFromActivity = false;
        memoryMatchGameActivity.clickYes();
        assertEquals(1, memoryMatchGameActivity.correctAnswer);
        assertEquals(1, memoryMatchGameActivity.score);
        assertEquals(0, memoryMatchGameActivity.wrongAnswer);
    }

    //Testing the logic when values are different is correct and no is clicked
    @Test
    public void answerCorrectTest2(){
        memoryMatchGameActivity.arrayTile = new ArrayList<>();
        memoryMatchGameActivity.arrayTile.add(5);
        memoryMatchGameActivity.arrayTile.add(1);
        memoryMatchGameActivity.arrayTile.add(0);
        memoryMatchGameActivity.positionCount = 2;
        memoryMatchGameActivity.calledFromActivity = false;
        memoryMatchGameActivity.clickNo();
        assertEquals(1, memoryMatchGameActivity.correctAnswer);
        assertEquals(1, memoryMatchGameActivity.score);
        assertEquals(0, memoryMatchGameActivity.wrongAnswer);
    }

    //Testing the logic when values are same is correct and no is clicked
    @Test
    public void answerWrongTest1() {
        memoryMatchGameActivity.arrayTile = new ArrayList<>();
        memoryMatchGameActivity.arrayTile.add(0);
        memoryMatchGameActivity.arrayTile.add(5);
        memoryMatchGameActivity.arrayTile.add(5);
        memoryMatchGameActivity.positionCount = 2;
        memoryMatchGameActivity.calledFromActivity = false;
        memoryMatchGameActivity.clickNo();
        assertEquals(0, memoryMatchGameActivity.correctAnswer);
        assertEquals(0, memoryMatchGameActivity.score);
        assertEquals(1, memoryMatchGameActivity.wrongAnswer);
    }

    //Testing the logic when values are different is correct and yes is clicked
    @Test
    public void answerWrongTest2() {
        memoryMatchGameActivity.arrayTile = new ArrayList<>();
        memoryMatchGameActivity.arrayTile.add(0);
        memoryMatchGameActivity.arrayTile.add(1);
        memoryMatchGameActivity.arrayTile.add(5);
        memoryMatchGameActivity.positionCount = 2;
        memoryMatchGameActivity.calledFromActivity = false;
        memoryMatchGameActivity.clickYes();
        assertEquals(0, memoryMatchGameActivity.correctAnswer);
        assertEquals(0, memoryMatchGameActivity.score);
        assertEquals(1, memoryMatchGameActivity.wrongAnswer);
    }
}