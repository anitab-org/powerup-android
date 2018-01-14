package powerup.systers.com;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.datamodel.StoreItem;
import powerup.systers.com.db.DatabaseHandler;
import powerup.systers.com.powerup.PowerUpUtils;


public class StoreActivity extends AppCompatActivity {

    GridView gridView;
    public int storeItemTypeindex = 0;
    public int currentPage = 0;
    int screenWidth, screenHeight;
    ImageView clothImageView, hairImageView, accessoryImageView;
    ImageView leftArrow, rightArrow, hairButton, clothesButton, accessoriesButton;
    List<List<StoreItem>> allDataSet;
    GridAdapter adapter;
    TextView karmaPoints;
    private DatabaseHandler mDbHandler;
    java.lang.reflect.Field photoNameField;
    R.drawable ourRID;
    long selectedItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setmDbHandler(new DatabaseHandler(this));
        getmDbHandler().open();
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        karmaPoints = (TextView) findViewById(R.id.karma_points);
        karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
        Button mapButton = (Button) findViewById(R.id.map_button);

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(StoreActivity.this,MapActivity.class));
            }
        });


        ImageView eyeImageView = (ImageView) findViewById(R.id.eye_view);
        ImageView skinImageView = (ImageView) findViewById(R.id.skin_view);
        hairImageView = (ImageView) findViewById(R.id.hair_view);
        clothImageView = (ImageView) findViewById(R.id.dress_view);
        accessoryImageView = (ImageView) findViewById(R.id.acc_view);

        String eyeImageName = getResources().getString(R.string.eye);
        eyeImageName = eyeImageName + getmDbHandler().getAvatarEye();
        ourRID = new R.drawable();

        try {
            photoNameField = ourRID.getClass().getField(eyeImageName);
            eyeImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        String skinImageName = getResources().getString(R.string.skin);
        skinImageName = skinImageName + getmDbHandler().getAvatarSkin();
        try {
            photoNameField = ourRID.getClass().getField(skinImageName);
            skinImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

        setAvatarClothes(getmDbHandler().getAvatarCloth());
        setAvatarHair(getmDbHandler().getAvatarHair());
        setAvatarAccessories(getmDbHandler().getAvatarAccessory());


        leftArrow = (ImageView) findViewById(R.id.left_arrow);
        rightArrow = (ImageView) findViewById(R.id.right_arrow);
        hairButton = (ImageView) findViewById(R.id.hair_button);
        clothesButton = (ImageView) findViewById(R.id.clothes_button);
        accessoriesButton = (ImageView) findViewById(R.id.accessories_button);

        hairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 0;
                storeItemTypeindex = 0;
                adapter.refresh(allDataSet.get(storeItemTypeindex).subList(0, 6));
            }
        });

        clothesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 0;
                storeItemTypeindex = 1;
                adapter.refresh(allDataSet.get(storeItemTypeindex).subList(0, PowerUpUtils.CLOTHES_IMAGES.length%6));
            }
        });

        accessoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage = 0;
                storeItemTypeindex = 2;
                adapter.refresh(allDataSet.get(storeItemTypeindex).subList(0, PowerUpUtils.ACCESSORIES_IMAGES.length%6));
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage == 0) {
                    return;
                }
                currentPage--;
                if (currentPage * 6 < allDataSet.get(storeItemTypeindex).size()) {
                    if (allDataSet.get(storeItemTypeindex).size() >= currentPage * 6 + 6) {
                        adapter.refresh(allDataSet.get(storeItemTypeindex).subList(currentPage * 6, currentPage * 6 + 6));
                    } else {
                        adapter.refresh(allDataSet.get(storeItemTypeindex).subList(currentPage * 6, allDataSet.get(storeItemTypeindex).size()));
                    }
                }
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPage++;
                if (currentPage * 6 < allDataSet.get(storeItemTypeindex).size()) {
                    if (allDataSet.get(storeItemTypeindex).size() >= currentPage * 6 + 6) {
                        adapter.refresh(allDataSet.get(storeItemTypeindex).subList(currentPage * 6, currentPage * 6 + 6));
                    } else {
                        adapter.refresh(allDataSet.get(storeItemTypeindex).subList(currentPage * 6, allDataSet.get(storeItemTypeindex).size()));
                    }
                } else {
                    currentPage--;
                }
            }
        });

        gridView = (GridView) findViewById(R.id.grid_view);
        createDataLists();
        adapter = new GridAdapter(this, allDataSet.get(0).subList(0, 6));
        gridView.setAdapter(adapter);
    }

    public void setAvatarHair(int index){
        getmDbHandler().setAvatarHair(index);
        String hairImageName = getResources().getString(R.string.hair);
        hairImageName = hairImageName + index;
        try {
            photoNameField = ourRID.getClass().getField(hairImageName);
            hairImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }

    }

    public void setAvatarClothes(int index){
        getmDbHandler().setAvatarCloth(index);
        String clothImageName = getResources().getString(R.string.cloth);
        clothImageName = clothImageName + index;
        try {
            photoNameField = ourRID.getClass().getField(clothImageName);
            clothImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }
    }

    public void setAvatarAccessories(int index){
        getmDbHandler().setAvatarAccessory(index);
        String accessoryImageName = getResources().getString(R.string.accessories);
        accessoryImageName = accessoryImageName + index;
        try {
            photoNameField = ourRID.getClass().getField(accessoryImageName);
            accessoryImageView.setImageResource(photoNameField.getInt(ourRID));
        } catch (NoSuchFieldException | IllegalAccessException
                | IllegalArgumentException error) {
            error.printStackTrace();
        }
    }

    public void createDataLists() {
        allDataSet = new ArrayList<>();

        List<StoreItem> storeHair = new ArrayList<>();
        List<StoreItem> storeClothes = new ArrayList<>();
        List<StoreItem> storeAccessories = new ArrayList<>();

        allDataSet.add(storeHair);
        allDataSet.add(storeClothes);
        allDataSet.add(storeAccessories);


        for (int i = 0; i < PowerUpUtils.HAIR_IMAGES.length; i++) {
            StoreItem item = new StoreItem(PowerUpUtils.HAIR_POINTS_TEXTS[i], PowerUpUtils.HAIR_IMAGES[i]);
            allDataSet.get(0).add(item);
        }
        for (int i = 0; i < PowerUpUtils.CLOTHES_IMAGES.length; i++) {
            StoreItem item = new StoreItem(PowerUpUtils.CLOTHES_POINTS_TEXTS[i], PowerUpUtils.CLOTHES_IMAGES[i]);
            allDataSet.get(1).add(item);
        }
        for (int i = 0; i < PowerUpUtils.ACCESSORIES_IMAGES.length; i++) {
            StoreItem item = new StoreItem(PowerUpUtils.ACCESSORIES_POINTS_TEXTS[i], PowerUpUtils.ACCESSORIES_IMAGES[i]);
            allDataSet.get(2).add(item);
        }
    }

    public int calculatePosition(int position) {
        int id = currentPage*6+position;
        return id;
    }

    class GridAdapter extends BaseAdapter {

        List<StoreItem> storeItems;
        Context context;

        GridAdapter(Context context, List<StoreItem> list) {
            this.context = context;
            this.storeItems = list;
        }

        public void refresh(List<StoreItem> updatedList) {
            this.storeItems = updatedList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return storeItems.size();
        }

        @Override
        public Object getItem(int position) {
            return storeItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            ImageView itemImage;
            TextView itemPoints;

            ViewHolder(View view) {
                itemImage = (ImageView) view.findViewById(R.id.item_image);
                itemPoints = (TextView) view.findViewById(R.id.item_points);
            }
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View storeItem = convertView;
            ViewHolder holder;
            if (storeItem == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                storeItem = layoutInflater.inflate(R.layout.store_selection_view, parent, false);
                int itemWidth = (int) ((screenWidth / 85.428f) * 13);
                int itemHeight = (int) ((screenHeight / 51.428f) * 18);
                storeItem.setLayoutParams(new AbsListView.LayoutParams(itemWidth, itemHeight));
                holder = new ViewHolder(storeItem);
                storeItem.setTag(holder);
                selectedItemId = getItemId(calculatePosition(position)+1); //Previously purchased
            } else {
                holder = (ViewHolder) storeItem.getTag();
            }
            storeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.isEnabled()){

                        TextView itemPoints = (TextView) v.findViewById(R.id.item_points);
                        int index = calculatePosition(position)+1;
                        if (storeItemTypeindex == 0) { //hair
                            setAvatarHair(index);
                            selectedItemId = getmDbHandler().getAvatarHair(); //hairItem selected
                            if (getmDbHandler().getPurchasedHair(index) == 0){
                                SessionHistory.totalPoints -= Integer.parseInt(itemPoints.getText().toString());
                                karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));

                                getmDbHandler().setPurchasedHair(index);
                            }

                        } else if (storeItemTypeindex == 1) { //clothes
                            setAvatarClothes(index);
                            selectedItemId = getmDbHandler().getAvatarCloth(); //clothItem selected
                            if (getmDbHandler().getPurchasedClothes(index) == 0){
                                SessionHistory.totalPoints -= Integer.parseInt(itemPoints.getText().toString());
                                karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
                                getmDbHandler().setPurchasedClothes(index);
                            }

                        } else if (storeItemTypeindex == 2) { //accessories
                            setAvatarAccessories(index);
                            selectedItemId = getmDbHandler().getAvatarAccessory(); //accessoryItem selected
                            if (getmDbHandler().getPurchasedAccessories(index) == 0){
                                SessionHistory.totalPoints -= Integer.parseInt(itemPoints.getText().toString());
                                karmaPoints.setText(String.valueOf(SessionHistory.totalPoints));
                                getmDbHandler().setPurchasedAccessories(index);
                            }
                        }
                        adapter.refresh(adapter.storeItems); // will update change the background if any is not available

                    }
                }
            });
            StoreItem temp = storeItems.get(position);
                holder.itemImage.setBackground(context.getResources().getDrawable(temp.imageId));
                holder.itemPoints.setText(temp.points);

            int id = calculatePosition(position)+1;

            if (getPurchasedStatus(id) == 1) { // whatever type is currently opened, it is already bought
                storeItem.setBackground(getResources().getDrawable(R.drawable.sold_item));
                storeItem.setEnabled(true);
                //Testing whether the item matches id (selected)
                if (selectedItemId == id) {
                    holder.itemImage.setImageResource(R.drawable.store_tick);
                } else {
                    holder.itemImage.setImageResource(android.R.color.transparent);
                }
            } else { //not purchased => available/not available
                holder.itemImage.setImageResource(Color.TRANSPARENT);
                if (Integer.parseInt(temp.points) <= SessionHistory.totalPoints) { //can be bought
                    storeItem.setBackground(getResources().getDrawable(R.drawable.buy_item));
                    storeItem.setEnabled(true);

                } else { //can't be bought
                    storeItem.setBackground(getResources().getDrawable(R.drawable.unavailable_item));
                    storeItem.setEnabled(false);
                }
            }
            return storeItem;
        }

    }

    public int getPurchasedStatus(int index) {
        if (storeItemTypeindex == 0) { //hair
            return getmDbHandler().getPurchasedHair(index);
        } else if (storeItemTypeindex == 1) { //clothes
            return getmDbHandler().getPurchasedClothes(index);
        } else if (storeItemTypeindex == 2) { //accessories
            return getmDbHandler().getPurchasedAccessories(index);
        }
        return 0;
    }

    public DatabaseHandler getmDbHandler() {
        return mDbHandler;
    }

    public void setmDbHandler(DatabaseHandler mDbHandler) {
        this.mDbHandler = mDbHandler;
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}


