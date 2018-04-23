package powerup.systers.com.vocab_match_game;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by sachinaggarwal on 24/07/17.
 */

public class VocabTileImageView extends android.support.v7.widget.AppCompatImageView {

    private int position;

    public VocabTileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
