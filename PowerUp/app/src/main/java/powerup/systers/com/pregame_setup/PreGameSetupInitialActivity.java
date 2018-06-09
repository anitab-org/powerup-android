package powerup.systers.com.pregame_setup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import powerup.systers.com.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.StartActivity;
import powerup.systers.com.datamodel.SessionHistory;

public class PreGameSetupInitialActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregame_setup_initial_screen);
        ButterKnife.bind(this);
    }

    @OnItemSelected(R.id.pregame_spinner_1)
    public void clickSpinner1(int i) {
        String a[] = getResources().getStringArray(R.array.pregame_array_1);
        saveMember(a[i], 0);
    }

    @OnItemSelected(R.id.pregame_spinner_2)
    public void clickSpinner2(int i) {
        String a[] = getResources().getStringArray(R.array.pregame_array_2);
        saveMember(a[i], 1);
    }

    @OnItemSelected(R.id.pregame_spinner_3)
    public void clickSpinner3(int i) {
        String a[] = getResources().getStringArray(R.array.pregame_array_3);
        saveMember(a[i], 2);
    }

    @OnItemSelected(R.id.pregame_spinner_4)
    public void clickSpinner4(int i) {
        String a[] = getResources().getStringArray(R.array.pregame_array_4);
        saveMember(a[i], 3);
    }

    @OnClick(R.id.btn_continue)
    public void click() {
        startActivityForResult(new Intent(PreGameSetupInitialActivity.this, MapActivity.class), 0);
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
    }

    private void saveMember(String member, int spinner) {
        SessionHistory.familyMember[spinner] = member;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PreGameSetupInitialActivity.this, StartActivity.class));
        overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        Log.v("Saved Values ", "\nValue 1  =  " + SessionHistory.familyMember[0]);
        Log.v("Saved Values ", "\nValue 2  =  " + SessionHistory.familyMember[1]);
        Log.v("Saved Values ", "\nValue 3  =  " + SessionHistory.familyMember[2]);
        Log.v("Saved Values ", "\nValue 4  =  " + SessionHistory.familyMember[3]);
        super.onStop();
    }
}
