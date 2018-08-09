package powerup.systers.com.ui.game_activity;

import java.util.List;

import powerup.systers.com.data.entities.Answer;
import powerup.systers.com.data.entities.Scenario;

public interface GameScreenContract {
    interface IGameScreenView {
        void updateAvatarEye(int eye);
        void updateAvatarCloth(int cloth);
        void updateAvatarHair(int hair);
        void updateAvatarSkin(int skin);
        void updateScenarioFromDatabase(Scenario scenario);
        void setScenarioBackground(int id);
        void updateQuestion(String question);
        void updateAnswer(List<Answer> dataList);
        void setPrevScene(Scenario scenario, int type);
    }

    interface IGameScreenPresenter {
        void calculateEyeValue(int value);
        void calculateHairValue(int value);
        void calculateSkinValue(int value);
        void calculateClothValue(int value);
        void setValues();
        void getScenarioBackground();
        void loadQuestion();
        void loadAnswer();
        void getPreviousScene(int id, int type);
        void loadScenarioFromDatabase();
    }
}
