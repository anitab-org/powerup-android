/**
 * @desc allows user to purchase/change clothes using the current session's total points
 * (karma) and sets progress of power and health bars.
 */

package powerup.systers.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;

import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.db.DatabaseHandler;

public class SelectFeaturesActivity extends AppCompatActivity {

    public Activity selectFeatureInstance;
    private Boolean storeItemSelected = false;
    private Boolean storeBagSelected = false;
    private Boolean storeHatSelected = false;
    private Boolean storeGlassesSelected = false;
    private Boolean storeNecklaceSelected = false;
    Integer bag = 1;
    Integer glasses = 1;
    Integer hat = 1;
    Integer necklace = 1;
    Integer hatPurchased = 0;
    Integer glassesPurchased = 0;
    Integer bagPurchased = 0;
    Integer necklacePurchased = 0;
    private Integer hair = 1;
    private Integer accessory = 1;
    private Integer cloth = 1;
    private String value;
    private DatabaseHandler mDbHandler;
    private ImageView imageViewSelectFeature;
    private TextView tvPaidSelectFeature;
    private TextView tvPaidHandbag;
    private TextView tvPaidGlasses;
    private TextView tvPaidHat;
    private TextView tvPaidNecklace;
    private ImageView hairView;
    private ImageView clothView;
    private TextView tvPoints;

    private ImageView bagView;
    private ImageView glassesView;
    private ImageView hatView;
    private ImageView necklaceView;

    private ImageView imageViewhandbag;
    private ImageView imageViewGlasses;
    private ImageView imageViewHat;
    private ImageView imageViewNecklace;

    private TextView tvHandbagPoints;
    private TextView tvGlassesPoints;
    private TextView tvHatPoints;
    private TextView tvNecklacePoints;


