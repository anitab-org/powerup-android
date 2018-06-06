package powerup.systers.com.pregame_setup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import powerup.systers.com.R;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.powerup.PowerUpUtils;

import static powerup.systers.com.powerup.PowerUpUtils.MAX_ELEMENTS_PER_SCREEN;

public class PregameSetupActivity extends AppCompatActivity {

    private GridView gridView;
    private List<List<Integer>> npcViewList = new ArrayList<>();
    private List<List<Integer>> npcFullViewList = new ArrayList<>();
    private GridAdapter gridViewAdapter;
    private ImageView imageViewNpc, backButton, leftArrow, rightArrow, storeTick;
    private TextView npcText;
    private int screenWidth, screenHeight, currentPage, previousClickedPosition = 0, clickedPosition = 0;
    private boolean firstClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_setup);

        gridView = findViewById(R.id.pregame_grid_view);
        backButton = findViewById(R.id.btn_back);
        npcText = findViewById(R.id.txt_npc);
        imageViewNpc = findViewById(R.id.img_npc);
        leftArrow = findViewById(R.id.pregame_left_arrow);
        rightArrow = findViewById(R.id.pregame_right_arrow);
        storeTick = findViewById(R.id.img_tick);


        //Lists for views in grid
        List<Integer> npcSister = new ArrayList<>();
        List<Integer> npcFriend = new ArrayList<>();
        List<Integer> npcDoctor = new ArrayList<>();
        List<Integer> npcTeacher = new ArrayList<>();

        //Lists for full views
        List<Integer> npcSisterImages = new ArrayList<>();
        List<Integer> npcFriendImages = new ArrayList<>();
        List<Integer> npcDoctorImages = new ArrayList<>();
        List<Integer> npcTeacherImages = new ArrayList<>();

        npcViewList.add(npcSister);
        npcViewList.add(npcFriend);
        npcViewList.add(npcDoctor);
        npcViewList.add(npcTeacher);
        currentPage = 0;

        npcFullViewList.add(npcSisterImages);
        npcFullViewList.add(npcFriendImages);
        npcFullViewList.add(npcDoctorImages);
        npcFullViewList.add(npcTeacherImages);

        for (int i = 0; i < PowerUpUtils.SISTER_IMAGES.length; i++) {
            npcSister.add(PowerUpUtils.SISTER_IMAGES[i]);
            npcSisterImages.add(PowerUpUtils.NPC_SISTER_IMAGES[i]);
        }

        for (int i = 0; i < PowerUpUtils.FRIEND_IMAGES.length; i++) {
            npcFriend.add(PowerUpUtils.FRIEND_IMAGES[i]);
            npcFriendImages.add(PowerUpUtils.NPC_FRIEND_IMAGES[i]);
        }

        for (int i = 0; i < PowerUpUtils.DOCTOR_IMAGES.length; i++) {
            npcDoctor.add(PowerUpUtils.DOCTOR_IMAGES[i]);
            npcDoctorImages.add(PowerUpUtils.NPC_DOCTOR_IMAGES[i]);
        }

        for (int i = 0; i < PowerUpUtils.TEACHER_IMAGES.length; i++) {
            npcTeacher.add(PowerUpUtils.TEACHER_IMAGES[i]);
            npcTeacherImages.add(PowerUpUtils.NPC_TEACHER_IMAGES[i]);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PregameSetupActivity.this, PreGameSetupInitialActivity.class));
                //TODO: Include the writing to database of user selected option
                overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage - 1 >= 0) {
                    currentPage--;
                    if (npcViewList.get(SessionHistory.npcType).size() >= currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN) {
                        gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN));
                    } else {
                        gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, npcViewList.get(SessionHistory.npcType).size()));
                    }
                }
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((currentPage + 1) * MAX_ELEMENTS_PER_SCREEN < npcViewList.get(SessionHistory.npcType).size()) {
                    currentPage++;
                    if (npcViewList.get(SessionHistory.npcType).size() >= currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN) {
                        gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN));
                    } else {
                        gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, npcViewList.get(SessionHistory.npcType).size()));
                    }
                }
            }
        });

        imageViewNpc.setImageResource(PowerUpUtils.NPC_IMAGE[SessionHistory.npcType]);
        npcText.setText(PowerUpUtils.NPC_TEXT[SessionHistory.npcType]);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        gridViewAdapter = new GridAdapter(this);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(0, Math.min(npcViewList.get(SessionHistory.npcType).size(), MAX_ELEMENTS_PER_SCREEN)));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                previousClickedPosition = clickedPosition;
                clickedPosition = i + currentPage * MAX_ELEMENTS_PER_SCREEN;
                imageViewNpc.setImageResource(npcFullViewList.get(SessionHistory.npcType).get(clickedPosition));
                gridView.getChildAt(clickedPosition).findViewById(R.id.img_tick).setAlpha(1);
                if (! firstClick){
                   gridView.getChildAt(previousClickedPosition).findViewById(R.id.img_tick).setAlpha(0);
                }
                firstClick = false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PregameSetupActivity.this, PreGameSetupInitialActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        super.onBackPressed();
    }

    class GridAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater layoutInflater;
        private List<Integer> selectedNpcList;

        GridAdapter(Context c) {
            context = c;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            selectedNpcList = npcViewList.get(SessionHistory.npcType);
        }

        public void refresh(List<Integer> updatedList) {
            selectedNpcList = updatedList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return selectedNpcList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ImageView img;
            if (view == null ) {
                view = layoutInflater.inflate(R.layout.pregame_setup_list_item, null);
                img = view.findViewById(R.id.item_npc);
                view.setTag(img);
            } else {
                img = (ImageView) view.getTag();
            }
            img.setImageResource(selectedNpcList.get(i));
            int itemWidth = (int) ((screenWidth / 85.428f) * 13);
            int itemHeight = (int) ((screenHeight / 51.428f) * 18);
            view.setLayoutParams(new AbsListView.LayoutParams(itemWidth, itemHeight));
            return view;
        }
    }
}
