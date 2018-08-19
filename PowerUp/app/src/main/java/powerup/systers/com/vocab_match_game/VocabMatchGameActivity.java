package powerup.systers.com.vocab_match_game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.Random;

import powerup.systers.com.ui.map_screen.MapActivity;
import powerup.systers.com.R;
import powerup.systers.com.data.SessionHistory;
import powerup.systers.com.utils.PowerUpUtils;


public class VocabMatchGameActivity extends AppCompatActivity {

    public VocabBoardTextView tv1, tv2, tv3;
    public VocabTileImageView img1, img2, img3;
    public int height, width, oldestTile, score, latestTile;
    public TextView scoreView;
    public MediaPlayer mediaPlayerPlus;
    public MediaPlayer mediaPlayerNegative;
    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_match_game);
        scoreView = (TextView) findViewById(R.id.vocab_score);
        tv1 = (VocabBoardTextView) findViewById(R.id.tv1);
        tv2 = (VocabBoardTextView) findViewById(R.id.tv2);
        tv3 = (VocabBoardTextView) findViewById(R.id.tv3);
        tv1.setOnDragListener(listenDrag);
        tv2.setOnDragListener(listenDrag);
        tv3.setOnDragListener(listenDrag);
        tv1.setOnTouchListener(new TouchListener());
        tv2.setOnTouchListener(new TouchListener());
        tv3.setOnTouchListener(new TouchListener());
        img1 = (VocabTileImageView) findViewById(R.id.img1);
        img3 = (VocabTileImageView) findViewById(R.id.img3);
        img2 = (VocabTileImageView) findViewById(R.id.img2);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        img1.getLayoutParams().width = height / 4;
        img1.getLayoutParams().height = height / 4;
        img2.getLayoutParams().width = height / 4;
        img2.getLayoutParams().height = height / 4;
        img3.getLayoutParams().width = height / 4;
        img3.getLayoutParams().height = height / 4;
        initialSetUp();
        mediaPlayerPlus = MediaPlayer.create(this, R.raw.plus_power_up);
        mediaPlayerNegative = MediaPlayer.create(this, R.raw.negative_hurt);
    }

    public void initialSetUp() {
        boolean calledByTutorialsActivity = getIntent().
                getBooleanExtra(PowerUpUtils.CALLED_BY, false);
        if(!calledByTutorialsActivity) {
            VocabMatchSessionManager session = new VocabMatchSessionManager(this);
            oldestTile = session.getCurrTile();
            latestTile = session.getCurrTile();
            score = session.getCurrScore();
            scoreView.setText("" + score);
            //makes sure no array out of bond, if no board left loop to the beginning
            //this is cleaner than trying to figure out what the clipboard contained before
            tv1.setText(PowerUpUtils.
                    VOCAB_MATCHES_BOARDS_TEXTS[oldestTile % PowerUpUtils.VOCAB_TILES_IMAGES.length]);
            tv2.setText(PowerUpUtils.
                    VOCAB_MATCHES_BOARDS_TEXTS[(oldestTile + 1) % PowerUpUtils.VOCAB_TILES_IMAGES.length]);
            tv3.setText(PowerUpUtils.
                    VOCAB_MATCHES_BOARDS_TEXTS[(oldestTile + 2) % PowerUpUtils.VOCAB_TILES_IMAGES.length]);
        } else {
            tv1.setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[0]);
            tv2.setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[1]);
            tv3.setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[2]);
            latestTile = 0;
            oldestTile = 0;
            score=0;
        }
        tv1.setPosition(0);
        tv2.setPosition(1);
        tv3.setPosition(2);
        r = new Random();
        startNewTile(Math.abs(r.nextInt() % 3), img1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                latestTile++;
                startNewTile(Math.abs(r.nextInt() % 3), img2);
            }
        }, 2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                latestTile++;
                startNewTile(Math.abs(r.nextInt() % 3), img3);
            }
        }, 4000);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startNewTile(final int position, final VocabTileImageView imageview) {

        if (latestTile < PowerUpUtils.VOCAB_TILES_IMAGES.length) {
            imageview.setImageDrawable(getResources().getDrawable(PowerUpUtils.VOCAB_TILES_IMAGES[latestTile]));
        }
        imageview.setX(0);
        imageview.setPosition(position);
        imageview.setY(position * (height / 3));
        imageview.setVisibility(View.VISIBLE);
        imageview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        ObjectAnimator animation = ObjectAnimator.ofFloat(imageview, "translationX", width * 0.63f);
        animation.setDuration(6000);
        animation.setInterpolator(new LinearInterpolator());
        animation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                imageview.setLayerType(View.LAYER_TYPE_NONE, null);
                imageview.setVisibility(View.GONE);
                final TextView boardView = getBoardFromPosition(imageview.getPosition());
                String boardText = getBoardFromPosition(imageview.getPosition()).getText().toString();

                if (oldestTile < PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS.length) {
                    String tileText = PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[oldestTile];
                    if (tileText.equals(boardText)) {
                        score++;
                        scoreView.setText("" + score);
                        mediaPlayerPlus.start();
                        boardView.setBackground(getResources().getDrawable(R.drawable.vocab_clipboard_green));
                    }else {
                        mediaPlayerNegative.start();
                        boardView.setBackground(getResources().getDrawable(R.drawable.vocab_clipboard_red));
                    }
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        boardView.setBackground(getResources().getDrawable(R.drawable.vocab_clipboard_yellow));
                    }
                },250);
                latestTile++;


                if (latestTile < PowerUpUtils.VOCAB_TILES_IMAGES.length) {
                    getPositionFromText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[oldestTile]).setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[latestTile]);
                }
                oldestTile++;
                if (latestTile < PowerUpUtils.VOCAB_TILES_IMAGES.length) {
                    startNewTile(Math.abs(r.nextInt() % 3), imageview);
                } else if (latestTile == PowerUpUtils.VOCAB_TILES_IMAGES.length + 2){
                    Intent intent = new Intent(VocabMatchGameActivity.this,VocabMatchEndActivity.class);
                    intent.putExtra(PowerUpUtils.SCORE,score);
                    SessionHistory.totalPoints += score;
                    SessionHistory.currScenePoints += score;
                    finish();
                    startActivity(intent);
                    overridePendingTransition(R.animator.fade_in_custom, R.animator.fade_out_custom);
                }

            }
        });
        animation.start();
    }

    public TextView getBoardFromPosition(int position) {
        if (tv1.getPosition() == position)
            return tv1;
        else if (tv2.getPosition() == position)
            return tv2;
        else
            return tv3;
    }

    public VocabBoardTextView getPositionFromText(String text) {
        if (tv1.getText().equals(text))
            return tv1;
        else if (tv2.getText().equals(text))
            return tv2;
        else
            return tv3;
    }

    View.OnDragListener listenDrag = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            final View view = (View) event.getLocalState();

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_STARTED:
                    view.setVisibility(View.INVISIBLE);
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
                    break;

                case DragEvent.ACTION_DROP:
                    VocabBoardTextView source = (VocabBoardTextView) view;
                    VocabBoardTextView target = (VocabBoardTextView) v;
                    float sourceX = source.getY();
                    int sourcePosition = source.getPosition();
                    source.setY(target.getY());
                    source.setPosition(target.getPosition());
                    target.setY(sourceX);
                    target.setPosition(sourcePosition);
                    break;
            }
            return true;
        }
    };

    private class TouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onPause() {
        VocabMatchSessionManager session = new VocabMatchSessionManager(this);
        session.saveData(score, oldestTile);
        super.onPause();
    }

    /**
     * Goes back to the map when user presses back button
     */
    @Override
    public void onBackPressed(){
        // The flag FLAG_ACTIVITY_CLEAR_TOP checks if an instance of the activity is present and it
        // clears the activities that were created after the found instance of the required activity
        startActivity(new Intent(VocabMatchGameActivity.this, MapActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
