package powerup.systers.com.ui.scenario_over_screen_level2;

import powerup.systers.com.data.entities.Scenario;

public interface ScenarioOverLevel2Contract {
    interface IScenarioOverLevel2View {
        void setCurrentScenario(Scenario scenario);
        void setPrevScenario(Scenario scenario);
    }

    interface IScenarioOverLevel2Presenter {
        void loadScenario();
    }
}
