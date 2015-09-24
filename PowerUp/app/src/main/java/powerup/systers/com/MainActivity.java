package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageButton new_user = (ImageButton) findViewById(R.id.newUserButtonFirstPage);
		new_user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this,
						AvatarRoom.class);
				startActivityForResult(myIntent, 0);
			}
		});

		ImageButton start = (ImageButton) findViewById(R.id.startButtonMain);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(MainActivity.this, MapActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}
}
