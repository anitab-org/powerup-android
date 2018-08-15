package powerup.systers.com.ui.avatar_room;

public interface AvatarRoomContract {

    interface IAvatarRoomView {
        // update Avatar
        void updateAvatarEye(int eye);
        void updateAvatarCloth(int cloth);
        void updateAvatarHair(int hair);
        void updateAvatarSkin(int skin);
    }

    interface IAvatarRoomPresenter {
        // used in init function - onCreate function
        void setValues(int eye, int hair, int skin, int cloth);
        // calculates resource Id & calls view consecutive view function
        void calculateEyeValue(int value);
        void calculateHairValue(int value);
        void calculateSkinValue(int value);
        void calculateClothValue(int value);
    }
}
