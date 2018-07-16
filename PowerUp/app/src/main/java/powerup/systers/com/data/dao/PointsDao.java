package powerup.systers.com.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import powerup.systers.com.data.entities.Points;

@Dao
public interface PointsDao {

    @Insert
    void insertPoints(Points points);
}
