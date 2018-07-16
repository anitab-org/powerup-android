package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Avatar")
public class Avatar {

    @PrimaryKey
    public int avatarId;

    public int face;

    public int clothes;

    public int hair;

    public int eyes;

    public int bag;

    public int glasses;

    public int hat;

    public int necklace;

    public Avatar(int avatarId, int face, int clothes, int hair, int eyes, int bag, int glasses, int hat, int necklace) {
        this.avatarId = avatarId;
        this.face = face;
        this.clothes = clothes;
        this.hair = hair;
        this.eyes = eyes;
        this.bag = bag;
        this.glasses = glasses;
        this.hat = hat;
        this.necklace = necklace;
    }
}
