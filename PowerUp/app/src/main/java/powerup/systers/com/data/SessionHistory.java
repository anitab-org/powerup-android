/**
 * @desc sets up the default avatar features and scenario points upon beginning the app.
 */

package powerup.systers.com.data;

public class SessionHistory {
    public static int currQID = 1;
    public static int currSessionID = 1;
    public static int prevSessionID = -1;
    public static int totalPoints = 0;
    public static int currScenePoints = 0;
    public static int eyesTotalNo = 9;
    public static int clothTotalNo = 4;
    public static int hairTotalNo = 16;
    public static int skinTotalNo = 16;
    public static boolean sceneHomeIsReplayed = false;
    public static boolean sceneSchoolIsReplayed = false;
    public static boolean sceneHospitalIsReplayed = false;
    public static boolean sceneLibraryIsReplayed = false;
    public static boolean hasPreviouslyCustomized = false;
    public static int[] npcList;
    public static boolean adult1Chosen = false;
    public static boolean adult2Chosen = false;
    public static boolean child1Chosen = false;
    public static boolean child2Chosen = false;
    public static boolean characterChosen = false;
    public static int[] npcFullViewList;
    public static int characterType = 0;
    public static int selectedAdult1 = 0;
    public static int selectedAdult2 = 0;
    public static int selectedChild1 = 0;
    public static int selectedChild2 = 0;
    public static int selectedValue = 0;
}
