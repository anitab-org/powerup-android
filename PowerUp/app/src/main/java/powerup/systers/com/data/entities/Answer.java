/**
 * @desc this class holds getter and setter methods for each question/answer pair
 * each answer has a specific point value
 * examples include setAnswerID() and getPoints()
 */

package powerup.systers.com.data.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Answer")
public class Answer {

    @PrimaryKey
    private int answerID;

    private int questionID;

    private String answerDescription;

    private int nextQuestionID;

    private int answerPoints;

    public Answer(int answerID, int questionID, String answerDescription, int nextQuestionID, int answerPoints) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.answerDescription = answerDescription;
        this.nextQuestionID = nextQuestionID;
        this.answerPoints = answerPoints;
    }

    public int getAnswerID() {
        return answerID;
    }

    public int getQuestionID() {
        return questionID;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public int getNextQuestionID() {
        return nextQuestionID;
    }

    public int getAnswerPoints() {
        return answerPoints;
    }
}
