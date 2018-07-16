package powerup.systers.com.data;

import android.support.annotation.NonNull;

import java.util.List;

import powerup.systers.com.data.entities.Answer;
import powerup.systers.com.data.entities.Scenario;
import powerup.systers.com.data.pref.IPrefHelper;

public interface IDataSource extends IPrefHelper {

    interface LoadAnswerListCallBack {
        void onDataLoaded(List<Answer> dataList);
    }

    interface LoadStringCallBack {
        void onDataLoaded(String data);
    }

    interface LoadScenarioCallBack {
        void onScenarioLoaded(Scenario scenario);
    }

    interface LoadBooleanCallback {
        void onResultLoaded(boolean value);
    }

    interface LoadIntegerCallback {
        void onResultLoaded(int value);
    }

    void readAndInsertCSVData();

    void insertPointsAndAvatarData();

    void getAnswerList(int questionId, @NonNull LoadAnswerListCallBack callBack);

    void getCurrentQuestion(@NonNull LoadStringCallBack callBack);

    void getCurrentScenario(@NonNull LoadScenarioCallBack callBack);

    void getScenarioFromId(int scenarioId, @NonNull LoadScenarioCallBack callBack);

    void setSessionId(String scenarioName, @NonNull LoadBooleanCallback callback);

    void setCompletedScenario(int id);

    void setReplayedScenario(String scenarioName);

    void getAvatarSkin(@NonNull LoadIntegerCallback callback);

    void setAvatarSkin(int face);

    void getAvatarEye(@NonNull LoadIntegerCallback callback);

    void setAvatarEye(int eye);

    void getAvatarCloth(@NonNull LoadIntegerCallback callback);

    void setAvatarCloth(int cloth);

    void getAvatarHair(@NonNull LoadIntegerCallback callback);

    void setAvatarHair(int hair);

    void getAvatarAccessory(@NonNull LoadIntegerCallback callback);

    void setAvatarAccessory(int id);

    void getPurchasedClothes(int id, @NonNull LoadIntegerCallback callback);

    void setPurchasedClothes(int clothes);

    void getPurchasedHair(int id, @NonNull LoadIntegerCallback callback);

    void setPurchasedHair(int hair);

    void getPurchasedAccessories(int id, @NonNull LoadIntegerCallback callback);

    void setPurchasedAccessories(int id);

    void updateComplete();

    void updateReplayed();

    void resetCompleted(int id);

    void resetReplayed(int id);

    void resetPurchase();



}
