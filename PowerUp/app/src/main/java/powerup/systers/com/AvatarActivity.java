package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import powerup.systers.com.db.DatabaseHandler;

public class AvatarActivity extends Activity {

	private DatabaseHandler mDbHandler;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.avatar);
		setmDbHandler(new DatabaseHandler(this));
		getmDbHandler().open();
		ImageView eyeView = (ImageView) findViewById(R.id.eyeView);
		ImageView faceView = (ImageView) findViewById(R.id.faceView);
		ImageView hairView = (ImageView) findViewById(R.id.hairView);
		ImageView clothView = (ImageView) findViewById(R.id.clothView);
		Button continueButton = (Button) findViewById(R.id.continueButton);
		
		String eyeImageName = "eye";
		eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
		R.drawable ourRID = new R.drawable();
		java.lang.reflect.Field photoNameField;
		try {
			photoNameField = ourRID.getClass().getField(eyeImageName);
			eyeView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String faceImageName = "face";
		faceImageName = faceImageName + getmDbHandler().getAvatarFace();
		try {
			photoNameField = ourRID.getClass().getField(faceImageName);
			faceView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String clothImageName = "cloth";
		clothImageName = clothImageName + getmDbHandler().getAvatarCloth();
		try {
			photoNameField = ourRID.getClass().getField(clothImageName);
			clothView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String hairImageName = "hair";
		hairImageName = hairImageName + getmDbHandler().getAvatarHair();
		try {
			photoNameField = ourRID.getClass().getField(hairImageName);
			hairView.setImageResource(photoNameField.getInt(ourRID));
		} catch (NoSuchFieldException | IllegalAccessException
				| IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		continueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(AvatarActivity.this, Game.class);
				startActivityForResult(myIntent, 0);
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
