package powerup.systers.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import powerup.systers.com.db.DatabaseHandler;

public class DressingRoomActivity extends AppCompatActivity {

    private DatabaseHandler mDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressing_room);
        ImageView clothesImageView = (ImageView) findViewById(R.id.clothesImageView);
        ImageView hairImageView = (ImageView) findViewById(R.id.hairImageView);
        ImageView accessoriesImageView = (ImageView) findViewById(R.id.accessoriesImageView);

        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();

        IconRoundCornerProgressBar powerBarHealing = (IconRoundCornerProgressBar) findViewById(R.id.powerbarHealing);
        powerBarHealing.setIconImageResource(R.drawable.icon_healing);
        powerBarHealing.setProgress(mDbHandler.getHealing());
        Log.i("healing", " " + mDbHandler.getHealing());

        IconRoundCornerProgressBar powerbarInvisibility = (IconRoundCornerProgressBar) findViewById(R.id.powerbarInvisibility);
        powerbarInvisibility.setIconImageResource(R.drawable.icon_invisibility);
        powerbarInvisibility.setProgress(mDbHandler.getInvisibility());
        Log.i("invis", " " + mDbHandler.getInvisibility());

        IconRoundCornerProgressBar powerbarStrength = (IconRoundCornerProgressBar) findViewById(R.id.powerbarStrength);
        powerbarStrength.setIconImageResource(R.drawable.icon_strength);
        powerbarStrength.setProgress(mDbHandler.getStrength());
        Log.i("str", " " + mDbHandler.getStrength());

        IconRoundCornerProgressBar powerbarTelepathy = (IconRoundCornerProgressBar) findViewById(R.id.powerbarTelepathy);
        powerbarTelepathy.setIconImageResource(R.drawable.icon_telepathy);
        powerbarTelepathy.setProgress(mDbHandler.getTelepathy());
        Log.i("tele", " " + mDbHandler.getTelepathy());

        clothesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DressingRoomActivity.this, SelectFeaturesActivity.class);
                intent.putExtra("feature","cloth");
                startActivity(intent);
            }
        });

        hairImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DressingRoomActivity.this, SelectFeaturesActivity.class);
                intent.putExtra("feature","hair");
                startActivity(intent);
            }
        });

        accessoriesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DressingRoomActivity.this, SelectFeaturesActivity.class);
                intent.putExtra("feature","accessory");
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
