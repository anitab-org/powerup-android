package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.kill_the_virus_game.KillTheVirusEndActivity;

@RunWith(JUnit4.class)

public class KillTheVirusEndTests {

    @Mock
    private
    KillTheVirusEndActivity killTheVirusEndActivity;

    @Before
    public void setKillTheVirusEndActivity() {
        killTheVirusEndActivity = new KillTheVirusEndActivity();
    }
}