package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Row;
import id.delta.whatsapp.value.Themes;

public class Card extends RelativeLayout {

    public Card(Context context) {
        super(context);
        init();
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Card(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        Drawable mDrawable = Tools.getDrawable("delta_card_row");
        if(Prefs.getBoolean(Keys.KEY_CARD_TRANSPARENT, true)){
            mDrawable = Tools.getDrawable("delta_card_row_trans");
        }
        mDrawable.setColorFilter(Row.cardColor(), PorterDuff.Mode.SRC_IN);
        this.setBackground(mDrawable);

    }

}

