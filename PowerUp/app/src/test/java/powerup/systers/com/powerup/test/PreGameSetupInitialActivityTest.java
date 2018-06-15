package powerup.systers.com.powerup.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.powerup.PowerUpUtils;
import powerup.systers.com.pregame_setup.PreGameSetupInitialActivity;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

@RunWith(JUnit4.class)
public class PreGameSetupInitialActivityTest {

    @Mock
    private PreGameSetupInitialActivity mPreGameSetupInitialActivity;

    @Before
    public void setmPreGameSetupInitialActivity() {
        mPreGameSetupInitialActivity = new PreGameSetupInitialActivity();
    }

    //Checking initial values
    @Test
    public void checkInitialValues() {
        assertEquals(false, SessionHistory.hasPreviouslyCustomized);
        assertEquals(false, SessionHistory.adult1Chosen);
        assertEquals(false, SessionHistory.adult2Chosen);
        assertEquals(false, SessionHistory.child1Chosen);
        assertEquals(false, SessionHistory.child2Chosen);
        assertEquals(0, SessionHistory.selectedAdult1);
        assertEquals(0, SessionHistory.selectedAdult2);
        assertEquals(0, SessionHistory.selectedChild1);
        assertEquals(0, SessionHistory.selectedChild2);
    }

    //Checking values after first NPC is clicked
    @Test
    public void clickNpc1Test() {
        mPreGameSetupInitialActivity.clickNpc1();
        assertArrayEquals(PowerUpUtils.ADULT_IMAGES_1, SessionHistory.npcList);
        assertArrayEquals(PowerUpUtils.NPC_ADULT_IMAGES_1, SessionHistory.npcFullViewList);
        assertEquals(PowerUpUtils.NPC_ADULT_1, SessionHistory.characterType);
        assertEquals(true, SessionHistory.adult1Chosen);
        assertEquals(true, SessionHistory.characterChosen);
        assertEquals(SessionHistory.selectedAdult1, SessionHistory.selectedValue);
    }

    //Checking values after second NPC is clicked
    @Test
    public void clickNpc2Test() {
        mPreGameSetupInitialActivity.clickNpc2();
        assertArrayEquals(PowerUpUtils.ADULT_IMAGES_2, SessionHistory.npcList);
        assertArrayEquals(PowerUpUtils.NPC_ADULT_IMAGES_2, SessionHistory.npcFullViewList);
        assertEquals(PowerUpUtils.NPC_ADULT_2, SessionHistory.characterType);
        assertEquals(true, SessionHistory.adult2Chosen);
        assertEquals(true, SessionHistory.characterChosen);
        assertEquals(SessionHistory.selectedAdult2, SessionHistory.selectedValue);
    }

    //Checking values after third NPC is clicked
    @Test
    public void clickNpc3Test() {
        mPreGameSetupInitialActivity.clickNpc3();
        assertArrayEquals(PowerUpUtils.CHILD_IMAGES, SessionHistory.npcList);
        assertArrayEquals(PowerUpUtils.NPC_CHILD_IMAGES, SessionHistory.npcFullViewList);
        assertEquals(PowerUpUtils.NPC_CHILD_1, SessionHistory.characterType);
        assertEquals(true, SessionHistory.child1Chosen);
        assertEquals(true, SessionHistory.characterChosen);
        assertEquals(SessionHistory.selectedChild1, SessionHistory.selectedValue);
    }

    //Checking values after fourth NPC is clicked
    @Test
    public void clickNpc4Test() {
        mPreGameSetupInitialActivity.clickNpc4();
        assertArrayEquals(PowerUpUtils.CHILD_IMAGES, SessionHistory.npcList);
        assertArrayEquals(PowerUpUtils.NPC_CHILD_IMAGES, SessionHistory.npcFullViewList);
        assertEquals(PowerUpUtils.NPC_CHILD_2, SessionHistory.characterType);
        assertEquals(true, SessionHistory.child2Chosen);
        assertEquals(true, SessionHistory.characterChosen);
        assertEquals(SessionHistory.selectedChild2, SessionHistory.selectedValue);
    }
}
