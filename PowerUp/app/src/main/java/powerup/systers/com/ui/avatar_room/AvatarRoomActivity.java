/**
 * @desc sets up the “Avatar Room” for user to customize avatar features.
 * Allows user to scroll through different skin/hair/clothing options.
 */

package powerup.systers.com.ui.avatar_room;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.ui.final_avatar_room.FinalAvatarActivity;
import powerup.systers.com.R;
import powerup.systers.com.data.DataSource;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.utils.InjectionClass;

public class AvatarRoomActivity extends Activity implements AvatarRoomContract.IAvatarRoomView {
    //avatar views
    @BindView(R.id.eye_view)
    public ImageView eyeAvatar;
    @BindView(R.id.skin_view)
    public ImageView skinAvatar;
    @BindView(R.id.dress_view)
    public ImageView clothAvatar;
    @BindView(R.id.hair_view)
    public ImageView hairAvatar;
    @BindView(R.id.continueButtonAvatar)
    public ImageView continueButton;

    private int eye;
    private int hair;
    private int skin;
    private int cloth;

    private DataSource source;
    private AvatarRoomPresenter presenter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get the database instance
        source = InjectionClass.provideDataSource(this);

        // initialize views
        setContentView(R.layout.avatar_room);
        ButterKnife.bind(this);

        // presenter init
        presenter = new AvatarRoomPresenter(this, this);

        // initalizing avatar view
        init();

    }

    // checking passed values from intent or use preferences values for avatar setup
    private void init() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            eye = extras.getInt("eye");
            skin = extras.getInt("skin");
            hair = extras.getInt("hair");
            cloth = extras.getInt("cloth");
        }
        else {
            eye = source.getCurrentEyeValue();
            hair = source.getCurrentHairValue();
            skin = source.getCurrentHairValue();
            cloth = source.getCurrentClothValue();
        }

        presenter.setValues(eye, hair, skin, cloth);
    }

    @OnClick(R.id.continueButtonAvatar)
    public void continueButtonListener(View view) {
        source.resetPurchase();
        Intent intent = new Intent(AvatarRoomActivity.this, FinalAvatarActivity.class);
        //passing values through intent
        intent.putExtra("eye" , eye);
        intent.putExtra("skin", skin);
        intent.putExtra("cloth", cloth);
        intent.putExtra("hair", hair);

        finish();
        startActivity(intent);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.eyes_left)
    public void eyeLeftListener(View view) {
        eye = (eye - 1) % SessionHistory.eyesTotalNo;
        if (eye == 0) {
            eye = SessionHistory.eyesTotalNo;
        }
        presenter.calculateEyeValue(eye);
    }

    @OnClick(R.id.eyes_right)
    public void eyeRightListener(View view) {
        eye = (eye + SessionHistory.eyesTotalNo)
                % SessionHistory.eyesTotalNo + 1;
        presenter.calculateEyeValue(eye);
    }

    @OnClick(R.id.skin_left)
    public void skinLeftListener(View view) {
        skin = (skin - 1) % SessionHistory.skinTotalNo;
        if (skin == 0) {
            skin = SessionHistory.skinTotalNo;
        }
        presenter.calculateSkinValue(skin);
    }

    @OnClick(R.id.skin_right)
    public void skinRightListener(View view) {
        skin = (skin + SessionHistory.skinTotalNo)
                % SessionHistory.skinTotalNo + 1;
        presenter.calculateSkinValue(skin);
    }

    @OnClick(R.id.hair_left)
    public void hairLeftListener(View view) {
        hair = (hair - 1) % SessionHistory.hairTotalNo;
        if (hair == 0) {
            hair = SessionHistory.hairTotalNo;
        }
        presenter.calculateHairValue(hair);
    }

    @OnClick(R.id.hair_right)
    public void hairRightListener(View view) {
        hair = (hair + SessionHistory.hairTotalNo)
                % SessionHistory.hairTotalNo + 1;
        presenter.calculateHairValue(hair);
    }

    @OnClick(R.id.clothes_left)
    public void clothLeftListener(View view) {
        cloth = (cloth - 1) % SessionHistory.clothTotalNo;
        if (cloth == 0) {
            cloth = SessionHistory.clothTotalNo;
        }
        presenter.calculateClothValue(cloth);
    }

    @OnClick(R.id.clothes_right)
    public void clothRightListener(View view) {
        cloth = (cloth + SessionHistory.clothTotalNo)
                % SessionHistory.clothTotalNo + 1;
        presenter.calculateClothValue(cloth);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataSource.clearInstance();
        presenter = null;
    }
}
