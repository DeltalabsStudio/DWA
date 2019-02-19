package id.delta.whatsapp.ui.accent;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;

import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;


public class AccentCheckBox extends CheckBox {

	public AccentCheckBox(Context context) {
		super(context);
		setChecked(isChecked());
	}

	public AccentCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		setChecked(isChecked());
	}

	public AccentCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setChecked(isChecked());
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public AccentCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		setChecked(isChecked());
	}

	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);

		changeColor(checked);
	}

	private void changeColor(boolean isChecked) {
		if (Build.VERSION.SDK_INT >= 16) {
			Resources res = getResources();
            Drawable d = res.getDrawable(Tools.intDrawable("abc_btn_check_material"));
		 
			if(isChecked) {
				d.setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_ATOP);
				
			} else {
				
				d.setColorFilter(0xff9e9e9e, PorterDuff.Mode.SRC_ATOP);
				
			}

			try {
				AccentCheckBox.this.setButtonDrawable(d);

			}
			catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}
}
