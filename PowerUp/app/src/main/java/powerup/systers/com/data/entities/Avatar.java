package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "Avatar")
public class Avatar {

    private Integer avatarId;

    private Integer face;

    private Integer clothes;

    private Integer hair;

    private Integer eyes;

    private Integer bag;

    private Integer glasses;

    private Integer hat;

    private Integer necklace;
}
