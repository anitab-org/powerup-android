package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "Accessories")
public class Accessory {

    private Integer accessoryId;

    private String accessoryName;

    private Integer accessoryPoints;

    private Integer accessoryPurchased;

    public Accessory(Integer accessoryId, String accessoryName, Integer accessoryPoints, Integer accessoryPurchased) {
        this.accessoryId = accessoryId;
        this.accessoryName = accessoryName;
        this.accessoryPoints = accessoryPoints;
        this.accessoryPurchased = accessoryPurchased;
    }
}
