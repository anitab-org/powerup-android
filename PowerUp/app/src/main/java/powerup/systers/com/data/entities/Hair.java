package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Hair")
public class Hair {

    @PrimaryKey
    public int hairId;

    public String hairName;

    public int hairPoints;

    public int hairPurchased;

    public Hair(int hairId, String hairName, int hairPoints, int hairPurchased) {
        this.hairId = hairId;
        this.hairName = hairName;
        this.hairPoints = hairPoints;
        this.hairPurchased = hairPurchased;
    }
}
