package powerup.systers.com.pregame_setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import powerup.systers.com.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.StartActivity;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.powerup.PowerUpUtils;

public class PreGameSetupInitialActivity extends Activity {

    private ImageView continueButton;
    private Button btnSister, btnFriend, btnDoctor, btnTeacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_setup_initial_screen);

        continueButton = findViewById(R.id.btn_continue);
        btnSister = findViewById(R.id.btn_sister);
        btnFriend = findViewById(R.id.btn_friend);
        btnDoctor = findViewById(R.id.btn_doctor);
        btnTeacher = findViewById(R.id.btn_teacher);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(PreGameSetupInitialActivity.this, MapActivity.class), 0);
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });

        btnSister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionHistory.npcType = PowerUpUtils.SISTER_TYPE;
                startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });

        btnFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionHistory.npcType = PowerUpUtils.FRIEND_TYPE;
                startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionHistory.npcType = PowerUpUtils.DOCTOR_TYPE;
                startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });

        btnTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionHistory.npcType = PowerUpUtils.TEACHER_TYPE;
                startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PreGameSetupInitialActivity.this, StartActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        super.onBackPressed();
    }
}
