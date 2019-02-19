package id.delta.whatsapp.ui.accent;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.whatsapp.WaImageView;

import id.delta.whatsapp.value.Colors;


/**
 * Created by DELTALABS on 6/18/2017.
 */

public class AccentImageView extends WaImageView {

    public AccentImageView(Context context) {
        super(context);
        init();
    }

    public AccentImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AccentImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setColorFilter(Colors.setWarnaAksen());
    }

}
