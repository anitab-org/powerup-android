package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.memory_match_game.MemoryMatchEndActivity;

@RunWith(JUnit4.class)
public class MemoryMatchEndTest {

    @Mock
    private
    MemoryMatchEndActivity memoryMatchEndActivity;

    @Before
    public void setMemoryMatchEndActivity() {
        memoryMatchEndActivity = new MemoryMatchEndActivity();
    }
}