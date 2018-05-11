package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "Hair")
public class Hair {

    private Integer hairId;

    private String hairName;

    private Integer hairPoints;

    private Integer hairPurchased;

    public Hair(Integer hairId, String hairName, Integer hairPoints, Integer hairPurchased) {
        this.hairId = hairId;
        this.hairName = hairName;
        this.hairPoints = hairPoints;
        this.hairPurchased = hairPurchased;
    }
}
