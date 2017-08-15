/**
 * @desc displays avatar features in dressing room and updates power/health bars.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;

public class DressingRoomActivity extends AppCompatActivity {

    public Activity dressingRoomInstance;
    private DatabaseHandler mDbHandler;
    private TextView karmaPoints;
    ImageView clothView,eyeView,faceView,hairView,bagView,glassesView,hatView,necklaceView;
    java.lang.reflect.Field photoNameField;
    R.drawable ourRID;

    public DressingRoomActivity() {
        dressingRoomInstance = this;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressing_room);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        karmaPoints = (TextView) findViewById(R.id.karmaPoints);
        dressingRoomInstance = this;
        eyeView = (ImageView) findViewById(R.id.eyeView);
        faceView = (ImageView) findViewById(R.id.faceView);
        hairView = (ImageView) findViewById(R.id.hairView);
        clothView = (ImageView) findViewById(R.id.clothView);
        bagView = (ImageView) findViewById(R.id.bagView);
        glassesView = (ImageView) findViewById(R.id.glassesView);
        hatView = (ImageView) findViewById(R.id.hatView);
        necklaceView = (ImageView) findViewById(R.id.necklaceView);
        TextView karmaPoints = (TextView) findViewById(R.id.karmaPoints);
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
        String eyeImageName = getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
        ourRID = new R.drawable();
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            eyeView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String faceImageName = getResources().getString(R.string.face);
        faceImageName = faceImageName + getmDbHandler().getAvatarFace();
        try {
            photoNameField = ourRID.getClass().getField(faceImageName);
            faceView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String clothImageName = getResources().getString(R.string.cloth);
        clothImageName = clothImageName + getmDbHandler().getAvatarCloth();
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            clothView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String hairImageName = getResources().getString(R.string.hair);
        hairImageName = hairImageName + getmDbHandler().getAvatarHair();
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            hairView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        ImageView clothesImageView = (ImageView) findViewById(R.id.clothesImageView);
        ImageView hairImageView = (ImageView) findViewById(R.id.hairImageView);
        ImageView accessoriesImageView = (ImageView) findViewById(R.id.accessoriesImageView);


        clothesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DressingRoomActivity.this, SelectFeaturesActivity.class);
                intent.putExtra(getResources().getString(R.string.feature), getResources().getString(R.string.cloth));
                startActivity(intent);
            }
        });

        hairImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DressingRoomActivity.this, SelectFeaturesActivity.class);
                intent.putExtra(getResources().getString(R.string.feature), getResources().getString(R.string.hair));
                startActivity(intent);
            }
        });

        accessoriesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DressingRoomActivity.this, SelectFeaturesActivity.class);
                intent.putExtra(getResources().getString(R.string.feature), getResources().getString(R.string.accessory));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));

    }
    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
