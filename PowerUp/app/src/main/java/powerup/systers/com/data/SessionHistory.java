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
    public static int eyesTotalNo = 8;
    public static int clothTotalNo = 49;
    public static int hairTotalNo = 17;
    public static int skinTotalNo = 16;
    public static boolean sceneHomeIsReplayed = false;
    public static boolean sceneSchoolIsReplayed = false;
    public static boolean sceneHospitalIsReplayed = false;
    public static boolean sceneLibraryIsReplayed = false;
    public static boolean sceneHomeLevel2IsReplayed = false;
    public static boolean sceneSchoolLevel2IsReplayed = false;
    public static boolean sceneHospitalLevel2IsReplayed = false;
    public static boolean sceneLibraryLevel2IsReplayed = false;
    public static boolean hasPreviouslyCustomized = false;
    public static boolean level1Completed = false;
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
    public static int progressHealth = 0;
    public static int progressHealing = 0;
    public static int progressInvisibility = 0;
    public static int progressTelepathy = 0;
}
