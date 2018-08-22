package powerup.systers.com.ui.scenario_over_screen_level2;

import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.data.entities.Scenario;
import powerup.systers.com.ui.scenario_over_screen_level2.ScenarioOverLevel2Contract;

public class ScenarioOverLevel2Presenter implements ScenarioOverLevel2Contract.IScenarioOverLevel2Presenter {
    private ScenarioOverLevel2Contract.IScenarioOverLevel2View view;
    private DataSource source;

    ScenarioOverLevel2Presenter(ScenarioOverLevel2Contract.IScenarioOverLevel2View view, DataSource source) {
        this.view = view;
        this.source = source;
    }

    @Override
    public void loadScenario() {
        // setting value to current scene object & prevScene object
        source.getScenarioFromId(SessionHistory.currSessionID, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                view.setCurrentScenario(scenario);
            }
        });
        source.getScenarioFromId(SessionHistory.prevSessionID, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                view.setPrevScenario(scenario);
            }
        });

    }
}
