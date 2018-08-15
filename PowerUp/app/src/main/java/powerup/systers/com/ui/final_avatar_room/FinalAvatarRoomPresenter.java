package powerup.systers.com.ui.final_avatar_room;

import android.content.Context;
import android.util.Log;

import powerup.systers.com.R;
import powerup.systers.com.data.DataSource;

public class FinalAvatarRoomPresenter implements FinalAvatarRoomContract.IAvatarRoomPresenter{

    private FinalAvatarRoomContract.IAvatarRoomView view;
    private Context context;

    public FinalAvatarRoomPresenter(FinalAvatarRoomContract.IAvatarRoomView view, DataSource source, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void calculateEyeValue(int value) {
        String eyeImageName = context.getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            view.updateAvatarEye(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            Log.e("FinalAvatarRoom", "Error due to :" + eyeImageName);
        }
    }

    @Override
    public void calculateHairValue(int value) {
        String hairImageName = context.getResources().getString(R.string.hair);
        hairImageName = hairImageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            view.updateAvatarHair(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            Log.e("FinalAvatarRoom", "Error due to :" + hairImageName);
        }
    }

    @Override
    public void calculateSkinValue(int value) {
        String skinmageName = context.getResources().getString(R.string.skin);
        skinmageName = skinmageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(skinmageName);
            view.updateAvatarSkin(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            Log.e("FinalAvatarRoom", "Error due to :" + skinmageName);
        }
    }

    @Override
    public void calculateClothValue(int value) {
        String clothImageName = context.getResources().getString(R.string.cloth);
        clothImageName = clothImageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            view.updateAvatarCloth(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            Log.e("FinalAvatarRoom", "Error due to :" + clothImageName);
        }
    }

    @Override
    public void setValues(int eye, int hair, int skin, int cloth) {
        calculateEyeValue(eye);
        calculateHairValue(hair);
        calculateSkinValue(skin);
        calculateClothValue(cloth);
    }
}
