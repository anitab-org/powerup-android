package powerup.systers.com.ui.map_screen;

import android.util.Log;

import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.data.entities.Scenario;

public class MapPresenter implements MapContract.IMapPresenter {

    private DataSource dataSource;
    private MapContract.IMapView view;

    public MapPresenter(DataSource dataSource, MapContract.IMapView view) {
        this.dataSource = dataSource;
        this.view = view;
    }

    @Override
    public void checkCompletion() {
        dataSource.getScenarioFromId(4, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                Log.v("MAP ACTIVITY:: ", " " + scenario);
                if ((scenario.getCompleted() == 1) || SessionHistory.sceneHomeIsReplayed) {
                    view.setSchool();
                }
            }
        });
        dataSource.getScenarioFromId(5, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                if ((scenario.getCompleted() == 1) || SessionHistory.sceneSchoolIsReplayed) {
                    view.setHospital();
                }
            }
        });
        dataSource.getScenarioFromId(6, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                if ((scenario.getCompleted() == 1) || SessionHistory.sceneHospitalIsReplayed) {
                    view.setLibrary();
                }
            }
        });
    }
}
