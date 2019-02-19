package id.delta.whatsapp.ui.accent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.whatsapp.WaTextView;

import id.delta.whatsapp.value.Colors;

/**
 * Created by DELTALABS on 6/18/2017.
 */

public class AccentTextView extends WaTextView {
    public AccentTextView(Context context) {
        super(context);
        init();
    }

    public AccentTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AccentTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setTextColor(Colors.setWarnaAksen());
    }

}
