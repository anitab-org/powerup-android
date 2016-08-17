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

    public static Activity selectFeatureInstance;
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
    private DatabaseHandler mDbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_features);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        selectFeatureInstance = this;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        LinearLayout linearLayouthandbag = (LinearLayout) findViewById(R.id.linearLayouthandbag);
        LinearLayout linearLayoutGlasses = (LinearLayout) findViewById(R.id.linearLayoutGlasses);
        LinearLayout linearLayoutHat = (LinearLayout) findViewById(R.id.linearLayoutHat);
        LinearLayout linearLayoutNecklace = (LinearLayout) findViewById(R.id.linearLayoutNecklace);

        ImageView eyeView = (ImageView) findViewById(R.id.eyeView);
        ImageView faceView = (ImageView) findViewById(R.id.faceView);
        final ImageView hairView = (ImageView) findViewById(R.id.hairView);
        final ImageView clothView = (ImageView) findViewById(R.id.clothView);
        String eyeImageName = getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
        R.drawable ourRID = new R.drawable();
        java.lang.reflect.Field photoNameField;
        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            eyeView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        String faceImageName = getResources().getString(R.string.face);
        faceImageName = faceImageName + getmDbHandler().getAvatarFace();
        try {
            photoNameField = ourRID.getClass().getField(faceImageName);
            faceView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        String clothImageName = getResources().getString(R.string.cloth);
        clothImageName = clothImageName + getmDbHandler().getAvatarCloth();
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            clothView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        String hairImageName = getResources().getString(R.string.hair);
        hairImageName = hairImageName + getmDbHandler().getAvatarHair();
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            hairView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException e) {
            e.printStackTrace();
        }

        IconRoundCornerProgressBar powerBarHealing = (IconRoundCornerProgressBar) findViewById(R.id.powerbarHealing);
        powerBarHealing.setIconImageResource(R.drawable.icon_healing);
        powerBarHealing.setProgress(mDbHandler.getHealing());
        IconRoundCornerProgressBar powerbarInvisibility = (IconRoundCornerProgressBar) findViewById(R.id.powerbarInvisibility);
        powerbarInvisibility.setIconImageResource(R.drawable.icon_invisibility);
        powerbarInvisibility.setProgress(mDbHandler.getInvisibility());
        IconRoundCornerProgressBar powerbarStrength = (IconRoundCornerProgressBar) findViewById(R.id.powerbarStrength);
        powerbarStrength.setIconImageResource(R.drawable.icon_strength);
        powerbarStrength.setProgress(mDbHandler.getStrength());
        IconRoundCornerProgressBar powerbarTelepathy = (IconRoundCornerProgressBar) findViewById(R.id.powerbarTelepathy);
        powerbarTelepathy.setIconImageResource(R.drawable.icon_telepathy);
        powerbarTelepathy.setProgress(mDbHandler.getTelepathy());

        final String value = getIntent().getExtras().getString(getResources().getString(R.string.feature));
        Button continueButton = (Button) findViewById(R.id.continueButton);
        final ImageView imageViewSelectFeature = (ImageView) findViewById(R.id.imageViewSelectFeature);
        final TextView tvPaidSelectFeature = (TextView) findViewById(R.id.tvPaidSelectFeature);
        final TextView tvPaidHandbag = (TextView) findViewById(R.id.tvPaidHandbag);
        final TextView tvPaidGlasses = (TextView) findViewById(R.id.tvPaidGlasses);
        final TextView tvPaidHat = (TextView) findViewById(R.id.tvPaidHat);
        final TextView tvPaidNecklace = (TextView) findViewById(R.id.tvPaidNecklace);

        if (value.equalsIgnoreCase(getResources().getString(R.string.cloth))) {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayoutGlasses.setVisibility(View.GONE);
            linearLayouthandbag.setVisibility(View.GONE);
            linearLayoutHat.setVisibility(View.GONE);
            linearLayoutNecklace.setVisibility(View.GONE);
            imageViewSelectFeature.setImageDrawable(getResources().getDrawable(R.drawable.cloth1));
            TextView tv = (TextView) findViewById(R.id.textViewSelectFeature);
            final TextView tvPoints = (TextView) findViewById(R.id.tvSelectFeaturePoints);
            tvPoints.setText(String.valueOf(getmDbHandler().getPointsClothes(cloth)));
            tv.setText(R.string.cloth);
            ImageButton left = (ImageButton) findViewById(R.id.leftSelectFeature);
            ImageButton right = (ImageButton) findViewById(R.id.rightSelectFeature);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cloth = (cloth - 1) % SessionHistory.clothTotalNo;
                    if (cloth == 0) {
                        cloth = SessionHistory.clothTotalNo;
                    }
                    imageViewSelectFeature.setAlpha((float) 1);
                    tvPaidSelectFeature.setText(getResources().getString(R.string.empty));
                    Integer isPurchased = mDbHandler.getPurchasedClothes(cloth);
                    if (isPurchased == 1) {
                        imageViewSelectFeature.setAlpha((float) 0.5);
                        tvPaidSelectFeature.setText(getResources().getString(R.string.paid_feature));
                    }
                    String clothImageName = getResources().getString(R.string.cloth);
                    clothImageName = clothImageName + cloth.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(clothImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                        clothView.setImageResource(photoNameField.getInt(ourRID));
                        tvPoints.setText(String.valueOf(getmDbHandler().getPointsClothes(cloth)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cloth = (cloth + SessionHistory.clothTotalNo)
                            % SessionHistory.clothTotalNo + 1;
                    Integer isPurchased = mDbHandler.getPurchasedClothes(cloth);
                    imageViewSelectFeature.setAlpha((float) 1);
                    tvPaidSelectFeature.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewSelectFeature.setAlpha((float) 0.5);
                        tvPaidSelectFeature.setText(getResources().getString(R.string.paid_feature));
                    }
                    String clothImageName = getResources().getString(R.string.cloth);
                    clothImageName = clothImageName + cloth.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(clothImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                        clothView.setImageResource(photoNameField.getInt(ourRID));
                        tvPoints.setText(String.valueOf(getmDbHandler().getPointsClothes(cloth)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (value.equalsIgnoreCase(getResources().getString(R.string.hair))) {
            linearLayout.setVisibility(View.VISIBLE);
            linearLayoutGlasses.setVisibility(View.GONE);
            linearLayouthandbag.setVisibility(View.GONE);
            linearLayoutHat.setVisibility(View.GONE);
            linearLayoutNecklace.setVisibility(View.GONE);
            imageViewSelectFeature.setImageDrawable(getResources().getDrawable(R.drawable.hair1));
            TextView tv = (TextView) findViewById(R.id.textViewSelectFeature);
            final TextView tvPoints = (TextView) findViewById(R.id.tvSelectFeaturePoints);
            tvPoints.setText(String.valueOf(getmDbHandler().getPointsHair(hair)));
            tv.setText(R.string.hair);
            ImageButton left = (ImageButton) findViewById(R.id.leftSelectFeature);
            ImageButton right = (ImageButton) findViewById(R.id.rightSelectFeature);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hair = (hair - 1) % SessionHistory.hairTotalNo;
                    if (hair == 0) {
                        hair = SessionHistory.hairTotalNo;
                    }
                    Integer isPurchased = mDbHandler.getPurchasedHair(hair);
                    imageViewSelectFeature.setAlpha((float) 1);
                    tvPaidSelectFeature.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewSelectFeature.setAlpha((float) 0.5);
                        tvPaidSelectFeature.setText(getResources().getString(R.string.paid_feature));
                    }
                    String hairImageName = getResources().getString(R.string.hair);
                    hairImageName = hairImageName + hair.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(hairImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                        hairView.setImageResource(photoNameField.getInt(ourRID));
                        tvPoints.setText(String.valueOf(getmDbHandler().getPointsHair(hair)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hair = (hair + SessionHistory.hairTotalNo)
                            % SessionHistory.hairTotalNo + 1;
                    Integer isPurchased = mDbHandler.getPurchasedHair(hair);
                    imageViewSelectFeature.setAlpha((float) 1);
                    tvPaidSelectFeature.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewSelectFeature.setAlpha((float) 0.5);
                        tvPaidSelectFeature.setText(getResources().getString(R.string.paid_feature));
                    }
                    String hairImageName = getResources().getString(R.string.hair);
                    hairImageName = hairImageName + hair.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(hairImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                        hairView.setImageResource(photoNameField.getInt(ourRID));
                        tvPoints.setText(String.valueOf(getmDbHandler().getPointsHair(hair)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
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
            final ImageView bagView = (ImageView) findViewById(R.id.bagView);
            final ImageView glassesView = (ImageView) findViewById(R.id.glassesView);
            final ImageView hatView = (ImageView) findViewById(R.id.hatView);
            final ImageView necklaceView = (ImageView) findViewById(R.id.necklaceView);
            final ImageView imageViewhandbag = (ImageView) findViewById(R.id.imageViewhandbag);
            final ImageView imageViewGlasses = (ImageView) findViewById(R.id.imageViewGlasses);
            final ImageView imageViewHat = (ImageView) findViewById(R.id.imageViewHat);
            final ImageView imageViewNecklace = (ImageView) findViewById(R.id.imageViewNecklace);
            final TextView tvHandbagPoints = (TextView) findViewById(R.id.tvHandbagPoints);
            final TextView tvGlassesPoints = (TextView) findViewById(R.id.tvGlassesPoints);
            final TextView tvHatPoints = (TextView) findViewById(R.id.tvHatPoints);
            final TextView tvNecklacePoints = (TextView) findViewById(R.id.tvNecklacePoints);
            tvHandbagPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory)));
            tvGlassesPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory + SessionHistory.bagTotalNo)));
            tvHatPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory
                    + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo)));
            tvNecklacePoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(accessory
                    + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo)));
            leftHandbag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bag = (bag - 1) % SessionHistory.bagTotalNo;
                    if (bag == 0) {
                        bag = SessionHistory.bagTotalNo;
                    }
                    bagPurchased = bag;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(bagPurchased);
                    imageViewhandbag.setAlpha((float) 1);
                    tvPaidHandbag.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewhandbag.setAlpha((float) 0.5);
                        tvPaidHandbag.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.bag);
                    eyeImageName = eyeImageName + bag.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        bagView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewhandbag.setImageResource(photoNameField.getInt(ourRID));
                        tvHandbagPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(bag)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            rightHandbag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bag = (bag + SessionHistory.bagTotalNo)
                            % SessionHistory.bagTotalNo + 1;
                    bagPurchased = bag;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(bagPurchased);
                    imageViewhandbag.setAlpha((float) 1);
                    tvPaidHandbag.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewhandbag.setAlpha((float) 0.5);
                        tvPaidHandbag.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.bag);
                    eyeImageName = eyeImageName + bag.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        bagView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewhandbag.setImageResource(photoNameField.getInt(ourRID));
                        tvHandbagPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(bag)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            leftGlasses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    glasses = (glasses - 1) % SessionHistory.bagTotalNo;
                    if (glasses == 0) {
                        glasses = SessionHistory.glassesTotalNo;
                    }
                    glassesPurchased = glasses + SessionHistory.bagTotalNo;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(glassesPurchased);
                    imageViewGlasses.setAlpha((float) 1);
                    tvPaidGlasses.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewGlasses.setAlpha((float) 0.5);
                        tvPaidGlasses.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.glasses);
                    eyeImageName = eyeImageName + glasses.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        glassesView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewGlasses.setImageResource(photoNameField.getInt(ourRID));
                        tvGlassesPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(glasses + SessionHistory.bagTotalNo)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            rightGlasses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    glasses = (glasses + SessionHistory.glassesTotalNo)
                            % SessionHistory.glassesTotalNo + 1;
                    glassesPurchased = glasses + SessionHistory.bagTotalNo;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(glassesPurchased);
                    imageViewGlasses.setAlpha((float) 1);
                    tvPaidGlasses.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewGlasses.setAlpha((float) 0.5);
                        tvPaidGlasses.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.glasses);
                    eyeImageName = eyeImageName + glasses.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        glassesView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewGlasses.setImageResource(photoNameField.getInt(ourRID));
                        tvGlassesPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(glasses + SessionHistory.bagTotalNo)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            leftHat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hat = (hat - 1) % SessionHistory.hatTotalNo;
                    if (hat == 0) {
                        hat = SessionHistory.hatTotalNo;
                    }
                    hatPurchased = hat + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(hatPurchased);
                    imageViewHat.setAlpha((float) 1);
                    tvPaidHat.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewHat.setAlpha((float) 0.5);
                        tvPaidHat.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.hat);
                    eyeImageName = eyeImageName + hat.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        hatView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewHat.setImageResource(photoNameField.getInt(ourRID));
                        tvHatPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(hat
                                + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            rightHat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hat = (hat + SessionHistory.hatTotalNo)
                            % SessionHistory.hatTotalNo + 1;
                    hatPurchased = hat + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(hatPurchased);
                    imageViewHat.setAlpha((float) 1);
                    tvPaidHat.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewHat.setAlpha((float) 0.5);
                        tvPaidHat.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.hat);
                    eyeImageName = eyeImageName + hat.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        hatView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewHat.setImageResource(photoNameField.getInt(ourRID));
                        tvHatPoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(hat
                                + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            leftNecklace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    necklace = (necklace - 1) % SessionHistory.necklaceTotalNo;
                    if (necklace == 0) {
                        necklace = SessionHistory.necklaceTotalNo;
                    }
                    necklacePurchased = necklace
                            + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(necklacePurchased);
                    imageViewNecklace.setAlpha((float) 1);
                    tvPaidNecklace.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewNecklace.setAlpha((float) 0.5);
                        tvPaidNecklace.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.necklace);
                    eyeImageName = eyeImageName + necklace.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        necklaceView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewNecklace.setImageResource(photoNameField.getInt(ourRID));
                        tvNecklacePoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(necklace
                                + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });

            rightNecklace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    necklace = (necklace + SessionHistory.necklaceTotalNo)
                            % SessionHistory.necklaceTotalNo + 1;
                    necklacePurchased = necklace
                            + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo;
                    Integer isPurchased = mDbHandler.getPurchasedAccessories(necklacePurchased);
                    imageViewNecklace.setAlpha((float) 1);
                    tvPaidNecklace.setText(getResources().getString(R.string.empty));
                    if (isPurchased == 1) {
                        imageViewNecklace.setAlpha((float) 0.5);
                        tvPaidNecklace.setText(getResources().getString(R.string.paid_feature_small));
                    }
                    String eyeImageName = getResources().getString(R.string.necklace);
                    eyeImageName = eyeImageName + necklace.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(eyeImageName);
                        necklaceView.setImageResource(photoNameField.getInt(ourRID));
                        imageViewNecklace.setImageResource(photoNameField.getInt(ourRID));
                        tvNecklacePoints.setText(String.valueOf(getmDbHandler().getPointsAccessories(necklace
                                + SessionHistory.bagTotalNo + SessionHistory.glassesTotalNo + SessionHistory.hatTotalNo)));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getmDbHandler().open();
                if (value.equalsIgnoreCase(getResources().getString(R.string.cloth))) {
                    if(SessionHistory.totalPoints < getmDbHandler().getPointsClothes(cloth))
                        Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                    else{
                        getmDbHandler().setAvatarCloth(cloth);
                        getmDbHandler().setPurchasedClothes(cloth);
                        SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsClothes(cloth);
                    }
                } else if (value.equalsIgnoreCase(getResources().getString(R.string.hair))) {
                    if(SessionHistory.totalPoints < getmDbHandler().getPointsHair(hair))
                        Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                    else{
                        getmDbHandler().setAvatarHair(hair);
                        getmDbHandler().setPurchasedHair(hair);
                        SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsClothes(cloth);
                    }
                } else if (value.equalsIgnoreCase(getResources().getString(R.string.accessory))) {
                    if (hatPurchased != 0){
                        if(SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(hatPurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else{
                            getmDbHandler().setPurchasedAccessories(hatPurchased);
                            getmDbHandler().setAvatarHat(hat);
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(hatPurchased);
                        }
                    }
                    if (glassesPurchased != 0){
                        if(SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(glassesPurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else{
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(hatPurchased);
                            getmDbHandler().setPurchasedAccessories(glassesPurchased);
                            getmDbHandler().setAvatarGlasses(glasses);
                        }
                    }
                    if (bagPurchased != 0){
                        if(SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(bagPurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else{
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(hatPurchased);
                            getmDbHandler().setPurchasedAccessories(bagPurchased);
                            getmDbHandler().setAvatarBag(bag);
                        }
                    }
                    if (necklacePurchased != 0){
                        if(SessionHistory.totalPoints < getmDbHandler().getPointsAccessories(necklacePurchased))
                            Toast.makeText(SelectFeaturesActivity.this, R.string.points_check, Toast.LENGTH_SHORT).show();
                        else{
                            SessionHistory.totalPoints = SessionHistory.totalPoints - getmDbHandler().getPointsAccessories(hatPurchased);
                            getmDbHandler().setPurchasedAccessories(necklacePurchased);
                            getmDbHandler().setAvatarNecklace(necklace);
                        }
                    }
                }
                Intent myIntent = new Intent(SelectFeaturesActivity.this, AvatarActivity.class);
                myIntent.putExtra(getResources().getString(R.string.feature), 2);
                startActivity(myIntent);
                getmDbHandler().close();
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
