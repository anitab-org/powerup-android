package powerup.systers.com.ui.store_screen_level2;

import java.util.List;

import powerup.systers.com.data.StoreItem;

public interface StoreLevel2Contract {
    interface IStoreLevel2View {
        // update Avatar
        void updateAvatarEye(int eye);
        void updateAvatarCloth(int cloth);
        void updateAvatarHair(int hair);
        void updateAvatarSkin(int skin);
        void updateAvatarAccessory(int accessory);
    }

    interface IStoreLevel2Presenter {
        // calculates resource Id & calls view consecutive view function
        void calculateEyeValue(int value);
        void calculateHairValue(int value);
        void calculateSkinValue(int value);
        void calculateClothValue(int value);
        void calculateAccessoryValue(int value);
        // used in init function - onCreate function
        void setValues();
        // creating datalist for gridview
        List<List<StoreItem>> createDataList();
    }
}