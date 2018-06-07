package powerup.systers.com.pregame_setup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.datamodel.SessionHistory;
import powerup.systers.com.powerup.PowerUpUtils;

import static powerup.systers.com.powerup.PowerUpUtils.MAX_ELEMENTS_PER_SCREEN;

public class PregameSetupActivity extends AppCompatActivity {

    @BindView(R.id.img_npc)
    public ImageView imageViewNpc;
    @BindView(R.id.pregame_grid_view)
    public GridView gridView;
    @BindView(R.id.txt_npc)
    public TextView npcText;

    //Lists for views in grid
    private List<Integer> npcSister = new ArrayList<>();
    private List<Integer> npcFriend = new ArrayList<>();
    private List<Integer> npcDoctor = new ArrayList<>();
    private List<Integer> npcTeacher = new ArrayList<>();
    //Lists for full views
    private List<Integer> npcSisterImages = new ArrayList<>();
    private List<Integer> npcFriendImages = new ArrayList<>();
    private List<Integer> npcDoctorImages = new ArrayList<>();
    private List<Integer> npcTeacherImages = new ArrayList<>();
    private List<List<Integer>> npcViewList = new ArrayList<>();
    private List<List<Integer>> npcFullViewList = new ArrayList<>();
    private GridAdapter gridViewAdapter;
    private int screenWidth, screenHeight, currentPage = 0, clickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_setup);
        ButterKnife.bind(this);

        //creating array lists for image views
        createMainDataLists();
        createDataLists();

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
                int previousClickedPosition = clickedPosition;
                clickedPosition = i + currentPage * MAX_ELEMENTS_PER_SCREEN;
                imageViewNpc.setImageResource(npcFullViewList.get(SessionHistory.npcType).get(clickedPosition));
                gridView.getChildAt(clickedPosition).findViewById(R.id.img_tick).setAlpha(1);
                if (previousClickedPosition != clickedPosition) {
                    gridView.getChildAt(previousClickedPosition).findViewById(R.id.img_tick).setAlpha(0);
                }
            }
        });
    }

    @OnClick(R.id.btn_back)
    public void clickBackButton() {
        saveChosenNpc(SessionHistory.npcType);
        startActivity(new Intent(PregameSetupActivity.this, PreGameSetupInitialActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.pregame_left_arrow)
    public void clickLeftArrow() {
        if (currentPage - 1 >= 0) {
            currentPage--;
            if (npcViewList.get(SessionHistory.npcType).size() >= currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN) {
                gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN));
            } else {
                gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, npcViewList.get(SessionHistory.npcType).size()));
            }
        }
    }

    @OnClick(R.id.pregame_right_arrow)
    public void clickRightArrow() {
        if ((currentPage + 1) * MAX_ELEMENTS_PER_SCREEN < npcViewList.get(SessionHistory.npcType).size()) {
            currentPage++;
            if (npcViewList.get(SessionHistory.npcType).size() >= currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN) {
                gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, currentPage * MAX_ELEMENTS_PER_SCREEN + MAX_ELEMENTS_PER_SCREEN));
            } else {
                gridViewAdapter.refresh(npcViewList.get(SessionHistory.npcType).subList(currentPage * MAX_ELEMENTS_PER_SCREEN, npcViewList.get(SessionHistory.npcType).size()));
            }
        }
    }

    @Override
    public void onBackPressed() {
        saveChosenNpc(SessionHistory.npcType);
        startActivity(new Intent(PregameSetupActivity.this, PreGameSetupInitialActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        updateClickedPosition(SessionHistory.npcType);
        super.onStart();
    }

    public void createMainDataLists() {
        //Lists for image view in grid adapter
        npcViewList.add(npcSister);
        npcViewList.add(npcFriend);
        npcViewList.add(npcDoctor);
        npcViewList.add(npcTeacher);
        //Lists for full image views of selected NPCs
        npcFullViewList.add(npcSisterImages);
        npcFullViewList.add(npcFriendImages);
        npcFullViewList.add(npcDoctorImages);
        npcFullViewList.add(npcTeacherImages);
    }

    public void createDataLists() {
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
    }

    public void saveChosenNpc(int type) {
        switch (type) {
            case PowerUpUtils.SISTER_TYPE:
                SessionHistory.npcHome = clickedPosition;
                Log.v("Saved Value", "value = " + SessionHistory.npcHome);
                break;
            case PowerUpUtils.FRIEND_TYPE:
                SessionHistory.npcSchool = clickedPosition;
                break;
            case PowerUpUtils.DOCTOR_TYPE:
                SessionHistory.npcHospital = clickedPosition;
                break;
            case PowerUpUtils.TEACHER_TYPE:
                SessionHistory.npcLibrary = clickedPosition;
                break;
            default:
                SessionHistory.npcHome = clickedPosition;
                break;
        }
    }

    public void updateClickedPosition(int type) {
        switch (type) {
            case PowerUpUtils.SISTER_TYPE:
                clickedPosition = SessionHistory.npcHome;
                break;
            case PowerUpUtils.FRIEND_TYPE:
                clickedPosition = SessionHistory.npcSchool;
                break;
            case PowerUpUtils.DOCTOR_TYPE:
                clickedPosition = SessionHistory.npcHospital;
                break;
            case PowerUpUtils.TEACHER_TYPE:
                clickedPosition = SessionHistory.npcLibrary;
                break;
            default:
                clickedPosition = SessionHistory.npcLibrary;
                break;
        }
        imageViewNpc.setImageResource(npcFullViewList.get(SessionHistory.npcType).get(clickedPosition));
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

        private void refresh(List<Integer> updatedList) {
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
            if (view == null) {
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
            if (i == clickedPosition)
                view.findViewById(R.id.img_tick).setAlpha(1);
            return view;
        }
    }
}
