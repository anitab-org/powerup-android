package powerup.systers.com.pregame_setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.StartActivity;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.powerup.PowerUpUtils;

public class PreGameSetupInitialActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_setup_initial_screen);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_continue)
    public void click() {
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, MapActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.btn_sister)
    public void clickSister() {
        setNpcType(PowerUpUtils.SISTER_TYPE);
    }

    @OnClick(R.id.btn_friend)
    public void clickFriend() {
        setNpcType(PowerUpUtils.FRIEND_TYPE);
    }

    @OnClick(R.id.btn_doctor)
    public void clickDoctor() {
        setNpcType(PowerUpUtils.DOCTOR_TYPE);
    }

    @OnClick(R.id.btn_teacher)
    public void clickTeacher() {
        setNpcType(PowerUpUtils.TEACHER_TYPE);
    }

    public void setNpcType(int type) {
        //Set the value on SessionHistory.npcType
        SessionHistory.npcType = type;
        //Migrate to PreGameSetupMainActivity
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PreGameSetupInitialActivity.this, StartActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        super.onBackPressed();
    }
}
