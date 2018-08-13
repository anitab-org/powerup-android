package powerup.systers.com.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import powerup.systers.com.data.dao.AccessoryDao;
import powerup.systers.com.data.dao.AnswerDao;
import powerup.systers.com.data.dao.AvatarDao;
import powerup.systers.com.data.dao.ClothesDao;
import powerup.systers.com.data.dao.HairDao;
import powerup.systers.com.data.dao.PointsDao;
import powerup.systers.com.data.dao.QuestionsDao;
import powerup.systers.com.data.dao.ScenarioDao;
import powerup.systers.com.data.entities.Accessory;
import powerup.systers.com.data.entities.Answer;
import powerup.systers.com.data.entities.Avatar;
import powerup.systers.com.data.entities.Clothes;
import powerup.systers.com.data.entities.Hair;
import powerup.systers.com.data.entities.Points;
import powerup.systers.com.data.entities.Question;
import powerup.systers.com.data.entities.Scenario;


@Database(entities = {Accessory.class, Answer.class, Avatar.class, Clothes.class, Hair.class, Points.class, Question.class, Scenario.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final Object sLock = new Object();

    public abstract AccessoryDao accessoryDao();
    public abstract AnswerDao answerDao();
    public abstract AvatarDao avatarDao();
    public abstract ClothesDao clothesDao();
    public abstract HairDao hairDao();
    public abstract PointsDao pointsDao();
    public abstract QuestionsDao questionsDao();
    public abstract ScenarioDao scenarioDao();

    public static AppDatabase getAppDatabase(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE =
                        Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "user-database")
                                .fallbackToDestructiveMigration()
                                .allowMainThreadQueries()
                                .build();
            }
            return INSTANCE;
        }

    }



    public static void destroyInstance() {
        INSTANCE = null;
    }

}
