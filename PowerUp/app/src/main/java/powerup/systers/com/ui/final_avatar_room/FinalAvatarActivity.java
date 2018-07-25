package powerup.systers.com.ui.final_avatar_room;

/**
 * Created by fidel on 12/21/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.ui.avatar_room.AvatarRoomActivity;
import powerup.systers.com.ui.map_screen_level2.MapLevel2Activity;
import powerup.systers.com.utils.InjectionClass;

public class FinalAvatarActivity extends Activity implements FinalAvatarRoomContract.IAvatarRoomView{
    private DataSource dataSource;

    @BindView(R.id.eye_view)
    ImageView eyeAvatar;
    @BindView(R.id.skin_view)
    ImageView skinAvatar;
    @BindView(R.id.dress_view)
    ImageView clothAvatar;
    @BindView(R.id.hair_view)
    ImageView hairAvatar;
    //initialize continue & back button
    @BindView(R.id.continueButtonFinal)
    ImageView continueButton;
    @BindView(R.id.backButtonFinal)
    ImageView backButton;
    private FinalAvatarRoomPresenter presenter;

    int eye, skin, hair, cloth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the database instance
        dataSource = InjectionClass.provideDataSource(this);

        setContentView(R.layout.final_avatar);
        ButterKnife.bind(this);
        presenter = new FinalAvatarRoomPresenter(this, dataSource, this);

        // check value from avatarRoomActivity
        init();
    }


    @OnClick(R.id.continueButtonFinal)
    public void continuebuttonListener( View view) {
        // setting current avatar value in preferences & start MapActivity
        dataSource.setCurrentEyeValue(eye);
        dataSource.setCurrentSkinValue(skin);
        dataSource.setCurrentClothValue(cloth);
        dataSource.setCurrentHairValue(hair);
        dataSource.setPreviouslyCustomized(true);
        // setting purchased hair & clothes
        dataSource.setPurchasedHair(hair);
        dataSource.setPurchasedClothes(cloth);

        dataSource.updateComplete();//set all the complete fields back to 0
        dataSource.updateReplayed();//set all the replayed fields back to 0

        SessionHistory.totalPoints = 0;    //reset the points stored
        SessionHistory.currSessionID = 1;
        SessionHistory.currScenePoints = 0;
        SessionHistory.sceneHomeIsReplayed = false;
        SessionHistory.sceneSchoolIsReplayed = false;
        SessionHistory.sceneHospitalIsReplayed = false;
        SessionHistory.sceneLibraryIsReplayed = false;
        // starting map activity
        startActivityForResult(new Intent(FinalAvatarActivity.this, MapActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.backButtonFinal)
    public void backButtonListener(View view) {
        Intent intent = new Intent(FinalAvatarActivity.this, AvatarRoomActivity.class);
        //passing current value of avatar via intent
        intent.putExtra("eye" , eye);
        intent.putExtra("skin", skin);
        intent.putExtra("cloth", cloth);
        intent.putExtra("hair", hair);

        finish();
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void init() {
        // checking passed values from intent or use preferences values for avatar setup
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eye = extras.getInt("eye");
            skin = extras.getInt("skin");
            hair = extras.getInt("hair");
            cloth = extras.getInt("cloth");
        }
        else {
            eye = dataSource.getCurrentEyeValue();
            hair = dataSource.getCurrentHairValue();
            skin = dataSource.getCurrentHairValue();
            cloth = dataSource.getCurrentClothValue();
        }
        // setting values on avatar
        presenter.setValues(eye, hair, skin, cloth);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataSource.clearInstance();
        presenter = null;
    }

    @Override
    public void updateAvatarEye(int eye) {
        eyeAvatar.setImageResource(eye);
    }

    @Override
    public void updateAvatarCloth(int cloth) {
        clothAvatar.setImageResource(cloth);
    }

    @Override
    public void updateAvatarHair(int hair) {
        hairAvatar.setImageResource(hair);
    }

    @Override
    public void updateAvatarSkin(int skin) {
        skinAvatar.setImageResource(skin);
    }

}
