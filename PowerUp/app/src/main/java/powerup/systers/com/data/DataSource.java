package powerup.systers.com.data;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import powerup.systers.com.data.dao.*;
import powerup.systers.com.data.entities.*;
import powerup.systers.com.data.pref.IPrefHelper;
import powerup.systers.com.utils.*;

public class DataSource implements IDataSource {

    private Context context;
    private static volatile DataSource INSTANCE;

    private AccessoryDao accessoryDao;
    private AnswerDao answerDao;
    private AvatarDao avatarDao;
    private ClothesDao clothesDao;
    private HairDao hairDao;
    private QuestionsDao questionsDao;
    private ScenarioDao scenarioDao;
    private PointsDao pointsDao;
    private AppExecutors appExecutors;
    private IPrefHelper prefHelper;

    // prevent direct instantiation
    private DataSource(@NonNull AppExecutors appExecutors, Context context, @NonNull AccessoryDao accessoryDao, @NonNull AnswerDao answerDao, @NonNull AvatarDao avatarDao,
                       @NonNull ClothesDao clothesDao, @NonNull HairDao hairDao, @NonNull QuestionsDao questionsDao, @NonNull ScenarioDao scenarioDao, @NonNull PointsDao pointsDao, IPrefHelper prefHelper) {
        this.appExecutors = appExecutors;
        this.avatarDao = avatarDao;
        this.accessoryDao = accessoryDao;
        this.answerDao = answerDao;
        this.clothesDao = clothesDao;
        this.hairDao = hairDao;
        this.questionsDao = questionsDao;
        this.scenarioDao = scenarioDao;
        this.pointsDao = pointsDao;
        this.context = context;
        this.prefHelper = prefHelper;
    }