    public SelectFeaturesActivity() {
        selectFeatureInstance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_features);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        LinearLayout linearLayouthandbag = (LinearLayout) findViewById(R.id.linearLayouthandbag);
        LinearLayout linearLayoutGlasses = (LinearLayout) findViewById(R.id.linearLayoutGlasses);
        LinearLayout linearLayoutHat = (LinearLayout) findViewById(R.id.linearLayoutHat);
        LinearLayout linearLayoutNecklace = (LinearLayout) findViewById(R.id.linearLayoutNecklace);
        TextView karmaPoints = (TextView) findViewById(R.id.karmaPoints);
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
        ImageView eyeView = (ImageView) findViewById(R.id.eyeView);
        ImageView faceView = (ImageView) findViewById(R.id.faceView);
        hairView = (ImageView) findViewById(R.id.hairView);
        clothView = (ImageView) findViewById(R.id.clothView);
        String eyeImageName = getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            eyeView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String faceImageName = getResources().getString(R.string.face);
        faceImageName = faceImageName + getmDbHandler().getAvatarFace();
        try {
            photoNameField = ourRID.getClass().getField(faceImageName);
            faceView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String clothImageName = getResources().getString(R.string.cloth);
        clothImageName = clothImageName + getmDbHandler().getAvatarCloth();
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            clothView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String hairImageName = getResources().getString(R.string.hair);
        hairImageName = hairImageName + getmDbHandler().getAvatarHair();
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            hairView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        value = getIntent().getExtras().getString(getResources().getString(R.string.feature));

        ImageView continueButton = (ImageView) findViewById(R.id.continueButton);

        imageViewSelectFeature = (ImageView) findViewById(R.id.imageViewSelectFeature);
        tvPaidSelectFeature = (TextView) findViewById(R.id.tvPaidSelectFeature);
        tvPaidHandbag = (TextView) findViewById(R.id.tvPaidHandbag);
        tvPaidGlasses = (TextView) findViewById(R.id.tvPaidGlasses);
        tvPaidHat = (TextView) findViewById(R.id.tvPaidHat);
        tvPaidNecklace = (TextView) findViewById(R.id.tvPaidNecklace);

        if (value != null ? value.equalsIgnoreCase(getResources().getString(R.string.cloth)) : false) {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayoutGlasses.setVisibility(View.GONE);
            linearLayouthandbag.setVisibility(View.GONE);
            linearLayoutHat.setVisibility(View.GONE);
            linearLayoutNecklace.setVisibility(View.GONE);
            imageViewSelectFeature.setImageDrawable(getResources().getDrawable(R.drawable.cloth1));
            int isPurchased = getmDbHandler().getPurchasedClothes(1);
            if (isPurchased == 1) {
                imageViewSelectFeature.setAlpha((float) 0.5);
                tvPaidSelectFeature.setText(getResources().getString(R.string.paid_feature));
            }
            TextView tv = (TextView) findViewById(R.id.textViewSelectFeature);
            tvPoints = (TextView) findViewById(R.id.tvSelectFeaturePoints);
            tvPoints.setText(String.valueOf(getmDbHandler().getPointsClothes(cloth)));
            tv.setText(R.string.cloth);
            // Left and right buttons allow user to scroll through clothing options
            ImageButton left = (ImageButton) findViewById(R.id.leftSelectFeature);
            ImageButton right = (ImageButton) findViewById(R.id.rightSelectFeature);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeItemSelected = true;
                    cloth = (cloth - 1) % SessionHistory.clothTotalNo;
                    if (cloth == 0) {
                        // Go back to the last cloth
                        cloth = SessionHistory.clothTotalNo;
                    }
                    resourceSetter(value, cloth, "cloth");
                }
            });

            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeItemSelected = true;
                    cloth = (cloth + SessionHistory.clothTotalNo)
                            % SessionHistory.clothTotalNo + 1;
                    resourceSetter(value, cloth, "cloth");
                }
            });
        } else if (value.equalsIgnoreCase(getResources().getString(R.string.hair))) {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayoutGlasses.setVisibility(View.GONE);
            linearLayouthandbag.setVisibility(View.GONE);
            linearLayoutHat.setVisibility(View.GONE);
            linearLayoutNecklace.setVisibility(View.GONE);
            imageViewSelectFeature.setImageDrawable(getResources().getDrawable(R.drawable.hair1));
            int isPurchased = getmDbHandler().getPurchasedHair(1);
            if (isPurchased == 1) {
                imageViewSelectFeature.setAlpha((float) 0.5);
                tvPaidSelectFeature.setText(getResources().getString(R.string.paid_feature));
            }
            TextView tv = (TextView) findViewById(R.id.textViewSelectFeature);
            tvPoints = (TextView) findViewById(R.id.tvSelectFeaturePoints);
            tvPoints.setText(String.valueOf(getmDbHandler().getPointsHair(hair)));
            tv.setText(R.string.hair);
            ImageButton left = (ImageButton) findViewById(R.id.leftSelectFeature);
            ImageButton right = (ImageButton) findViewById(R.id.rightSelectFeature);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeItemSelected = true;
                    hair = (hair - 1) % SessionHistory.hairTotalNo;
                    if (hair == 0) {
                        hair = SessionHistory.hairTotalNo;
                    }
                    resourceSetter(value, hair, "hair");
                }
            });

            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeItemSelected = true;
                    hair = (hair + SessionHistory.hairTotalNo)
                            % SessionHistory.hairTotalNo + 1;
                    resourceSetter(value, hair, "hair");
                }
            });
        } else if (value.equalsIgnoreCase(getResources().getString(R.string.accessory))) {
            linearLayout.setVisibility(View.GONE);
            linearLayoutGlasses.setVisibility(View.VISIBLE);
            linearLayouthandbag.setVisibility(View.VISIBLE);
            linearLayoutHat.setVisibility(View.VISIBLE);
            linearLayoutNecklace.setVisibility(View.VISIBLE);

            ImageButton leftHandbag = (ImageButton) findViewById(R.id.leftHandbag);
            ImageButton rightHandbag = (ImageButton) findViewById(R.id.rightHandbag);
            ImageButton leftGlasses = (ImageButton) findViewById(R.id.leftGlasses);
            ImageButton rightGlasses = (ImageButton) findViewById(R.id.rightGlasses);
            ImageButton leftHat = (ImageButton) findViewById(R.id.leftHat);
            ImageButton rightHat = (ImageButton) findViewById(R.id.rightHat);
            ImageButton leftNecklace = (ImageButton) findViewById(R.id.leftNecklace);
            ImageButton rightNecklace = (ImageButton) findViewById(R.id.rightNecklace);

            bagView = (ImageView) findViewById(R.id.bagView);
            glassesView = (ImageView) findViewById(R.id.glassesView);
            hatView = (ImageView) findViewById(R.id.hatView);
            necklaceView = (ImageView) findViewById(R.id.necklaceView);

            imageViewhandbag = (ImageView) findViewById(R.id.imageViewhandbag);
            imageViewGlasses = (ImageView) findViewById(R.id.imageViewGlasses);
            imageViewHat = (ImageView) findViewById(R.id.imageViewHat);
            imageViewNecklace = (ImageView) findViewById(R.id.imageViewNecklace);

            tvHandbagPoints = (TextView) findViewById(R.id.tvHandbagPoints);
            tvGlassesPoints = (TextView) findViewById(R.id.tvGlassesPoints);
            tvHatPoints = (TextView) findViewById(R.id.tvHatPoints);
            tvNecklacePoints = (TextView) findViewById(R.id.tvNecklacePoints);

            tvHandbagPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory)));
            tvGlassesPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory + SessionHistory.bagTotalNo)));
            tvHatPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory
                    + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo)));
            tvNecklacePoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory
                    + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo)));
            leftHandbag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeBagSelected = true;
                    bag = (bag - 1) % SessionHistory.bagTotalNo;
                    if (bag == 0) {
                        bag = SessionHistory.bagTotalNo;
                    }
                    resourceSetter(value, bag, getResources().getString(R.string.bag));
                }
            });

            rightHandbag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeBagSelected = true;
                    bag = (bag + SessionHistory.bagTotalNo)
                            % SessionHistory.bagTotalNo + 1;
                    resourceSetter(value, bag, getResources().getString(R.string.bag));
                }
            });

            leftGlasses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeGlassesSelected = true;
                    glasses = (glasses - 1) % SessionHistory.bagTotalNo;
                    if (glasses == 0) {
                        glasses = SessionHistory.glassesTotalNo;
                    }
                    resourceSetter(value, glasses, getResources().getString(R.string.glasses));
                }
            });

            rightGlasses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeGlassesSelected = true;
                    glasses = (glasses + SessionHistory.glassesTotalNo)
                            % SessionHistory.glassesTotalNo + 1;
                    resourceSetter(value, glasses, getResources().getString(R.string.glasses));
                }
            });

            leftHat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeHatSelected = true;
                    hat = (hat - 1) % SessionHistory.hatTotalNo;
                    if (hat == 0) {
                        hat = SessionHistory.hatTotalNo;
                    }
                    resourceSetter(value, hat, getResources().getString(R.string.hat));
                }
            });

            rightHat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeHatSelected = true;
                    hat = (hat + SessionHistory.hatTotalNo)
                            % SessionHistory.hatTotalNo + 1;
                    resourceSetter(value, hat, getResources().getString(R.string.hat));
                }
            });

            leftNecklace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeNecklaceSelected = true;
                    necklace = (necklace - 1) % SessionHistory.necklaceTotalNo;
                    if (necklace == 0) {
                        necklace = SessionHistory.necklaceTotalNo;
                    }
                    resourceSetter(value, necklace, getResources().getString(R.string.necklace));
                }
            });

            rightNecklace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storeNecklaceSelected = true;
                    necklace = (necklace + SessionHistory.necklaceTotalNo)
                            % SessionHistory.necklaceTotalNo + 1;
                    resourceSetter(value, necklace, getResources().getString(R.string.necklace));
                }
            });
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmDbHandler().open();
                if (value.equalsIgnoreCase(getResources().getString(R.string.cloth))) {
                    if (SessionHistory.totalPoints < getmDbHandler().getPointsClothes(cloth))
                        Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                    else {
                        getmDbHandler().setAvatarCloth(cloth);
                        getmDbHandler().setPurchasedClothes(cloth);
                        SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsClothes(cloth);
                    }
                } else if (value.equalsIgnoreCase(getResources().getString(R.string.hair))) {
                    if (SessionHistory.totalPoints < getmDbHandler().getPointsHair(hair))
                        Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                    else {
                        getmDbHandler().setAvatarHair(hair);
                        getmDbHandler().setPurchasedHair(hair);
                        SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsHair(hair);
                    }
                } else if (value.equalsIgnoreCase(getResources().getString(R.string.accessory))) {
                    if (hatPurchased != 0) {
                        if (SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(hatPurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else {
                            getmDbHandler().setPurchasedAccessories(hatPurchased);
                            getmDbHandler().setAvatarHat(hat);
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(hatPurchased);
                        }
                    }
                    if (glassesPurchased != 0) {
                        if (SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(glassesPurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else {
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(glassesPurchased);
                            getmDbHandler().setPurchasedAccessories(glassesPurchased);
                            getmDbHandler().setAvatarGlasses(glasses);
                        }
                    }
                    if (bagPurchased != 0) {
                        if (SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(bagPurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else {
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(bagPurchased);
                            getmDbHandler().setPurchasedAccessories(bagPurchased);
                            getmDbHandler().setAvatarBag(bag);
                        }
                    }
                    if (necklacePurchased != 0) {
                        if (SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(necklacePurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else {
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(necklacePurchased);
                            getmDbHandler().setPurchasedAccessories(necklacePurchased);
                            getmDbHandler().setAvatarNecklace(necklace);
                        }
                    }
                }
                Intent intent = new Intent(SelectFeaturesActivity.this, AvatarActivity.class);
                intent.putExtra(getResources().getString(R.string.feature), 2);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                getmDbHandler().close();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("bagNum", bag);
        savedInstanceState.putInt("glassesNum", glasses);
        savedInstanceState.putInt("hatNum", hat);
        savedInstanceState.putInt("necklaceNum", necklace);
        savedInstanceState.putInt("hairNum", hair);
        savedInstanceState.putInt("clothNum", cloth);
        savedInstanceState.putInt("accessoryNum", accessory);
        savedInstanceState.putBoolean("storeItemSelected", storeItemSelected);
        savedInstanceState.putBoolean("storeBagSelected", storeBagSelected);
        savedInstanceState.putBoolean("storeHatSelected", storeHatSelected);
        savedInstanceState.putBoolean("storeGlassesSelected", storeGlassesSelected);
        savedInstanceState.putBoolean("storeNecklaceSelected", storeNecklaceSelected);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        accessory = savedInstanceState.getInt("accessoryNum");
        storeItemSelected = savedInstanceState.getBoolean("storeItemSelected");
        if (value.equalsIgnoreCase(getResources().getString(R.string.accessory))) {
            storeBagSelected = savedInstanceState.getBoolean("storeBagSelected");
            storeHatSelected = savedInstanceState.getBoolean("storeHatSelected");
            storeGlassesSelected = savedInstanceState.getBoolean("storeGlassesSelected");
            storeNecklaceSelected = savedInstanceState.getBoolean("storeNecklaceSelected");
            bag = savedInstanceState.getInt("bagNum");
            hat = savedInstanceState.getInt("hatNum");
            glasses = savedInstanceState.getInt("glassesNum");
            necklace = savedInstanceState.getInt("necklaceNum");
            if (storeBagSelected) {
                resourceSetter(value, bag, getResources().getString(R.string.bag));
            }
            if (storeHatSelected) {
                resourceSetter(value, hat, getResources().getString(R.string.hat));
            }
            if (storeGlassesSelected) {
                resourceSetter(value, glasses, getResources().getString(R.string.glasses));
            }
            if (storeNecklaceSelected) {
                resourceSetter(value, necklace, getResources().getString(R.string.necklace));
            }
        } else if (value.equalsIgnoreCase(getResources().getString(R.string.hair)) && storeItemSelected) {
            hair = savedInstanceState.getInt("hairNum");
            resourceSetter(value, hair, getResources().getString(R.string.hair));
        } else if (value.equalsIgnoreCase(getResources().getString(R.string.cloth)) && storeItemSelected) {
            cloth = savedInstanceState.getInt("clothNum");
            resourceSetter(value, cloth, getResources().getString(R.string.cloth));
        }
    }

    public void resourceSetter(String valueType, int selectionNum, String subType) {
        String imageName = "";
        Integer isPurchased = 0;
        if (valueType.equalsIgnoreCase(getResources().getString(R.string.cloth)) ||
                valueType.equalsIgnoreCase(getResources().getString(R.string.hair))) {
            imageViewSelectFeature.setAlpha((float) 1);
            tvPaidSelectFeature.setText(getResources().getString(R.string.empty));
            if (valueType.equalsIgnoreCase(getResources().getString(R.string.cloth))) {
                isPurchased = mDbHandler.getPurchasedClothes(selectionNum);
            } else if (valueType.equalsIgnoreCase(getResources().getString(R.string.hair))) {
                isPurchased = mDbHandler.getPurchasedHair(selectionNum);
            }
            if (isPurchased == 1) {
                imageViewSelectFeature.setAlpha((float) 0.5);
                tvPaidSelectFeature.setText(getResources().getString(R.string.paid_feature));
            }
            imageName = valueType.toLowerCase();
        } else if (valueType.equalsIgnoreCase(getResources().getString(R.string.accessory))) {
            int selectionNumPurchased = 0;
            if (subType.equalsIgnoreCase(getResources().getString(R.string.bag))) {
                selectionNumPurchased = selectionNum;
                imageViewhandbag.setAlpha((float) 1);
                tvPaidHandbag.setText(getResources().getString(R.string.empty));
                isPurchased = mDbHandler.getPurchasedAccessories(selectionNumPurchased);
                if (isPurchased == 1) {
                    imageViewhandbag.setAlpha((float) 0.5);
                    tvPaidHandbag.setText(getResources().getString(R.string.paid_feature_small));
                }
                imageName = getResources().getString(R.string.bag);
            } else if (subType.equalsIgnoreCase(getResources().getString(R.string.hat))) {
                selectionNumPurchased = selectionNum + SessionHistory.bagTotalNo;
                imageViewHat.setAlpha((float) 1);
                tvPaidHat.setText(getResources().getString(R.string.empty));
                isPurchased = mDbHandler.getPurchasedAccessories(selectionNumPurchased);
                if (isPurchased == 1) {
                    imageViewHat.setAlpha((float) 0.5);
                    tvPaidHat.setText(getResources().getString(R.string.paid_feature_small));
                }
                imageName = getResources().getString(R.string.hat);
            } else if (subType.equalsIgnoreCase(getResources().getString(R.string.glasses))) {
                selectionNumPurchased = selectionNum + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo;
                imageViewGlasses.setAlpha((float) 1);
                tvPaidGlasses.setText(getResources().getString(R.string.empty));
                isPurchased = mDbHandler.getPurchasedAccessories(selectionNumPurchased);
                if (isPurchased == 1) {
                    imageViewGlasses.setAlpha((float) 0.5);
                    tvPaidGlasses.setText(getResources().getString(R.string.paid_feature_small));
                }
                imageName = getResources().getString(R.string.glasses);
            } else if (subType.equalsIgnoreCase(getResources().getString(R.string.necklace))) {
                selectionNumPurchased = selectionNum + SessionHistory.bagTotalNo +
                        SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo;
                imageViewNecklace.setAlpha((float) 1);
                tvPaidNecklace.setText(getResources().getString(R.string.empty));
                isPurchased = mDbHandler.getPurchasedAccessories(selectionNumPurchased);
                if (isPurchased == 1) {
                    imageViewNecklace.setAlpha((float) 0.5);
                    tvPaidNecklace.setText(getResources().getString(R.string.paid_feature_small));
                }
                imageName = getResources().getString(R.string.necklace);
            }
        }

        imageName = imageName + selectionNum;
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(imageName);
            if (valueType.equalsIgnoreCase(getResources().getString(R.string.accessory))) {
                if (subType.equalsIgnoreCase(getResources().getString(R.string.bag))) {
                    bagView.setImageResource(photoNameField.getInt(ourRID));
                    imageViewhandbag.setImageResource(photoNameField.getInt(ourRID));
                    tvHandbagPoints.setText(String.valueOf(getmDbHandler().
                            getPointsAccessories(bag)));
                } else if (subType.equalsIgnoreCase(getResources().getString(R.string.hat))) {
                    hatView.setImageResource(photoNameField.getInt(ourRID));
                    imageViewHat.setImageResource(photoNameField.getInt(ourRID));
                    tvHatPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(hat
                            + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo)));
                } else if (subType.equalsIgnoreCase(getResources().getString(R.string.glasses))) {
                    glassesView.setImageResource(photoNameField.getInt(ourRID));
                    imageViewGlasses.setImageResource(photoNameField.getInt(ourRID));
                    tvGlassesPoints.setText(String.valueOf(getmDbHandler().
                            getPointsAccessories(glasses + SessionHistory.bagTotalNo)));
                } else if (subType.equalsIgnoreCase(getResources().getString(R.string.necklace))) {
                    necklaceView.setImageResource(photoNameField.getInt(ourRID));
                    imageViewNecklace.setImageResource(photoNameField.getInt(ourRID));
                    tvNecklacePoints.setText(String.valueOf(getmDbHandler().
                            getPointsAccessories(necklace
                                    + SessionHistory.bagTotalNo +
                                    SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo)));
                }
            } else if (valueType.equalsIgnoreCase(getResources().getString(R.string.cloth))) {
                imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                clothView.setImageResource(photoNameField.getInt(ourRID));
                tvPoints.setText(String.valueOf(getmDbHandler().getPointsClothes(selectionNum)));
            } else if (valueType.equalsIgnoreCase(getResources().getString(R.string.hair))) {
                imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                hairView.setImageResource(photoNameField.getInt(ourRID));
                tvPoints.setText(String.valueOf(getmDbHandler().getPointsHair(selectionNum)));
            }
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }
    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }
}
