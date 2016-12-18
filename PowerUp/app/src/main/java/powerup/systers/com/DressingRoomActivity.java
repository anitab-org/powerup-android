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

    public static Activity dressingRoomInstance;
    private DatabaseHandler mDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressing_room);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        dressingRoomInstance = this;
        ImageView eyeView = (ImageView) findViewById(R.id.eyeView);
        ImageView faceView = (ImageView) findViewById(R.id.faceView);
        ImageView hairView = (ImageView) findViewById(R.id.hairView);
        ImageView clothView = (ImageView) findViewById(R.id.clothView);
        TextView karmaPoints = (TextView) findViewById(R.id.karmaPoints);
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
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

        IconRoundCornerProgressBar powerBarHealing = (IconRoundCornerProgressBar) findViewById(R.id.powerbarHealing);
        powerBarHealing.setIconImageResource(R.drawable.icon_healing);
        powerBarHealing.setProgress(mDbHandler.getHealing());

        IconRoundCornerProgressBar powerbarInvisibility = (IconRoundCornerProgressBar) findViewById(R.id.powerbarInvisibility);
        powerbarInvisibility.setIconImageResource(R.drawable.icon_invisibility);
        powerbarInvisibility.setProgress(mDbHandler.getInvisibility());

        IconRoundCornerProgressBar powerbarStrength = (IconRoundCornerProgressBar) findViewById(R.id.powerbarStrength);
        powerbarStrength.setIconImageResource(R.drawable.icon_strength);
        powerbarStrength.setProgress(mDbHandler.getStrength());

        IconRoundCornerProgressBar powerbarTelepathy = (IconRoundCornerProgressBar) findViewById(R.id.powerbarTelepathy);
        powerbarTelepathy.setIconImageResource(R.drawable.icon_telepathy);
        powerbarTelepathy.setProgress(mDbHandler.getTelepathy());

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

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
