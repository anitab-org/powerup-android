package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
		if (!previouslyStarted) {
			startActivity(new Intent(StartActivity.this, AvatarRoomActivity.class));
		}
		ImageButton new_user = (ImageButton) findViewById(R.id.newUserButtonFirstPage);
		new_user.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(StartActivity.this,
						AvatarRoomActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		ImageButton start = (ImageButton) findViewById(R.id.startButtonMain);
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(StartActivity.this, MapActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

	}
}
