package powerup.systers.com.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import powerup.systers.com.data.entities.Accessory;

@Dao
public interface AccessoryDao {

    @Insert
    void insertAllAccessories(List<Accessory> hairsList);

    @Query("SELECT accessoryPurchased FROM accessories WHERE accessoryId = :id")
    int getPurchasedAccessories(int id);

    @Query("UPDATE accessories SET accessoryPurchased = 1 WHERE accessoryId = :id")
    void setPurchasedAccessories(int id);

    @Query("UPDATE  Accessories SET accessoryPurchased =0")
    void resetAccessoryPurchase();

}
