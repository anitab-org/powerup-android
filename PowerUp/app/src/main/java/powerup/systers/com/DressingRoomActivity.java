package powerup.systers.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DressingRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dressing_room);
        ImageView clothesImageView = (ImageView) findViewById(R.id.clothesImageView);
        ImageView hairImageView = (ImageView) findViewById(R.id.hairImageView);
        ImageView accessoriesImageView = (ImageView) findViewById(R.id.accessoriesImageView);

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

}
