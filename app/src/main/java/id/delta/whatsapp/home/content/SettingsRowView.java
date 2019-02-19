package id.delta.whatsapp.home.content;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/*import com.whatsapp.About;
import com.whatsapp.SettingsAccount;
import com.whatsapp.SettingsChat;
import com.whatsapp.SettingsContacts;
import com.whatsapp.SettingsDataUsage;
import com.whatsapp.SettingsHelp;
import com.whatsapp.SettingsNotifications;*/

import com.whatsapp.About;
import com.whatsapp.SettingsAccount;
import com.whatsapp.SettingsChat;
import com.whatsapp.SettingsContacts;
import com.whatsapp.SettingsDataUsage;
import com.whatsapp.SettingsHelp;
import com.whatsapp.SettingsNotifications;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;

public class SettingsRowView extends View implements View.OnClickListener {

    private Activity mActivity;
    private Paint mIconPaint, mLabelPaint;
    private Bitmap mIcon;
    private String mLabel = "";
    private Drawable mIconDrawable = null;
    private int mIconMarginLeft, mIconSize;
    private int mLabelMarginLeft;
    private Rect mLabelRect;
    private float mLabelOffset;

    public SettingsRowView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SettingsRowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SettingsRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SettingsRowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, MainActivity mActivity) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attributeSet, int defStyleAttr) {
        mActivity = (Activity) context;

        setClickable(true);
        setFocusable(true);

        if (attributeSet != null) {
            int[] attrs = {Tools.intAttr("icon"), Tools.intAttr( "label")};

            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attributeSet,
                    attrs,
                    defStyleAttr, 0);


            try {
                mLabel = a.getString(1);
                mIconDrawable = a.getDrawable(0);
            } finally {
                a.recycle();
            }
        }

        setBackground(Tools.getSelectableItemBackground(mActivity));
        setClickable(true);
        setFocusable(true);

        mIconSize = Tools.dpToPx(mActivity, 24);
        mIconMarginLeft = Tools.dpToPx(mActivity, 16);

        mIconPaint = new Paint();
        mIconPaint.setColorFilter(new PorterDuffColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_IN));

        mIcon = Tools.drawableToBitmap(mIconDrawable);
        mIcon = Tools.resizeBitmap(mIcon, mIconSize, mIconSize);

        mLabelRect = new Rect();

        mLabelPaint = new Paint();
        mLabelPaint.setAntiAlias(true);
        mLabelPaint.setColor(Colors.themedTextColor());
        mLabelPaint.getTextBounds(mLabel, 0, mLabel.length(), mLabelRect);
        mLabelPaint.setTextSize(Tools.spToPx(mActivity, 16));

        mLabelMarginLeft = Tools.dpToPx(mActivity, 72);

        float textHeight = mLabelPaint.descent() - mLabelPaint.ascent();
        mLabelOffset = (textHeight / 2) - mLabelPaint.descent();

      //  setBackgroundColor(Theme.getColor(Theme.Key.COLOR_PREFERENCE_ITEM_BUTTON_BACKGROUND));

        setOnClickListener(this);
    }

    // https://stackoverflow.com/questions/11120392/android-center-text-on-canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasHeight = canvas.getHeight();
        canvas.drawBitmap(mIcon, mIconMarginLeft, (canvasHeight - mIconSize) / 2, mIconPaint);
        canvas.drawText(mLabel, mLabelMarginLeft, (canvasHeight / 2) + mLabelOffset, mLabelPaint);
    }

    @Override
    public void onClick(View view) {
        String name = mActivity.getResources().getResourceEntryName(view.getId());

        Intent intent = null;

        switch (name) {

            // WhatsApp
            case "account_info":
                intent = new Intent(mActivity, SettingsAccount.class);
                break;
            case "chat":
                intent = new Intent(mActivity, SettingsChat.class);
                break;
            case "notifications":
                intent = new Intent(mActivity, SettingsNotifications.class);
                break;
            case "data":
                intent = new Intent(mActivity, SettingsDataUsage.class);
                break;
            case "help":
                intent = new Intent(mActivity, SettingsHelp.class);
                break;
        }

        if (intent != null) {
            mActivity.startActivity(intent);
        }
    }

}