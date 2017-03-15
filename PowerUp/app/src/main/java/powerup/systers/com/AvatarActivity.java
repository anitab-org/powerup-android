/**
 * @desc displays avatar features, sets hasPreviouslyStarted to true, and
 * returns the user to map after clicking “continue”.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import powerup.systers.com.db.DatabaseHandler;

public class AvatarActivity extends Activity {

    int fromActivity;
    private DatabaseHandler mDbHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        fromActivity = getIntent().getExtras().getInt(getResources().getString(R.string.from_activity));
        ImageView eyeView = (ImageView) findViewById(R.id.eyeView);
        ImageView faceView = (ImageView) findViewById(R.id.faceView);
        ImageView hairView = (ImageView) findViewById(R.id.hairView);
        ImageView clothView = (ImageView) findViewById(R.id.clothView);
        ImageView bagView = (ImageView) findViewById(R.id.bagView);
        ImageView glassesView = (ImageView) findViewById(R.id.glassesView);
        ImageView hatView = (ImageView) findViewById(R.id.hatView);
        ImageView necklaceView = (ImageView) findViewById(R.id.necklaceView);
        Button continueButton = (Button) findViewById(R.id.continueButton);
        Button backButton = (Button) findViewById(R.id.backButton);
        String eyeImageName = getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            eyeView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        String faceImageName = getResources().getString(R.string.face);
        faceImageName = faceImageName + getmDbHandler().getAvatarFace();
        try {
            photoNameField = ourRID.getClass().getField(faceImageName);
            faceView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        String clothImageName = getResources().getString(R.string.cloth);
        clothImageName = clothImageName + getmDbHandler().getAvatarCloth();
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            clothView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        String hairImageName = getResources().getString(R.string.hair);
        hairImageName = hairImageName + getmDbHandler().getAvatarHair();
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            hairView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (getmDbHandler().getAvatarBag() != 0) {
            String bagImageName = getResources().getString(R.string.bag);
            bagImageName = bagImageName + getmDbHandler().getAvatarBag();
            try {
                photoNameField = ourRID.getClass().getField(bagImageName);
                bagView.setImageResource(photoNameField.getInt(ourRID));
            } catch (NoSuchFieldException | IllegalAccessException
                    | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        if (getmDbHandler().getAvatarGlasses() != 0) {
            String glassesImageName = getResources().getString(R.string.glasses);
            glassesImageName = glassesImageName + getmDbHandler().getAvatarGlasses();
            try {
                photoNameField = ourRID.getClass().getField(glassesImageName);
                glassesView.setImageResource(photoNameField.getInt(ourRID));
            } catch (NoSuchFieldException | IllegalAccessException
                    | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        if (getmDbHandler().getAvatarHat() != 0) {
            String hatImageName = getResources().getString(R.string.hat);
            hatImageName = hatImageName + getmDbHandler().getAvatarHat();
            try {
                photoNameField = ourRID.getClass().getField(hatImageName);
                hatView.setImageResource(photoNameField.getInt(ourRID));
            } catch (NoSuchFieldException | IllegalAccessException
                    | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        if (getmDbHandler().getAvatarNeckalce() != 0) {
            String necklaceImageName = getResources().getString(R.string.necklace);
            necklaceImageName = necklaceImageName + getmDbHandler().getAvatarNeckalce();
            try {
                photoNameField = ourRID.getClass().getField(necklaceImageName);
                necklaceView.setImageResource(photoNameField.getInt(ourRID));
            } catch (NoSuchFieldException | IllegalAccessException
                    | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromActivity == 1) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(AvatarActivity.this);
                    boolean hasPreviouslyStarted = prefs.getBoolean(getString(R.string.preferences_has_previously_started), false);
                    if (!hasPreviouslyStarted) {
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putBoolean(getString(R.string.preferences_has_previously_started), Boolean.TRUE);
                        edit.apply();
                    }
                    new AvatarRoomActivity().avatarRoomInstance.finish();
                    finish();
                    startActivityForResult(new Intent(AvatarActivity.this, MapActivity.class), 0);
                } else {
                    new DressingRoomActivity().dressingRoomInstance.finish();
                    new SelectFeaturesActivity().selectFeatureInstance.finish();
                    finish();
                    startActivityForResult(new Intent(AvatarActivity.this, MapActivity.class), 0);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
