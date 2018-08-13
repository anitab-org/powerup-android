package powerup.systers.com.ui.map_screen;

public interface MapContract {
    interface IMapView {
        void setSchool();
        void setHospital();
        void setLibrary();
    }

    interface IMapPresenter{
        void checkCompletion();
    }
}
