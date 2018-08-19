package powerup.systers.com.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import powerup.systers.com.data.entities.Avatar;

@Dao
public interface AvatarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAvatarDefaultValue(Avatar avatar);

    @Query("SELECT face FROM Avatar WHERE avatarId = 1")
    int getAvatarSkin();

    @Query("UPDATE Avatar SET face = :faceValue WHERE avatarId = 1")
    void setAvatarSkin(int faceValue);

    @Query("SELECT eyes FROM Avatar WHERE avatarId = 1")
    int getAvatarEye();

    @Query("UPDATE Avatar SET eyes = :eyeValue WHERE avatarId = 1")
    void setAvatarEye(int eyeValue);

    @Query("SELECT clothes FROM Avatar WHERE avatarId = 1")
    int getAvatarCloth();

    @Query("UPDATE Avatar SET clothes = :clothValue WHERE avatarId = 1")
    void setAvatarCloth(int clothValue);

    @Query("SELECT hair FROM Avatar WHERE avatarId = 1")
    int getAvatarHair();

    @Query("UPDATE Avatar SET hair = :hairValue WHERE avatarId = 1")
    void setAvatarHair(int hairValue);

    //Todo doubt in this query as there are more accessory than bag
    @Query("SELECT bag FROM Avatar WHERE avatarId = 1")
    int getAvatarAccessory();

    @Query("UPDATE Avatar SET bag = :value WHERE avatarId = :key")
    void setAvatarAccessory(int value, int key);

    // reset purchased accessories
    @Query("UPDATE  Avatar SET bag =0")
    void resetBagPurchase();



}