    // returns a singleton instance
    public static DataSource getInstance(@NonNull AppExecutors appExecutors, Context context, @NonNull AccessoryDao accessoryDao, @NonNull AnswerDao answerDao, @NonNull AvatarDao avatarDao,
                                         @NonNull ClothesDao clothesDao, @NonNull HairDao hairDao, @NonNull QuestionsDao questionsDao, @NonNull ScenarioDao scenarioDao, @NonNull PointsDao pointsDao, @NonNull IPrefHelper prefHelper) {
        if (INSTANCE == null) {
            synchronized (DataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataSource(appExecutors, context, accessoryDao, answerDao, avatarDao, clothesDao, hairDao, questionsDao, scenarioDao, pointsDao, prefHelper);
                }
            }
        }
        return INSTANCE;
    }

    public static void clearInstance() {
        INSTANCE = null;
    }


    // Reader Data using CSV Reader file & Insert in sqlite database
    @Override
    public void readAndInsertCSVData() {
        final CSVReader reader = new CSVReader(context);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                questionsDao.insertAllQuestion(reader.<Question>getList(PowerUpUtils.questionCSV, 0));
                answerDao.insertAllAnswers(reader.<Answer>getList(PowerUpUtils.answerCSV,1));
                scenarioDao.insertAllScenario(reader.<Scenario>getList(PowerUpUtils.scenarioCSV, 2));
                clothesDao.insertAllClothes(reader.<Clothes>getList(PowerUpUtils.clothesCSV, 3));
                hairDao.insertAllHair(reader.<Hair>getList(PowerUpUtils.hairCSV, 4));
                accessoryDao.insertAllAccessories(reader.<Accessory>getList(PowerUpUtils.accessoriesCSV, 5));

            }
        };
        appExecutors.diskIO().execute(saveRunnable);
    }

    /*
     enter the default avatar settings into the database &
     enter the lowest possible values of power/health bars and karma points
     */
    @Override
    public void insertPointsAndAvatarData() {
        final Avatar avatar = new Avatar(1,1,1,1,1,0,0,0,0);
        final Points points = new Points(1,1,1,1,1,0);
        Runnable insertRunnable = new Runnable() {
            @Override
            public void run() {
                avatarDao.insertAvatarDefaultValue(avatar);
                pointsDao.insertPoints(points);
            }
        };
        appExecutors.diskIO().execute(insertRunnable);
    }

    /**
     * @desc prevents a previously completed scenario from being repeated again. Sets
     * scene ID to the scenario name being passed.
     * @param scenarioName - the scenario being checked for completion
     * @return boolean - if the scenario has been completed or not
     */
    @Override
    public void setSessionId(final String scenarioName, @NonNull final LoadBooleanCallback callback) {

        Runnable sessionRunnable = new Runnable() {
            @Override
            public void run() {
                final Scenario scenario = scenarioDao.getSessionByName(scenarioName);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(scenario.getCompleted() == 1) {
                            callback.onResultLoaded(false);
                        }
                        else
                            callback.onResultLoaded(true);
                        SessionHistory.currSessionID = scenario.getScenarioId();
                        SessionHistory.currQID = scenario.getFirstQuestionID();
                    }
                });
            }
        };
        appExecutors.diskIO().execute(sessionRunnable);
    }

    @Override
    public void getAnswerList(final int questionId, @NonNull final LoadAnswerListCallBack callBack) {
        Runnable answerRunnable = new Runnable() {
            @Override
            public void run() {
                final List<Answer> descriptionList = answerDao.getAllAnswer(questionId);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onDataLoaded(descriptionList);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(answerRunnable);
    }

    @Override
    public void getCurrentQuestion(@NonNull final LoadStringCallBack callBack) {
        Runnable questionRunnable = new Runnable() {
            @Override
            public void run() {
                final String curQuestion = questionsDao.getQuestionDescriptionById(SessionHistory.currQID);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onDataLoaded(curQuestion);
                    }
                });

            }
        };
        appExecutors.diskIO().execute(questionRunnable);
    }

    @Override
    public void getCurrentScenario(@NonNull final LoadScenarioCallBack callBack) {
        Runnable scenarioRunnable = new Runnable() {
            @Override
            public void run() {
                final Scenario scenario = scenarioDao.getScenarioFromId(SessionHistory.currSessionID);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onScenarioLoaded(scenario);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(scenarioRunnable);
    }

    @Override
    public void getScenarioFromId(final int scenarioId, @NonNull final LoadScenarioCallBack callBack) {
        Runnable scenarioRunnable = new Runnable() {
            @Override
            public void run() {
                final Scenario scenario = scenarioDao.getScenarioFromId(scenarioId);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onScenarioLoaded(scenario);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(scenarioRunnable);
    }

    @Override
    public void setCompletedScenario(final int id) {
        Runnable completeRunnable = new Runnable() {
            @Override
            public void run() {
                scenarioDao.setCompletedScenario(id);
            }
        };
        appExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void setReplayedScenario(final String scenarioName) {
        Runnable replayedRunnable = new Runnable() {
            @Override
            public void run() {
                scenarioDao.setReplayedScenario(scenarioName);
            }
        };
        appExecutors.diskIO().execute(replayedRunnable);
    }

    @Override
    public void getAvatarSkin(@NonNull final LoadIntegerCallback callback) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                final int value = avatarDao.getAvatarSkin();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }
    /**
     * @desc change the avatar's face.
     * @param face - the number of the requested face
     */
    @Override
    public void setAvatarSkin(final int face) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                avatarDao.setAvatarSkin(face);
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    @Override
    public void getAvatarEye(@NonNull final LoadIntegerCallback callback) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                final int value = avatarDao.getAvatarEye();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    /**
     * @desc change the avatar's eyes.
     * @param eye - the number of the requested eye
     */
    @Override
    public void setAvatarEye(final int eye) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                avatarDao.setAvatarEye(eye);
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    @Override
    public void getAvatarCloth(@NonNull final LoadIntegerCallback callback) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                final int value = avatarDao.getAvatarCloth();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    /**
     * @desc change the avatar's clothes.
     * @param cloth - the number of the requested clothing
     */
    @Override
    public void setAvatarCloth(final int cloth) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                avatarDao.setAvatarCloth(cloth);
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    @Override
    public void getAvatarHair(@NonNull final LoadIntegerCallback callback) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                final int value = avatarDao.getAvatarHair();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    /**
     * @desc change the avatar's hair.
     * @param hair - the number of the requested hair
     */
    @Override
    public void setAvatarHair(final int hair) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                avatarDao.setAvatarHair(hair);
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    /**
     * @desc returns the id of current accessory assigned to avatar
     */
    @Override
    public void getAvatarAccessory(@NonNull final LoadIntegerCallback callback) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                final int value = avatarDao.getAvatarAccessory();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    /**
     * @desc changes the current accessory assigned to avatar to given accessoryId
     * @param id - the id of accessory to be set
     */
    @Override
    public void setAvatarAccessory(final int id) {
        Runnable avatarRunnable = new Runnable() {
            @Override
            public void run() {
                avatarDao.setAvatarAccessory(id, 1);
            }
        };
        appExecutors.diskIO().execute(avatarRunnable);
    }

    @Override
    public void getPurchasedClothes(final int id, @NonNull final LoadIntegerCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final int value = clothesDao.getPurchasedClothes(id);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void setPurchasedClothes(final int clothes) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                clothesDao.setPurchasedClothes(clothes);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPurchasedHair(final int id, @NonNull final LoadIntegerCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final int value = hairDao.getPurchasedHair(id);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void setPurchasedHair(final int hair) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                hairDao.setPurchasedHair(hair);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPurchasedAccessories(final int id, @NonNull final LoadIntegerCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final int value = accessoryDao.getPurchasedAccessories(id);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResultLoaded(value);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void setPurchasedAccessories(final int id) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                accessoryDao.setPurchasedAccessories(id);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    /**
     * @desc sets scenarios back to default (not complete).
     */
    @Override
    public void updateComplete() {
        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                scenarioDao.resetComplete();
            }
        };
        appExecutors.diskIO().execute(updateRunnable);
    }

    /**
     * @desc sets scenarios back so that they can be replayed.
     */
    @Override
    public void updateReplayed() {
        Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                scenarioDao.resetReplayed();
            }
        };
        appExecutors.diskIO().execute(updateRunnable);
    }

    @Override
    public void resetCompleted(final int id) {
        Runnable resetRunnable = new Runnable() {
            @Override
            public void run() {
                scenarioDao.resetCompletedById(id);
            }
        };
        appExecutors.diskIO().execute(resetRunnable);
    }

    @Override
    public void resetReplayed(final int id) {
        Runnable resetRunnable = new Runnable() {
            @Override
            public void run() {
                scenarioDao.resetReplayedById(id);
            }
        };
        appExecutors.diskIO().execute(resetRunnable);
    }

    /**
     * @desc sets purchases to default so that nothing has been bought.
     */
    @Override
    public void resetPurchase() {
        Runnable resetRunnable = new Runnable() {
            @Override
            public void run() {
                clothesDao.resetClothPurchase();
                hairDao.resetHairPurchase();
                accessoryDao.resetAccessoryPurchase();
                avatarDao.resetBagPurchase();
            }
        };
        appExecutors.diskIO().execute(resetRunnable);
    }


    @Override
    public boolean checkFirstTime() {
        return prefHelper.checkFirstTime();
    }

    @Override
    public void setFirstTime(boolean value) {
        prefHelper.setFirstTime(value);
    }

    @Override
    public boolean checkPreviouslyCustomized() {
        return prefHelper.checkPreviouslyCustomized();
    }

    @Override
    public void setPreviouslyCustomized(boolean value) {
        prefHelper.setPreviouslyCustomized(value);
    }

    @Override
    public int getCurrentEyeValue() {
        return prefHelper.getCurrentEyeValue();
    }

    @Override
    public void setCurrentEyeValue(int value) {
        prefHelper.setCurrentEyeValue(value);
    }

    @Override
    public int getCurrentSkinValue() {
        return prefHelper.getCurrentSkinValue();
    }

    @Override
    public void setCurrentSkinValue(int value) {
        prefHelper.setCurrentSkinValue(value);
    }

    @Override
    public int getCurrentHairValue() {
        return prefHelper.getCurrentHairValue();
    }

    @Override
    public void setCurrentHairValue(int value) {
        prefHelper.setCurrentHairValue(value);
    }

    @Override
    public int getCurrentClothValue() {
        return prefHelper.getCurrentClothValue();
    }

    @Override
    public void setCurrentClothValue(int value) {
        prefHelper.setCurrentClothValue(value);
    }

    @Override
    public int getCurrentAccessoriesValue() {
        return prefHelper.getCurrentAccessoriesValue();
    }

    @Override
    public void setCurrentAccessoriesValue(int value) {
        prefHelper.setCurrentAccessoriesValue(value);
    }
}
