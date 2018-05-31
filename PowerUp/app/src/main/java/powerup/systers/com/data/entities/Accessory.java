package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Accessories")
public class Accessory {

    @PrimaryKey
    private int accessoryId;

    private String accessoryName;

    private int accessoryPoints;

    private int accessoryPurchased;

    public Accessory(int accessoryId, String accessoryName, int accessoryPoints, int accessoryPurchased) {
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
        this.accessoryPoints = accessoryPoints;
        this.accessoryPurchased = accessoryPurchased;
    }
}
