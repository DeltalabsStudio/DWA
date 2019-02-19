package id.delta.whatsapp.ui.accent;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Conversation;


/**
 * Created by DELTALABS on 6/18/2017.
 */

public class RoundBackground extends FrameLayout {

    public RoundBackground(Context context) {
        super(context);
        init();
    }

    public RoundBackground(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundBackground(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        try{
            Drawable mDrawable = this.getBackground();
            Conversation.setInputBackground(mDrawable);
        }catch (Exception e){}
    }

}
