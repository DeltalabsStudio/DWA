package id.delta.whatsapp.home.content;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsapp.DialogToastActivity;
import com.whatsapp.yv;
import com.whatsapp.ProfileInfoActivity;
import com.whatsapp.contact.h;
import com.whatsapp.contact.b;
import com.whatsapp.contact.a.d;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.libs.ViewDragHelper;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;

public class HomeNavigationDrawer extends RelativeLayout {

    private MainActivity mContext;
    private boolean mOpened = false;
    private HomeContentLayout mParent;
    private ViewDragHelper mDragHelper;

    public HomeNavigationDrawer(Context context) {
        super(context);
        init(context);
    }

    public HomeNavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeNavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HomeNavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        mContext = (MainActivity)context;
    }

    public void toggle() {
        mOpened = !mOpened;
        setOpen(mOpened);
    }

    public void setOpen(boolean open) {
        mOpened = open;

        int left = open? getWidth() : 0;
        mParent.getViewDragHelper().smoothSlideViewTo(this, left, 0);
        mParent.invalidate();
      /*  final View mBlur = mContext.findViewById(Tools.intId("mBlur"));
        if(!open){
            mBlur.setBackgroundColor(Tools.getColor("delta_transparent"));
        }else {
            mBlur.setBackgroundColor(Colors.warnaHitam50);
        }*/
    }

    public boolean onBackPressed() {
        if (mOpened) {
            setOpen(false);
            return true;
        }
        return false;
    }

    public void setOpenUnanimated(boolean open) {
        mOpened = open;

        int left = open? 0 : getWidth() * -1;
        offsetLeftAndRight(left);
    }

    public boolean isOpen() {
        return mOpened;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        ViewParent parent = getParent();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setTranslationX(getWidth() * -1);
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        if (parent instanceof HomeContentLayout) {
            mParent = (HomeContentLayout) parent;
            mParent.setNavigationDrawer(this);
            mDragHelper = mParent.getViewDragHelper();
        }

        // Open Profile Info when pic is clicked
        View profile = findViewById(Tools.intId("profile_picture"));

        if (profile != null) {

            profile.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ProfileInfoActivity.class);
                    mContext.startActivity(intent);
                    setOpen(false);
                }
            });

        }

        TextView nameTV = findViewById(Tools.intId("name"));
        TextView numberTV = findViewById(Tools.intId("number"));
        ImageView pictureIM = findViewById(Tools.intId("profile_picture"));

     /*   if (isInEditMode() || isPreviewApp(mContext)) {
            return;
        }*/

        yv meManager = ((DialogToastActivity) mContext).mMeManager;
        yv.a user = meManager.d();

        nameTV.setText(user.p);
        numberTV.setText(h.a(user));

        d picture = d.a();
        Bitmap pictureBitmap = picture.a(user, 200, -1.0f, false);

        if (pictureBitmap == null) {
            pictureBitmap = b.a().b(user);
        }

        pictureIM.setImageBitmap(pictureBitmap);

        int THEME = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
        if(THEME == Themes.LIGHTTHEME){
            this.setBackgroundColor(Color.WHITE);
        }else {
            this.setBackgroundColor(Tools.getColor("twitter_night"));
        }

        final View header = findViewById(Tools.intId("header"));
        header.setBackgroundColor(Colors.setWarnaPrimer());
        header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                header.setPadding(0, 50, 0, 0);
            }
        });
    }
}
