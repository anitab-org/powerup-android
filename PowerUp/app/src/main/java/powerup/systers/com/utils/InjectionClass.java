package powerup.systers.com.utils;

import android.content.Context;

import powerup.systers.com.data.AppDatabase;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.pref.PrefHelper;


public class InjectionClass {

    public static DataSource provideDataSource(Context context) {
        AppDatabase database = AppDatabase.getAppDatabase(context);
        return DataSource.getInstance(new AppExecutors(), context, database.accessoryDao(), database.answerDao(), database.avatarDao(),
                database.clothesDao(), database.hairDao(), database.questionsDao(), database.scenarioDao(), database.pointsDao(),
                new PrefHelper(context, "pref_file"));
    }
}
