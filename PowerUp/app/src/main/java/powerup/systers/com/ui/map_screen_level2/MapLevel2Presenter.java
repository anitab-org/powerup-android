package powerup.systers.com.ui.map_screen_level2;

import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.data.entities.Scenario;

public class MapLevel2Presenter implements MapLevel2Contract.IMapLevel2Presenter {

    private DataSource dataSource;
    private MapLevel2Contract.IMapLevel2View view;

    public MapLevel2Presenter(DataSource dataSource, MapLevel2Contract.IMapLevel2View view) {
        this.dataSource = dataSource;
        this.view = view;
    }

    @Override
    public void checkCompletion() {
        dataSource.getScenarioFromId(8, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                if ((scenario.getCompleted() == 1) || SessionHistory.sceneHomeLevel2IsReplayed) {
                    view.setSchool();
                }
            }
        });
        dataSource.getScenarioFromId(9, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                if ((scenario.getCompleted() == 1) || SessionHistory.sceneSchoolLevel2IsReplayed) {
                    view.setHospital();
                }
            }
        });
        dataSource.getScenarioFromId(10, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                if ((scenario.getCompleted() == 1) || SessionHistory.sceneHospitalLevel2IsReplayed) {
                    view.setLibrary();
                }
            }
        });
    }
}
