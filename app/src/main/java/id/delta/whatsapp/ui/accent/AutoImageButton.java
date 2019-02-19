package id.delta.whatsapp.ui.accent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.whatsapp.WaImageButton;

import id.delta.whatsapp.value.Colors;


/**
 * Created by DELTALABS on 6/18/2017.
 */

public class AutoImageButton extends WaImageButton {

    public AutoImageButton(Context context) {
        super(context);
        init();
    }

    public AutoImageButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoImageButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        if(Colors.isDarken(Colors.setWarnaAksen())){
            this.setColorFilter(Colors.warnaPutih);
        }else {
            this.setColorFilter(Colors.warnaTitle);
        }
    }

}
