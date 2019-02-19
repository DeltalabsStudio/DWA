package id.delta.whatsapp.ui.accent;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.whatsapp.WaImageView;

import id.delta.whatsapp.value.Colors;


/**
 * Created by DELTALABS on 6/18/2017.
 */

public class FrameBackground extends FrameLayout {

    public FrameBackground(Context context) {
        super(context);
        init();
    }

    public FrameBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FrameBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        try{
            Drawable mDrawable = this.getBackground();
            mDrawable.setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_IN);
        }catch (Exception e){}
    }

}
