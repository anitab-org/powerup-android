/**
 * @desc this class holds getter and setter methods for each dialogue scenario's status
 * examples include setAsker() and getNextScenarioID()
 */

package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Scenario")
public class Scenario {

    @PrimaryKey
    private int scenarioId;

    private String scenarioName;

    private String timestamp;

    private String asker;

    private int avatar;

    private int firstQuestionID;

    private int completed;  // If equal to 1, the scenario is already completed

    private int nextScenarioID;

    private int replayed;   // If equal to 0, the scenario can be replayed

    public Scenario(int scenarioId, String scenarioName, String timestamp, String asker, int avatar, int firstQuestionID, int completed, int nextScenarioID, int replayed) {
        this.scenarioId = scenarioId;
        this.scenarioName = scenarioName;
        this.timestamp = timestamp;
        this.asker = asker;
        this.avatar = avatar;
        this.firstQuestionID = firstQuestionID;
        this.completed = completed;
        this.nextScenarioID = nextScenarioID;
        this.replayed = replayed;
    }
}
