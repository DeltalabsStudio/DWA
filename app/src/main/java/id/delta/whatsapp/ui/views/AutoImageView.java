package id.delta.whatsapp.ui.views;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.whatsapp.WaImageView;

import id.delta.whatsapp.value.Colors;

/**
 * Created by DELTALABS on 6/18/2017.
 */

public class AutoImageView extends WaImageView {

    public AutoImageView(Context context) {
        super(context);
        init();
    }

    public AutoImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setColorFilter(Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_ATOP);
    }

}
