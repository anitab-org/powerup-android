package powerup.systers.com.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import powerup.systers.com.data.entities.Question;

@Dao
public interface QuestionsDao {

    @Insert
    void insertAllQuestion(List<Question> questionList);

    @Query("SELECT * FROM Question WHERE questionID =:questionId")
    Question getQuestion(int questionId);


}
