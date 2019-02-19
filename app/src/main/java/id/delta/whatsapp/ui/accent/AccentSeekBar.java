package id.delta.whatsapp.ui.accent;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.SeekBar;

import id.delta.whatsapp.value.Colors;

@SuppressLint("AppCompatCustomView")
public class AccentSeekBar extends SeekBar {

	public AccentSeekBar(Context context) {
		super(context);
		setInitSeekBar();
	}

	public AccentSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setInitSeekBar();
	}

	public AccentSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setInitSeekBar();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public AccentSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		setInitSeekBar();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setInitSeekBar(){
		this.getProgressDrawable().setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_ATOP);
		this.getThumb().setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_ATOP);
	}


}
