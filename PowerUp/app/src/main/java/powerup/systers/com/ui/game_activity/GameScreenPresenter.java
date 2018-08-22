package powerup.systers.com.ui.game_activity;

import android.content.Context;
import android.util.Log;

import java.util.List;

import powerup.systers.com.R;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.IDataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.data.entities.Answer;
import powerup.systers.com.data.entities.Scenario;

public class GameScreenPresenter implements GameScreenContract.IGameScreenPresenter {
    private GameScreenContract.IGameScreenView view;
    private DataSource source;
    private Context context;

    GameScreenPresenter(GameScreenContract.IGameScreenView view, DataSource source, Context context) {
        this.view = view;
        this.source = source;
        this.context = context;
    }

    @Override
    public void calculateEyeValue(int value) {
        String eyeImageName = context.getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            view.updateAvatarEye(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            Log.e("GameScreenPresenter", "Error due to :" + eyeImageName);
        }
    }

    @Override
    public void calculateHairValue(int value) {
        String hairImageName = context.getResources().getString(R.string.hair);
        hairImageName = hairImageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            view.updateAvatarHair(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            Log.e("GameScreenPresenter", "Error due to :" + hairImageName);
        }
    }

    @Override
    public void calculateSkinValue(int value) {
        String skinmageName = context.getResources().getString(R.string.skin);
        skinmageName = skinmageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(skinmageName);
            view.updateAvatarSkin(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            Log.e("GameScreenPresenter", "Error due to :" + skinmageName);
        }
    }

    @Override
    public void calculateClothValue(int value) {
        String clothImageName = context.getResources().getString(R.string.cloth);
        clothImageName = clothImageName + value;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            view.updateAvatarCloth(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            Log.e("GameScreenPresenter", "Error due to :" + clothImageName);
        }
    }

    @Override
    public void setValues() {
        calculateEyeValue(source.getCurrentEyeValue());
        calculateClothValue(source.getCurrentClothValue());
        calculateHairValue(source.getCurrentHairValue());
        calculateSkinValue(source.getCurrentSkinValue());
    }

    @Override
    public void getScenarioBackground() {
        source.getScenarioFromId(SessionHistory.currSessionID, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                view.setScenarioBackground(scenario.getScenarioId()-1);
                view.updateScenarioFromDatabase(scenario);
            }
        });
    }

    @Override
    public void loadScenarioFromDatabase() {
        source.getScenarioFromId(SessionHistory.currSessionID, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                view.updateScenarioFromDatabase(scenario);
            }
        });
    }

    @Override
    public void loadQuestion() {
        source.getCurrentQuestion(new IDataSource.LoadStringCallBack() {
            @Override
            public void onDataLoaded(String data) {
                view.updateQuestion(data);
            }
        });
    }

    @Override
    public void loadAnswer() {
        source.getAnswerList(SessionHistory.currQID, new IDataSource.LoadAnswerListCallBack() {
            @Override
            public void onDataLoaded(List<Answer> dataList) {
                view.updateAnswer(dataList);
            }
        });

    }

    @Override
    public void getPreviousScene(int id, final int type) {
        source.getScenarioFromId(id, new IDataSource.LoadScenarioCallBack() {
            @Override
            public void onScenarioLoaded(Scenario scenario) {
                view.setPrevScene(scenario, type);
            }
        });
    }
}