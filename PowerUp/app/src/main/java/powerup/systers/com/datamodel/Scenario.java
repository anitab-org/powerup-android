/**
 * @desc this class holds getter and setter methods for each dialogue scenario's status
 * examples include setAsker() and getNextScenarioID()
 */

package powerup.systers.com.datamodel;

public class Scenario {

    private int id;
    private String scenarioName;
    private String timestamp;
    private String asker;
    private int avatar;
    private int firstQuestionID;
    private int completed;  // If equal to 1, the scenario is already completed
    private int nextScenarioID;
    private int replayed;   // If equal to 0, the scenario can be replayed

    public int getId() {
        return id;
    }

    public void setId(int iD) {
        id = iD;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getFirstQuestionID() {
        return firstQuestionID;
    }

    public void setFirstQuestionID(int firstQuestionID) {
        this.firstQuestionID = firstQuestionID;
    }

    public int getNextScenarioID() {
        return nextScenarioID;
    }

    public void setNextScenarioID(int nextScenarioID) {
        this.nextScenarioID = nextScenarioID;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getReplayed() {
        return replayed;
    }

    public void setReplayed(int replayed) {
        this.replayed = replayed;
    }
}
