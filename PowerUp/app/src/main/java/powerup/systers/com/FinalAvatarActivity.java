package powerup.systers.com;

/**
 * Created by fidel on 12/21/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;

public class FinalAvatarActivity extends Activity{
    private ImageView eyeAvatar;
    private ImageView skinAvatar;
    private ImageView clothAvatar;
    private ImageView hairAvatar;
    private DatabaseHandler mDbHandler;
    public Activity finalAvatarInstance;
    public FinalAvatarActivity() {
        finalAvatarInstance = this;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        setContentView(R.layout.final_avatar);
        eyeAvatar = (ImageView) findViewById(R.id.eye_view);
        hairAvatar = (ImageView) findViewById(R.id.hair_view);
        skinAvatar = (ImageView) findViewById(R.id.skin_view);
        clothAvatar = (ImageView) findViewById(R.id.dress_view);
        String eyeImageName = getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            eyeAvatar.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String skinImageName = getResources().getString(R.string.skin);
        skinImageName = skinImageName + getmDbHandler().getAvatarSkin();
        try {
            photoNameField = ourRID.getClass().getField(skinImageName);
            skinAvatar.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String clothImageName = getResources().getString(R.string.cloth);
        clothImageName = clothImageName + getmDbHandler().getAvatarCloth();
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            clothAvatar.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String hairImageName = getResources().getString(R.string.hair);
        hairImageName = hairImageName + getmDbHandler().getAvatarHair();
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            hairAvatar.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }
        ImageView continueButton = (ImageView) findViewById(R.id.continueButtonFinal);
        ImageView backButton = (ImageView) findViewById(R.id.backButtonFinal);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(FinalAvatarActivity.this);
                boolean hasPreviouslyStarted = prefs.getBoolean(getString(R.string.preferences_has_previously_started), false);
                if (!hasPreviouslyStarted) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean(getString(R.string.preferences_has_previously_started), Boolean.TRUE);
                    edit.apply();
                }
                startActivityForResult(new Intent(FinalAvatarActivity.this, MapActivity.class), 0);
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinalAvatarActivity.this, AvatarRoomActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }
    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
