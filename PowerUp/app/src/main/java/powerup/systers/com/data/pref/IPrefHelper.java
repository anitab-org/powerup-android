package powerup.systers.com.data.pref;

public interface IPrefHelper {
    boolean checkFirstTime();
    void setFirstTime(boolean value);
    boolean checkPreviouslyCustomized();
    void setPreviouslyCustomized(boolean value);
    int getCurrentEyeValue();
    void setCurrentEyeValue(int value);
    int getCurrentSkinValue();
    void setCurrentSkinValue(int value);
    int getCurrentHairValue();
    void setCurrentHairValue(int value);
    int getCurrentClothValue();
    void setCurrentClothValue(int value);
    int getCurrentAccessoriesValue();
    void setCurrentAccessoriesValue(int value);
}
