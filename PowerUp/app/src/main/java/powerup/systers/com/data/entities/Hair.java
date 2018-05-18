package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Hair")
public class Hair {

    @PrimaryKey
    private int hairId;

    private String hairName;

    private int hairPoints;

    private int hairPurchased;

    public Hair(int hairId, String hairName, int hairPoints, int hairPurchased) {
        this.hairId = hairId;
        this.hairName = hairName;
        this.hairPoints = hairPoints;
        this.hairPurchased = hairPurchased;
    }
}
