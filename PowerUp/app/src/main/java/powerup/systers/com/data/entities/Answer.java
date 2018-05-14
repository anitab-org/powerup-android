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
    private Integer answerID;

    private Integer questionID;

    private String answerDescription;

    private Integer nextQuestionID;

    private Integer answerPoints;

    public Answer(Integer answerID, Integer questionID, String answerDescription, Integer nextQuestionID, Integer answerPoints) {
        this.answerID = answerID;
        this.questionID = questionID;
        this.answerDescription = answerDescription;
        this.nextQuestionID = nextQuestionID;
        this.answerPoints = answerPoints;
    }
}
