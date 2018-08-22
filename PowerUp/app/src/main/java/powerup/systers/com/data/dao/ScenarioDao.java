package powerup.systers.com.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import powerup.systers.com.data.entities.Scenario;

@Dao
public interface ScenarioDao {

    @Insert
    void insertAllScenario(List<Scenario> scenarioList);

    @Query("SELECT * FROM scenario WHERE scenarioId =:scenarioId ")
    Scenario getScenarioFromId(int scenarioId);

    @Query("SELECT * FROM scenario WHERE scenarioName =:scenarioName")
    Scenario getSessionByName(String scenarioName);

    @Query("UPDATE Scenario SET completed = 1 WHERE scenarioId = :id")
    void setCompletedScenario(int id);

    @Query("UPDATE Scenario SET replayed = 1 WHERE scenarioName = :scenarioName")
    void setReplayedScenario(String scenarioName);

    @Query("UPDATE Scenario SET completed = 0")
    void resetComplete();

    @Query("UPDATE Scenario SET replayed = 0")
    void resetReplayed();

    @Query("UPDATE Scenario SET completed = 0 WHERE scenarioId = :id")
    void resetCompletedById(int id);

    @Query("UPDATE Scenario SET replayed = 0 WHERE scenarioId = :id")
    void resetReplayedById(int id);
}


