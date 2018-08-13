package powerup.systers.com.ui.pregame_setup;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.ui.StartActivity;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.utils.PowerUpUtils;

public class PreGameSetupInitialActivity extends Activity {

    @BindView(R.id.pregame_img_1)
    public ImageView npc1;
    @BindView(R.id.pregame_img_2)
    public ImageView npc2;
    @BindView(R.id.pregame_img_3)
    public ImageView npc3;
    @BindView(R.id.pregame_img_4)
    public ImageView npc4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_setup_initial_screen);
        ButterKnife.bind(this);
        updateNpcView();
    }

    @OnClick(R.id.btn_continue)
    public void click() {
        if (SessionHistory.characterChosen) {
            startActivityForResult(new Intent(PreGameSetupInitialActivity.this, MapActivity.class), 0);
            overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(PreGameSetupInitialActivity.this);
            builder.setTitle(getResources().getString(R.string.choose_member))
                    .setMessage(getApplicationContext().getResources().getString(R.string.pregame_warning_message));
            builder.setPositiveButton(getString(R.string.start_confirm_message_load), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            ColorDrawable drawable = new ColorDrawable(Color.WHITE);
            drawable.setAlpha(200);
            dialog.getWindow().setBackgroundDrawable(drawable);
            dialog.show();
        }
    }

    @OnClick(R.id.pregame_img_1)
    public void clickNpc1() {
        SessionHistory.npcList = PowerUpUtils.ADULT_IMAGES_1;
        SessionHistory.npcFullViewList = PowerUpUtils.NPC_ADULT_IMAGES_1;
        SessionHistory.characterType = PowerUpUtils.NPC_ADULT_1;
        SessionHistory.adult1Chosen = true;
        SessionHistory.characterChosen = true;
        SessionHistory.selectedValue = SessionHistory.selectedAdult1;
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.pregame_img_2)
    public void clickNpc2() {
        SessionHistory.npcList = PowerUpUtils.ADULT_IMAGES_2;
        SessionHistory.npcFullViewList = PowerUpUtils.NPC_ADULT_IMAGES_2;
        SessionHistory.characterType = PowerUpUtils.NPC_ADULT_2;
        SessionHistory.adult2Chosen = true;
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.pregame_img_3)
    public void clickNpc3() {
        SessionHistory.npcList = PowerUpUtils.CHILD_IMAGES;
        SessionHistory.npcFullViewList = PowerUpUtils.NPC_CHILD_IMAGES;
        SessionHistory.characterType = PowerUpUtils.NPC_CHILD_1;
        SessionHistory.child1Chosen = true;
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    @OnClick(R.id.pregame_img_4)
    public void clickNpc4() {
        SessionHistory.npcList = PowerUpUtils.CHILD_IMAGES;
        SessionHistory.npcFullViewList = PowerUpUtils.NPC_CHILD_IMAGES;
        SessionHistory.characterType = PowerUpUtils.NPC_CHILD_2;
        SessionHistory.child2Chosen = true;
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, PregameSetupActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    private void updateNpcView() {
        if (SessionHistory.adult1Chosen)
            npc1.setImageResource(PowerUpUtils.ADULT_IMAGES_1[SessionHistory.selectedAdult1]);
        if (SessionHistory.adult2Chosen && SessionHistory.selectedAdult2 != 0)
            npc2.setImageResource(PowerUpUtils.ADULT_IMAGES_2[SessionHistory.selectedAdult2]);
        if (SessionHistory.child1Chosen && SessionHistory.selectedChild1 != 0)
            npc3.setImageResource(PowerUpUtils.CHILD_IMAGES[SessionHistory.selectedChild1]);
        if (SessionHistory.child2Chosen && SessionHistory.selectedChild2 != 0)
            npc4.setImageResource(PowerUpUtils.CHILD_IMAGES[SessionHistory.selectedChild2]);
    }

    @Override
    public void onBackPressed() {
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, StartActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);    }
}
