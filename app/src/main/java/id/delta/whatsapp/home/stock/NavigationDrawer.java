package id.delta.whatsapp.home.stock;

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

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.whatsapp.DialogToastActivity;
import com.whatsapp.HomeActivity;
import com.whatsapp.ProfileInfoActivity;
import com.whatsapp.contact.a.d;
import com.whatsapp.contact.b;
import com.whatsapp.contact.h;
import com.whatsapp.yv;

import java.io.File;

import id.delta.whatsapp.libs.ViewDragHelper;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Main;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.value.Wallpaper;

public class NavigationDrawer extends RelativeLayout {

    private HomeActivity mContext;
    private boolean mOpened = false;
    private ContentLayout mParent;
    private ViewDragHelper mDragHelper;

    public NavigationDrawer(Context context) {
        super(context);
        init(context);
    }

    public NavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavigationDrawer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        mContext = (HomeActivity)context;
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

        if (parent instanceof ContentLayout) {
            mParent = (ContentLayout) parent;
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
        final View header = findViewById(Tools.intId("drawer_header"));
        header.setBackgroundColor(Colors.setWarnaPrimer());

        final View mHeaderContent = findViewById(Tools.intId("drawer"));
        mHeaderContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderContent.setPadding(0, 0, 0, 50);
            }
        });

        if(Prefs.getBoolean(Keys.KEY_HOME_COVER, false)){
            try{
                KenBurnsView mCover = findViewById(Tools.intId("mCover"));
                File coverPath = new File(Wallpaper.coverPath);
                if(coverPath.exists()){
                    Picasso.with(mContext).load(coverPath).into(mCover);
                }else {
                    Picasso.with(mContext).load(Tools.intDrawable("cover")).into(mCover);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        nameTV.setTextColor(Main.drawerTitle());
        numberTV.setTextColor(Main.drawerTitle());

        this.setBackgroundColor(Main.drawerBackground());
    }
}
