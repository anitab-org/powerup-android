package powerup.systers.com.vocab_match_game;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.Random;

import powerup.systers.com.R;
import powerup.systers.com.powerup.PowerUpUtils;


public class VocabMatchGameActivity extends AppCompatActivity {

    VocabBoardTextView tv1, tv2, tv3;
    VocabTileImageView img1, img2, img3;
    int height, width, oldestTile, score, latestTile;
    TextView scoreView;
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
    }

    private void initialSetUp() {
        tv1.setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[0]);
        tv1.setPosition(0);
        tv2.setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[1]);
        tv2.setPosition(1);
        tv3.setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[2]);
        tv3.setPosition(2);
        latestTile = 0;
        oldestTile = 0;
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

    public void startNewTile(final int position, final VocabTileImageView imageview) {
        imageview.setImageDrawable(getResources().getDrawable(PowerUpUtils.VOCAB_TILES_IMAGES[latestTile]));
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
                if (PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[oldestTile].equals(getTextFromBoard(imageview.getPosition()))) {
                    score++;
                    scoreView.setText("" + score);
                }
                latestTile++;
                if (latestTile < PowerUpUtils.VOCAB_TILES_IMAGES.length)
                    getPositionFromText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[oldestTile]).setText(PowerUpUtils.VOCAB_MATCHES_BOARDS_TEXTS[latestTile]);
                oldestTile++;
                if (latestTile < PowerUpUtils.VOCAB_TILES_IMAGES.length)
                    startNewTile(Math.abs(r.nextInt() % 3), imageview);
            }
        });
        animation.start();
    }

    public String getTextFromBoard(int position) {
        if (tv1.getPosition() == position)
            return tv1.getText().toString();
        else if (tv2.getPosition() == position)
            return tv2.getText().toString();
        else
            return tv3.getText().toString();
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
                    Log.i("Drag Event", "Entered");
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
                    Log.i("Drag Event", "Exited");
                    break;

                case DragEvent.ACTION_DROP:
                    Log.e("sachin1", "sachin");
                    VocabBoardTextView source = (VocabBoardTextView) view;
                    VocabBoardTextView target = (VocabBoardTextView) v;
                    float sourceX = source.getY();
                    int sourcePosition = source.getPosition();
                    source.setY(target.getY());
                    source.setPosition(target.getPosition());
                    target.setY(sourceX);
                    target.setPosition(sourcePosition);
                    Log.e("sachin", source.getPosition() + "" + target.getPosition());
                    break;
            }
            return true;
        }
    };

    private class TouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent motionEvent) {
            Log.e("sachin", motionEvent.getAction() + " " + motionEvent.getActionMasked() + " " + motionEvent.getSource());
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
}
