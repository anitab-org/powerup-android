package powerup.systers.com.ui.scenario_over_screen;

import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.data.entities.Scenario;

public class ScenarioOverPresenter implements ScenarioOverContract.IScenarioOverPresenter {
    private ScenarioOverContract.IScenarioOverView view;
    private DataSource source;

    ScenarioOverPresenter(ScenarioOverContract.IScenarioOverView view, DataSource source) {
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
