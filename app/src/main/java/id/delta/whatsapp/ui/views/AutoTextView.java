package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.whatsapp.WaTextView;

import id.delta.whatsapp.value.Colors;


/**
 * Created by DELTALABS on 6/18/2017.
 */

public class AutoTextView extends WaTextView {

    public AutoTextView(Context context) {
        super(context);
        init();
    }

    public AutoTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setTextColor(Colors.warnaAutoTitle());
    }

}
