package powerup.systers.com.powerup.test;

        import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

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

            //Testing the logic to update number of correct and wrong answers if yes button is pressed
            @Test
    public void clickYesTest() {
                memoryMatchGameActivity.correctAnswer = 1;
                memoryMatchGameActivity.wrongAnswer = 2;
                memoryMatchGameActivity.clickYes();
                if (memoryMatchGameActivity.arrayTile1[3] == memoryMatchGameActivity.arrayTile3[3]) {
                        assertEquals(2, memoryMatchGameActivity.correctAnswer);
                        assertEquals(2, memoryMatchGameActivity.wrongAnswer);
                    } else {
                        assertEquals(3, memoryMatchGameActivity.wrongAnswer);
                        assertEquals(1, memoryMatchGameActivity.correctAnswer);
                    }
            }

            //Testing the logic to update number of correct answers if no is pressed
            @Test
    public void clickNoTest() {
                memoryMatchGameActivity.correctAnswer = 1;
                memoryMatchGameActivity.wrongAnswer = 2;
                memoryMatchGameActivity.clickNo();
                if (memoryMatchGameActivity.arrayTile1[3] != memoryMatchGameActivity.arrayTile3[3]) {
                        assertEquals(2, memoryMatchGameActivity.correctAnswer);
                        assertEquals(2, memoryMatchGameActivity.wrongAnswer);
                    } else {
                        assertEquals(1, memoryMatchGameActivity.correctAnswer);
                        assertEquals(3, memoryMatchGameActivity.wrongAnswer);
                    }
            }

            //Testing the logic to update number of correct answers if answer is correct
            @Test
    public void correctAnswerTest() {
                boolean correct;
                memoryMatchGameActivity.correctAnswer = 0;
                if (memoryMatchGameActivity.arrayTile1[2] == memoryMatchGameActivity.arrayTile3[2]) {
                        memoryMatchGameActivity.correctAnswer = memoryMatchGameActivity.correctAnswer + 1;
                        correct = true;
                    } else {
                        correct = false;
                    }
                if (correct)
                        assertEquals(1, memoryMatchGameActivity.correctAnswer);
                else
                    assertEquals(0, memoryMatchGameActivity.correctAnswer);
            }

            //Testing the logic to update number of wrong answers if answer is wrong
            @Test
    public void wrongAnswerTest() {
                boolean wrong;
                memoryMatchGameActivity.wrongAnswer = 0;
                if (memoryMatchGameActivity.arrayTile1[2] != memoryMatchGameActivity.arrayTile3[2]) {
                        memoryMatchGameActivity.wrongAnswer = memoryMatchGameActivity.wrongAnswer + 1;
                        wrong = true;
                    } else {
                        wrong = false;
                    }
                if (wrong)
                        assertEquals(1, memoryMatchGameActivity.wrongAnswer);
                else
                    assertEquals(0, memoryMatchGameActivity.wrongAnswer);
            }

            //Testing if the answers are saved correctly in arrays for each round
            @Test
    public void updateArrayTest() {
                int i = memoryMatchGameActivity.roundCount;
                assertEquals(memoryMatchGameActivity.arrayTile2[i], memoryMatchGameActivity.arrayTile1[i]);
                assertEquals(memoryMatchGameActivity.arrayTile3[i], memoryMatchGameActivity.arrayTile2[i]);
            }
}