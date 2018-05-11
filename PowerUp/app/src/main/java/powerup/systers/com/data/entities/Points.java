package powerup.systers.com.data.entities;

import android.arch.persistence.room.Entity;

@Entity(tableName = "Point")
public class Points {

    private Integer pointsId;

    private Integer strength;

    private Integer invisibility;

    private Integer healing;

    private Integer telepathy;

    private Integer userPoints;
}
