package powerup.systers.com.ui.map_screen_level2;

public interface MapLevel2Contract {
    interface IMapView {
        void setSchool();
        void setHospital();
        void setLibrary();
    }

    interface IMapPresenter{
        void checkCompletion();
    }
}
