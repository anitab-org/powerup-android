/**
 * @desc this class holds getter and setter methods for each question and the scenario it belongs to
 * examples include getQuestionDescription() and setScenarioID()
 */

package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Question")
public class Question {

    @PrimaryKey
    public int questionID;

    public int scenarioID;

    public String questionDescription;

    public Question(int questionID, int scenarioID, String questionDescription) {
        this.questionID = questionID;
        this.scenarioID = scenarioID;
        this.questionDescription = questionDescription;
    }
}
