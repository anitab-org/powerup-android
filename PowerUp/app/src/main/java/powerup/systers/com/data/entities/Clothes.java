package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "Clothes")
public class Clothes {

    private Integer clothesId;

    private String clothesName;

    private Integer clothesPoints;

    private Integer clothesPurchased;

    public Clothes(Integer clothesId, String clothesName, Integer clothesPoints, Integer clothesPurchased) {
        this.clothesId = clothesId;
        this.clothesName = clothesName;
        this.clothesPoints = clothesPoints;
        this.clothesPurchased = clothesPurchased;
    }
}
