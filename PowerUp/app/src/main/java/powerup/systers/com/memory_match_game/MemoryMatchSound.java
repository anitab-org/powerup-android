package powerup.systers.com.memory_match_game;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import powerup.systers.com.R;
public class MemoryMatchSound extends Service {
    private MediaPlayer player;
    public static final String SOUND_TYPE_EXTRA = "SOUND_EXTRA";
    public static final int TYPE_CORRECT = R.raw.memory_match_correct;
    public static final int TYPE_INCORRECT = R.raw.memory_match_wrong;
    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int soundType = intent.getIntExtra(SOUND_TYPE_EXTRA,  TYPE_INCORRECT);
        player = MediaPlayer.create(this, soundType);
        player.start();
        return Service.START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }
}