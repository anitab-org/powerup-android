package powerup.systers.com.data.pref;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefHelper implements IPrefHelper {

    private static final String PREF_KEY_CHECK_FIRST_TIME = "IS_THIS_FIRST_RUN";
    private static final String PREF_KEY_PREVIOUSLY_CUSTOMIZED = "IS_PREVIOUSLY_CUSTOMIZED";

    private static final String PREF_KEY_CURRENT_EYE_VALUE = "CURRENT_EYE_VALUE";
    private static final String PREF_KEY_CURRENT_HAIR_VALUE = "CURRENT_HAIR_VALUE";
    private static final String PREF_KEY_CURRENT_SKIN_VALUE = "CURRENT_SKIN_VALUE";
    private static final String PREF_KEY_CURRENT_CLOTH_VALUE = "CURRENT_CLOTH_VALUE";
    private static final String PREF_KEY_CURRENT_ACCESSORY_VALUE = "CURRENT_ACCESSORY_VALUE";

    private final SharedPreferences mPrefs;

    public PrefHelper(Context context, String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public boolean checkFirstTime() {
        return mPrefs.getBoolean(PREF_KEY_CHECK_FIRST_TIME, true);
    }


    @Override
    public void setFirstTime(boolean value) {
        mPrefs.edit().putBoolean(PREF_KEY_CHECK_FIRST_TIME, value).apply();
    }

    @Override
    public boolean checkPreviouslyCustomized() {
        return mPrefs.getBoolean(PREF_KEY_PREVIOUSLY_CUSTOMIZED,false);
    }

    @Override
    public void setPreviouslyCustomized(boolean value) {
        mPrefs.edit().putBoolean(PREF_KEY_PREVIOUSLY_CUSTOMIZED,value).apply();
    }

    @Override
    public int getCurrentEyeValue() {
        return mPrefs.getInt(PREF_KEY_CURRENT_EYE_VALUE, 1);
    }

    @Override
    public void setCurrentEyeValue(int value) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_EYE_VALUE, value).apply();
    }

    @Override
    public int getCurrentSkinValue() {
        return mPrefs.getInt(PREF_KEY_CURRENT_SKIN_VALUE, 1);
    }

    @Override
    public void setCurrentSkinValue(int value) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_SKIN_VALUE, value).apply();
    }

    @Override
    public int getCurrentHairValue() {
        return mPrefs.getInt(PREF_KEY_CURRENT_HAIR_VALUE, 1);
    }

    @Override
    public void setCurrentHairValue(int value) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_HAIR_VALUE, value).apply();
    }

    @Override
    public int getCurrentClothValue() {
        return mPrefs.getInt(PREF_KEY_CURRENT_CLOTH_VALUE, 1);
    }

    @Override
    public void setCurrentClothValue(int value) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_CLOTH_VALUE, value).apply();
    }

    @Override
    public int getCurrentAccessoriesValue() {
        return mPrefs.getInt(PREF_KEY_CURRENT_ACCESSORY_VALUE, 0);
    }

    @Override
    public void setCurrentAccessoriesValue(int value) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_ACCESSORY_VALUE, value).apply();
    }

}
