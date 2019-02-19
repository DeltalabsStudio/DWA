package id.delta.whatsapp.ui.accent;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;

import id.delta.whatsapp.value.Colors;


public class AccentSwitch extends SwitchCompat {

	public AccentSwitch(Context context, AttributeSet c) {
		super(context, c);
		setTintColor();

	}
	public AccentSwitch(Context context) {
		super(context);
		setTintColor();
	}
	public AccentSwitch(Context context, AttributeSet c, int i) {
		super(context, c, i);
		setTintColor();
	}


	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		changeColor(checked);
	}

	private void changeColor(boolean isChecked) {
		if (Build.VERSION.SDK_INT >= 16) {
			int thumbColor;
			int trackColor;

			if(isChecked) {
				thumbColor = Colors.setWarnaAksen();
				trackColor = 80 * 0x01000000;
			} else {
				thumbColor = Color.argb(255, 236, 236, 236);
				trackColor = 0xff9e9e9e;
			}

			try {
				getThumbDrawable().setColorFilter(thumbColor, PorterDuff.Mode.MULTIPLY);
				getTrackDrawable().setColorFilter(trackColor+thumbColor, PorterDuff.Mode.MULTIPLY);

			}
			catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}


	private void setTintColor(){
	//	setTextSize(TextUtils.setUkuranTextView());
		//TextUtils.setUkuranText(getContext(),this);
	//	setTextColor(TextUtils.setGeneralTextColor());
	//	setTypeface(null, TextUtils.setTypeFaceRead());
		setChecked(isChecked());
	}

}
