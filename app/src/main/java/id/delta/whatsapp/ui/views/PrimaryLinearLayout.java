package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import id.delta.whatsapp.value.Colors;

/**
 * Created by DELTALABS on 6/18/2017.
 */

public class PrimaryLinearLayout extends LinearLayout {

    public PrimaryLinearLayout(Context context) {
        super(context);
        init();
    }

    public PrimaryLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PrimaryLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setBackgroundColor(Colors.setWarnaPrimer());
    }

}
