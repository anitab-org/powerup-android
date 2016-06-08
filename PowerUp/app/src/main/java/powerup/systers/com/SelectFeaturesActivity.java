package powerup.systers.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import powerup.systers.com.datamodel.SessionHistory;

public class SelectFeaturesActivity extends AppCompatActivity {

    private Integer hair = 1;
    private Integer accessory = 1;
    private Integer cloth = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_features);
        final String value = getIntent().getExtras().getString("feature");
        TextView tv = (TextView)findViewById(R.id.textViewSelectFeature);
        tv.setText("Choose your " + value);
        ImageButton left = (ImageButton)findViewById(R.id.leftSelectFeature);
        ImageButton right = (ImageButton) findViewById(R.id.rightSelectFeature);
        final ImageView imageViewSelectFeature = (ImageView) findViewById(R.id.imageViewSelectFeature);
        if(value.equalsIgnoreCase("cloth"))
            imageViewSelectFeature.setImageDrawable(getResources().getDrawable(R.drawable.cloth1));
        else if(value.equalsIgnoreCase("accessory"))
            imageViewSelectFeature.setImageDrawable(getResources().getDrawable(R.drawable.accessory1));

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value.equalsIgnoreCase("cloth")){
                    cloth = (cloth - 1) % SessionHistory.clothTotalNo;
                    if (cloth == 0) {
                        cloth = SessionHistory.clothTotalNo;
                    }

                    String clothImageName = "cloth";
                    clothImageName = clothImageName + cloth.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(clothImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else if(value.equalsIgnoreCase("hair")){
                    hair = (hair - 1) % SessionHistory.hairTotalNo;
                    if (hair == 0) {
                        hair = SessionHistory.hairTotalNo;
                    }

                    String hairImageName = "hair";
                    hairImageName = hairImageName + hair.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(hairImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else if(value.equalsIgnoreCase("accessory")){
                    accessory = (accessory - 1) % SessionHistory.accessoryTotalNo;
                    if (accessory == 0) {
                        accessory = SessionHistory.hairTotalNo;
                    }

                    String accessoryImageName = "accessory";
                    accessoryImageName = accessoryImageName + accessory.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(accessoryImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(value.equalsIgnoreCase("cloth")){
                    cloth = (cloth + SessionHistory.clothTotalNo)
                            % SessionHistory.clothTotalNo + 1;
                    String clothImageName = "cloth";
                    clothImageName = clothImageName + cloth.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(clothImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else if(value.equalsIgnoreCase("hair")){
                    hair = (hair + SessionHistory.hairTotalNo)
                            % SessionHistory.hairTotalNo + 1;
                    String hairImageName = "hair";
                    hairImageName = hairImageName + hair.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(hairImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else if(value.equalsIgnoreCase("accessory")){
                    accessory = (accessory + SessionHistory.accessoryTotalNo)
                            % SessionHistory.accessoryTotalNo + 1;
                    String accessoryImageName = "accessory";
                    accessoryImageName = accessoryImageName + accessory.toString();
                    R.drawable ourRID = new R.drawable();
                    java.lang.reflect.Field photoNameField;
                    try {
                        photoNameField = ourRID.getClass().getField(accessoryImageName);
                        imageViewSelectFeature.setImageResource(photoNameField.getInt(ourRID));
                    } catch (NoSuchFieldException | IllegalAccessException
                            | IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
