package powerup.systers.com.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import powerup.systers.com.data.entities.Answer;

@Dao
public interface AnswerDao {

    @Insert
    void insertAllAnswers(List<Answer> hairsList);

    @Query("SELECT answerDescription FROM Answer WHERE questionID =:questionId")
    List<String> getAllAnswer(int questionId);


}
