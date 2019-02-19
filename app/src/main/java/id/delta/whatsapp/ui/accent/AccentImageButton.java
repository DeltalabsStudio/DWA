package id.delta.whatsapp.ui.accent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.whatsapp.WaImageButton;
import com.whatsapp.WaImageView;

import id.delta.whatsapp.value.Colors;


/**
 * Created by DELTALABS on 6/18/2017.
 */

public class AccentImageButton extends WaImageButton {

    public AccentImageButton(Context context) {
        super(context);
        init();
    }

    public AccentImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AccentImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.setColorFilter(Colors.setWarnaAksen());
    }

}
