/**
 * @desc sets up the default avatar features and scenario points upon beginning the app.
 */

package powerup.systers.com.datamodel;

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
    public static int glassesTotalNo = 3;
    public static int bagTotalNo = 3;
    public static int hatTotalNo = 4;
    public static int necklaceTotalNo = 4;
    public static int accessoryTotalNo = 4;
    public static boolean sceneHomeIsReplayed = false;
    public static boolean sceneSchoolIsReplayed = false;
    public static boolean sceneHospitalIsReplayed = false;
    public static boolean sceneLibraryIsReplayed = false;
    public static boolean hasPreviouslyCustomized = false;
}
