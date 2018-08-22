package powerup.systers.com.kill_the_virus_game;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import powerup.systers.com.R;

public class KillTheVirusSound extends Service {
    private MediaPlayer mediaPlayer;
    public final String SOUND_TYPE = "SOUND_TYPE";
    public final static int BGM = 0;

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        int soundType = intent.getExtras().getInt(SOUND_TYPE);
                if(soundType == BGM)
                mediaPlayer = MediaPlayer.create(this, R.raw.kill_virus_bgm);

        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        stopSelf();
        super.onDestroy();
    }
}
