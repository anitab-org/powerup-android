/**
 * @desc sets up the “Avatar Room” for user to customize avatar features.
 * Allows user to scroll through different face/hair/clothing options.
 */

package powerup.systers.com;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.util.Random;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;

public class AvatarRoomActivity extends Activity {

    public static Activity avatarRoomInstance;
    private DatabaseHandler mDbHandler;
    private ImageView eyeView;
    private ImageView faceView;
    private ImageView clothView;
    private ImageView hairView;
    private ImageView eyeAvatar;
    private ImageView faceAvatar;
    private ImageView clothAvatar;
    private ImageView hairAvatar;
    private Integer eye = 1;
    private Integer hair = 1;
    private Integer face = 1;
    private Integer cloth = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        avatarRoomInstance = this;
        setContentView(R.layout.avatar_room);
        eyeView = (ImageView) findViewById(R.id.eyes);
        faceView = (ImageView) findViewById(R.id.face);
        clothView = (ImageView) findViewById(R.id.clothes);
        hairView = (ImageView) findViewById(R.id.hair);
        eyeAvatar = (ImageView) findViewById(R.id.eyeView);
        hairAvatar = (ImageView) findViewById(R.id.hairView);
        faceAvatar = (ImageView) findViewById(R.id.faceView);
        clothAvatar = (ImageView) findViewById(R.id.clothView);
        ImageButton eyeLeft = (ImageButton) findViewById(R.id.eyeLeft);
        ImageButton eyeRight = (ImageButton) findViewById(R.id.eyeRight);
        ImageButton faceLeft = (ImageButton) findViewById(R.id.faceLeft);
        ImageButton faceRight = (ImageButton) findViewById(R.id.faceRight);
        ImageButton clothLeft = (ImageButton) findViewById(R.id.clotheLeft);
        ImageButton clothRight = (ImageButton) findViewById(R.id.clotheRight);
        ImageButton hairLeft = (ImageButton) findViewById(R.id.hairLeft);
        ImageButton hairRight = (ImageButton) findViewById(R.id.hairRight);
        Button continueButton = (Button) findViewById(R.id.continueButtonAvatar);

        eyeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eye = (eye - 1) % SessionHistory.eyesTotalNo;
                if (eye == 0) {
                    eye = SessionHistory.eyesTotalNo;
                }
                String eyeImageName = getResources().getString(R.string.eye);
                eyeImageName = eyeImageName + eye.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(eyeImageName);
                    eyeView.setImageResource(photoNameField.getInt(ourRID));
                    eyeAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        eyeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eye = (eye + SessionHistory.eyesTotalNo)
                        % SessionHistory.eyesTotalNo + 1;
                String eyeImageName = getResources().getString(R.string.eye);
                eyeImageName = eyeImageName + eye.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(eyeImageName);
                    eyeView.setImageResource(photoNameField.getInt(ourRID));
                    eyeAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        faceLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                face = (face - 1) % SessionHistory.faceTotalNo;
                if (face == 0) {
                    face = SessionHistory.faceTotalNo;
                }
                String faceImageName = getResources().getString(R.string.face);
                faceImageName = faceImageName + face.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(faceImageName);
                    faceView.setImageResource(photoNameField.getInt(ourRID));
                    faceAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        faceRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                face = (face + SessionHistory.faceTotalNo)
                        % SessionHistory.faceTotalNo + 1;
                String faceImageName = getResources().getString(R.string.face);
                faceImageName = faceImageName + face.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(faceImageName);
                    faceView.setImageResource(photoNameField.getInt(ourRID));
                    faceAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        clothLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cloth = (cloth - 1) % SessionHistory.clothTotalNo;
                if (cloth == 0) {
                    cloth = SessionHistory.clothTotalNo;
                }
                String clothImageName = getResources().getString(R.string.cloth);
                clothImageName = clothImageName + cloth.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(clothImageName);
                    clothView.setImageResource(photoNameField.getInt(ourRID));
                    clothAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        clothRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cloth = (cloth + SessionHistory.clothTotalNo)
                        % SessionHistory.clothTotalNo + 1;
                String clothImageName = getResources().getString(R.string.cloth);
                clothImageName = clothImageName + cloth.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(clothImageName);
                    clothView.setImageResource(photoNameField.getInt(ourRID));
                    clothAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        hairLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hair = (hair - 1) % SessionHistory.hairTotalNo;
                if (hair == 0) {
                    hair = SessionHistory.hairTotalNo;
                }
                String hairImageName = getResources().getString(R.string.hair);
                hairImageName = hairImageName + hair.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(hairImageName);
                    hairView.setImageResource(photoNameField.getInt(ourRID));
                    hairAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        hairRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hair = (hair + SessionHistory.hairTotalNo)
                        % SessionHistory.hairTotalNo + 1;
                String hairImageName = getResources().getString(R.string.hair);
                hairImageName = hairImageName + hair.toString();
                R.drawable ourRID = new R.drawable();
                java.lang.reflect.Field photoNameField;
                try {
                    photoNameField = ourRID.getClass().getField(hairImageName);
                    hairView.setImageResource(photoNameField.getInt(ourRID));
                    hairAvatar.setImageResource(photoNameField.getInt(ourRID));
                } catch (NoSuchFieldException | IllegalAccessException
                        | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmDbHandler().open();
                getmDbHandler().setAvatarEye(eye);
                getmDbHandler().setAvatarFace(face);
                getmDbHandler().setAvatarHair(hair);
                getmDbHandler().setAvatarCloth(cloth);
                getmDbHandler().setAvatarBag(0);
                getmDbHandler().setAvatarGlasses(0);
                getmDbHandler().setAvatarHat(0);
                getmDbHandler().setAvatarNecklace(0);
                getmDbHandler().updateComplete();//set all the complete fields back to 0
                getmDbHandler().updateReplayed();//set all the replayed fields back to 0
                SessionHistory.totalPoints = 0;    //reset the points stored
                SessionHistory.currSessionID = 1;
                SessionHistory.currScenePoints = 0;
                getmDbHandler().resetPurchase();
                Random random = new Random();
                Integer healing = random.nextInt(101 - 1) + 1;
                getmDbHandler().setHealing(healing);

                random = new Random();
                Integer strength = random.nextInt(101 - 1) + 1;
                getmDbHandler().setStrength(strength);

                random = new Random();
                Integer invisibility = random.nextInt(101 - 1) + 1;
                getmDbHandler().setInvisibility(invisibility);

                random = new Random();
                Integer telepathy = random.nextInt(101 - 1) + 1;
                getmDbHandler().setTelepathy(telepathy);
                Log.i("Powers", mDbHandler.getHealing() + " " + mDbHandler.getInvisibility() +
                        " " + mDbHandler.getStrength());
                Intent intent = new Intent(AvatarRoomActivity.this, AvatarActivity.class);
                intent.putExtra(getResources().getString(R.string.from_activity), 1);
                startActivityForResult(intent, 0);
            }
        });
        getmDbHandler().close();
    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
