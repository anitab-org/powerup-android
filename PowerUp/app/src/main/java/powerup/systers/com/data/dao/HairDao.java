package powerup.systers.com.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import powerup.systers.com.data.entities.Hair;

@Dao
public interface HairDao {

    @Insert
    void insertAllHair(List<Hair> hairsList);

    @Query("SELECT hairPurchased FROM Hair WHERE hairId = :id")
    int getPurchasedHair(int id);

    @Query("UPDATE Hair SET hairPurchased = 1 WHERE hairId = :id")
    void setPurchasedHair(int id);

    // reset purchased hair
    @Query("UPDATE  Hair SET hairPurchased =0")
    void resetHairPurchase();
}
