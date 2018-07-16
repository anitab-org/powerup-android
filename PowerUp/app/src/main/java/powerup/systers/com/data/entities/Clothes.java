package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Clothes")
public class Clothes {

    @PrimaryKey
    public int clothesId;

    public String clothesName;

    public int clothesPoints;

    public int clothesPurchased;

    public Clothes(int clothesId, String clothesName, int clothesPoints, int clothesPurchased) {
        this.clothesId = clothesId;
        this.clothesName = clothesName;
        this.clothesPoints = clothesPoints;
        this.clothesPurchased = clothesPurchased;
    }
}
