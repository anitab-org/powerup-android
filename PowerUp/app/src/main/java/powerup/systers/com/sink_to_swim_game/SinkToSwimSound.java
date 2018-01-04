package powerup.systers.com.sink_to_swim_game;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;

import powerup.systers.com.R;

/**
 * Created by fidel on 1/4/2018.
 */

public class SinkToSwimSound extends Service {
    MediaPlayer mediaPlayer;
    final String SOUND_TYPE = "SOUND_TYPE";
    final static int BGM = 0;
    private SharedPreferences  prefs;
    final String CURR_POSITION = "CURR_POSITION";

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        //Prevent too many if statements in the future
        int soundType = intent.getExtras().getInt(SOUND_TYPE);
        switch (soundType) {
            case BGM:
                mediaPlayer = MediaPlayer.create(this, R.raw.sink_to_swim_bgm);
                break;
        }
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int curr = prefs.getInt(CURR_POSITION,0);
        mediaPlayer.seekTo(curr);
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        int curr = mediaPlayer.getCurrentPosition();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putInt(CURR_POSITION, curr);
        edit.apply();
        mediaPlayer.stop();
        mediaPlayer.release();
        stopSelf();
        super.onDestroy();
    }
}
