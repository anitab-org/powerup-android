package powerup.systers.com.ui.scenario_over_screen;

import powerup.systers.com.data.entities.Scenario;

public interface ScenarioOverContract {
    interface IScenarioOverView {
        void setCurrentScenario(Scenario scenario);
        void setPrevScenario(Scenario scenario);
    }

    interface IScenarioOverPresenter {
        void loadScenario();
    }
}
