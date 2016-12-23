/**
 * @desc this class holds getter and setter methods for each question/answer pair
 * each answer has a specific point value
 * examples include setAnswerID() and getPoints()
 */

package powerup.systers.com.datamodel;

public class Answer {

    private Integer answerID;
    private String answerDescription;
    private Integer questionID;
    private Integer nextQuestionID;
    private Integer points;

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer aId) {
        this.answerID = aId;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String aDes) {
        this.answerDescription = aDes;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer qId) {
        this.questionID = qId;
    }

    public Integer getNextQuestionID() {
        return nextQuestionID;
    }

    public void setNextQuestionID(Integer nextQuestionID) {
        this.nextQuestionID = nextQuestionID;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

}
