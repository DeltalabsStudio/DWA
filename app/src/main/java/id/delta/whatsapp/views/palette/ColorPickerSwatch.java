package id.delta.whatsapp.views.palette;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import id.delta.whatsapp.utils.Tools;


public class ColorPickerSwatch extends FrameLayout implements View.OnClickListener {
    private ImageView mCheckmarkImage;
    private int mColor;
    private OnColorSelectedListener mOnColorSelectedListener;
    private ImageView mSwatchImage;

    public ColorPickerSwatch(Context paramContext, int color, boolean checked, OnColorSelectedListener onColorSelectedListener) {
        super(paramContext);
        mColor = color;
        mOnColorSelectedListener = onColorSelectedListener;
        LayoutInflater.from(paramContext).inflate(Tools.intLayout("delta_color_swatch"), this);
        mSwatchImage = ((ImageView) findViewById(Tools.intId("color_picker_swatch")));
        mCheckmarkImage = ((ImageView) findViewById(Tools.intId("color_picker_checkmark")));
        setColor(color);
        setChecked(checked);
        setOnClickListener(this);
    }

    private void setChecked(boolean checked) {
        if (checked) {
            mCheckmarkImage.setVisibility(View.VISIBLE);
            return;
        }

        mCheckmarkImage.setVisibility(View.GONE);
    }

    public void onClick(View view) {
        if (mOnColorSelectedListener != null) {
            mOnColorSelectedListener.onColorSelected(mColor);
        }
    }

    protected void setColor(int color) {
       // akl[] drawables = new akl[1];
       // drawables[0] = ContextCompat.getDrawable(getContext(), Tools.intDrawable("color_picker_swatch"));
       Drawable[] drawables = new Drawable[]
                { Tools.getDrawable("delta_aw_swatch")};

        mSwatchImage.setImageDrawable(new ColorStateDrawable(drawables, color));
      //  mSwatchImage.setBackgroundColor(color);
    }

    public interface OnColorSelectedListener {
        void onColorSelected(int color);
    }
}
