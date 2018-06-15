package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.powerup.PowerUpUtils;
import powerup.systers.com.pregame_setup.PregameSetupActivity;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PregameSetupActivityTest {

    private int value = 4;

    @Mock
    private
    PregameSetupActivity mPregameSetupActivity;

    @Before
    public void setmPregameSetupActivity(){
        mPregameSetupActivity = new PregameSetupActivity();
    }

    @Test
    public void saveCharacterTest1(){
        mPregameSetupActivity.saveCharacter(PowerUpUtils.NPC_ADULT_1, value);
        assertEquals(value,SessionHistory.selectedAdult1);
    }

    @Test
    public void saveCharacterTest2(){
        mPregameSetupActivity.saveCharacter(PowerUpUtils.NPC_ADULT_2, value);
        assertEquals(value,SessionHistory.selectedAdult2);
    }

    @Test
    public void saveCharacterTest3(){
        mPregameSetupActivity.saveCharacter(PowerUpUtils.NPC_CHILD_1, 4);
        assertEquals(value,SessionHistory.selectedChild1);
    }

    @Test
    public void saveCharacterTest4(){
        mPregameSetupActivity.saveCharacter(PowerUpUtils.NPC_CHILD_2, value);
        assertEquals(value,SessionHistory.selectedChild2);
    }
}
