package powerup.systers.com.ui.map_screen_level2;

public interface MapLevel2Contract {
    interface IMapLevel2View {
        void setSchool();
        void setHospital();
        void setLibrary();
    }

    interface IMapLevel2Presenter{
        void checkCompletion();
    }
}
