package powerup.systers.com.ui.map_screen_level2;

import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.data.entities.Scenario;
import powerup.systers.com.ui.map_screen.MapContract;
import powerup.systers.com.ui.map_screen_level2.MapLevel2Contract;

public class MapLevel2Presenter implements MapLevel2Contract.IMapPresenter {

    private DataSource dataSource;
    private MapContract.IMapView view;

    public MapLevel2Presenter(DataSource dataSource, MapContract.IMapView view) {
        this.dataSource = dataSource;
        this.view = view;
    }

    @Override
    public void checkCompletion() {
        dataSource.getScenarioFromId(4, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
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
