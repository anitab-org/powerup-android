package powerup.systers.com.ui.pregame_setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.utils.PowerUpUtils;

public class PregameSetupActivity extends Activity {

    private static int screenWidth;
    private static int screenHeight;
    public @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    public @BindView(R.id.img_family_member)
    ImageView npcImageView;
    private int clickedPosition;
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int previousClickedPosition = clickedPosition;
            clickedPosition = mRecyclerView.getChildLayoutPosition(view);
            saveCharacter(SessionHistory.characterType,clickedPosition);
            mRecyclerView.findViewHolderForAdapterPosition(clickedPosition).itemView.findViewById(R.id.green_tick).setVisibility(View.VISIBLE);
            if (clickedPosition != previousClickedPosition && mRecyclerView.findViewHolderForAdapterPosition(previousClickedPosition)!= null)
                mRecyclerView.findViewHolderForAdapterPosition(previousClickedPosition).itemView.findViewById(R.id.green_tick).setVisibility(View.GONE);
            npcImageView.setImageResource(SessionHistory.npcFullViewList[clickedPosition]);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_pregame_setup);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getApplicationContext(), R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);

        int[] myDataset = SessionHistory.npcList;
        RecyclerView.Adapter mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        updateFullView(SessionHistory.characterType);
    }

    private static void saveCharacter(int character, int value) {
        switch (character) {
            case PowerUpUtils.NPC_ADULT_1:
                SessionHistory.selectedAdult1 = value;
                break;
            case PowerUpUtils.NPC_ADULT_2:
                SessionHistory.selectedAdult2 = value;
                break;
            case PowerUpUtils.NPC_CHILD_1:
                SessionHistory.selectedChild1 = value;
                break;
            case PowerUpUtils.NPC_CHILD_2:
                SessionHistory.selectedChild2 = value;
                break;
            default:
                SessionHistory.selectedAdult1 = 0;
                break;
        }
    }

    /**
     * Updates the position of green tick on start of the activity
     * @param holder - ViewHolder object
     * @param position - item number on grid view
     */
    private void updateViews(MyAdapter.ViewHolder holder, int position){
        int temp = 0;
        switch (SessionHistory.characterType){
            case PowerUpUtils.NPC_ADULT_1:
                temp = SessionHistory.selectedAdult1;
                break;
            case PowerUpUtils.NPC_ADULT_2:
                temp = SessionHistory.selectedAdult2;
                break;
            case PowerUpUtils.NPC_CHILD_1:
                temp = SessionHistory.selectedChild1;
                break;
            case PowerUpUtils.NPC_CHILD_2:
                temp = SessionHistory.selectedChild2;
                break;
            default:
                temp = SessionHistory.selectedAdult1;
                break;
        }
        if(position == temp){
            clickedPosition = temp;
            holder.imgTick.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgTick.setVisibility(View.GONE);
        }
    }

    /**
     * Update the full view of the NPC
     *
     * @param type - npc type
     */
    private void updateFullView(int type) {
        switch (type) {
            case PowerUpUtils.NPC_ADULT_1:
                npcImageView.setImageResource(PowerUpUtils.NPC_ADULT_IMAGES_1[SessionHistory.selectedAdult1]);
                break;
            case PowerUpUtils.NPC_ADULT_2:
                npcImageView.setImageResource(PowerUpUtils.NPC_ADULT_IMAGES_2[SessionHistory.selectedAdult2]);
                break;
            case PowerUpUtils.NPC_CHILD_1:
                npcImageView.setImageResource(PowerUpUtils.NPC_CHILD_IMAGES[SessionHistory.selectedChild1]);
                break;
            case PowerUpUtils.NPC_CHILD_2:
                npcImageView.setImageResource(PowerUpUtils.NPC_CHILD_IMAGES[SessionHistory.selectedChild2]);
                break;
            default:
                npcImageView.setImageResource(PowerUpUtils.NPC_ADULT_IMAGES_1[SessionHistory.selectedAdult1]);
                break;
        }
    }

    @OnClick(R.id.pregame_back_btn)
    public void clickBack() {
        saveCharacter(SessionHistory.characterType, clickedPosition);
        startActivityForResult(new Intent(PregameSetupActivity.this, PreGameSetupInitialActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @Override
    public void onBackPressed() {
        startActivityForResult(new Intent(PregameSetupActivity.this, PreGameSetupInitialActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private int[] mDataset;

        MyAdapter(int[] myDataset) {
            mDataset = myDataset;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //Create view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pregame_setup_list_item, parent, false);
            int itemWidth = (int) ((screenWidth / 85.428f) * 13);
            int itemHeight = (int) ((screenHeight / 51.428f) * 18);
            v.setLayoutParams(new AbsListView.LayoutParams(itemWidth, itemHeight));
            v.setOnClickListener(onClickListener);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
            //Replace the contents according to data item
            holder.npcView.setImageResource(mDataset[position]);
                       holder.imgTick.setImageResource(R.drawable.store_tick);
            //Setting green tick on appropriate data item
            updateViews(holder, position);
        }

        @Override
        public int getItemCount() {
            return mDataset.length;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView npcView;
            private ImageView imgTick;

            ViewHolder(View v) {
                super(v);
                npcView = v.findViewById(R.id.item_npc);
                imgTick = v.findViewById(R.id.green_tick);
            }
        }
    }

    private class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId) {
            this(context.getResources().getDimensionPixelSize(itemOffsetId));
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
}
