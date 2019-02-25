package id.delta.whatsapp.home.stock;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;
import com.whatsapp.ArchivedConversationsActivity;
import com.whatsapp.DialogToastActivity;
import com.whatsapp.GroupMembersSelector;
import com.whatsapp.HomeActivity;
import com.whatsapp.ListMembersSelector;
import com.whatsapp.ProfileInfoActivity;
import com.whatsapp.SettingsAccount;
import com.whatsapp.SettingsChat;
import com.whatsapp.SettingsDataUsage;
import com.whatsapp.SettingsHelp;
import com.whatsapp.SettingsNotifications;
import com.whatsapp.StarredMessagesActivity;
import com.whatsapp.ThumbnailButton;
import com.whatsapp.WebSessionsActivity;
import com.whatsapp.contact.a.d;
import com.whatsapp.contact.b;
import com.whatsapp.contact.h;
import com.whatsapp.yv;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import id.delta.whatsapp.activities.PrivacyActivity;
import id.delta.whatsapp.libs.ViewDragHelper;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Icons;
import id.delta.whatsapp.value.Main;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.value.Wallpaper;

public class NavigationDrawer extends RelativeLayout implements View.OnClickListener {

    private HomeActivity mContext;
    private boolean mOpened = false;
    private ContentLayout mParent;
    private ViewDragHelper mDragHelper;

    ImageView mImageItem, mCollapseIcon;
    TextView mLabelItem;
    RelativeLayout mItem;
    View mDrawerHeader, mSettingsView;
    KenBurnsView mCover;
    ThumbnailButton mProfilePicture;
    TextView mName, mNumber;
    boolean isCollapse = false;


    private int [] mItemIds = {
            Tools.intId("r0"),
            Tools.intId("r1"),
            Tools.intId("r2"),
            Tools.intId("r3"),
            Tools.intId("r4"),
            Tools.intId("r5"),
            Tools.intId("r6"),
            Tools.intId("r7"),
            Tools.intId("r8"),
            Tools.intId("r9"),
            Tools.intId("r10"),
            Tools.intId("r11"),
            Tools.intId("r12")
    };

    private int [] mImageIds = {
            Tools.intId("i0"),
            Tools.intId("i1"),
            Tools.intId("i2"),
            Tools.intId("i3"),
            Tools.intId("i4"),
            Tools.intId("i5"),
            Tools.intId("i6"),
            Tools.intId("i7"),
            Tools.intId("i8"),
            Tools.intId("i9"),
            Tools.intId("i10"),
            Tools.intId("i11"),
            Tools.intId("i12")
    };

    private int [] mLabelIds = {
            Tools.intId("l0"),
            Tools.intId("l1"),
            Tools.intId("l2"),
            Tools.intId("l3"),
            Tools.intId("l4"),
            Tools.intId("l5"),
            Tools.intId("l6"),
            Tools.intId("l7"),
            Tools.intId("l8"),
            Tools.intId("l9"),
            Tools.intId("l10"),
            Tools.intId("l11"),
            Tools.intId("l12")
    };

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
        /*
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

        */

        try{
            mDrawerHeader = findViewById(Tools.intId("mDrawerHeader"));
            mCover = findViewById(Tools.intId("mCover"));
            mName = findViewById(Tools.intId("mName"));
            mNumber = findViewById(Tools.intId("mNumber"));
            mProfilePicture = findViewById(Tools.intId("mProfilePicture"));

            mDrawerHeader.setOnClickListener(this);

            mDrawerHeader.setBackgroundColor(Colors.setWarnaPrimer());
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
            mNumber.setTextColor(Main.drawerTitle());
            mName.setTextColor(Main.drawerTitle());

            yv meManager = new yv();
            yv.a user = meManager.d();
            mName.setText(user.p);
            mNumber.setText(h.a(user));
            d picture = d.a();
            Bitmap pictureBitmap = picture.a(user, 200, -1.0f, false);

            if (pictureBitmap == null) {
                pictureBitmap = b.a().b(user);
            }
            mProfilePicture.setImageBitmap(pictureBitmap);

            for (int i = 0; i < mImageIds.length; i++) {
                mImageItem = findViewById(mImageIds[i]);
                mLabelItem = findViewById(mLabelIds[i]);
                mItem = findViewById(mItemIds[i]);

                mLabelItem.setTextColor(Main.drawerLabel());
                mItem.setOnClickListener(this);

                if(Prefs.getBoolean(Keys.KEY_CUSTOM_ICON, false)){
                    Picasso.with(mContext).load(new File(Environment.getExternalStorageDirectory(),
                            Icons.iconPath+ Icons.mItemName[i]+ Icons.fileType)).into(mImageItem);
                }
            }

            View mView = findViewById(Tools.intId("r0"));
            mSettingsView = findViewById(Tools.intId("mSettingsView"));
            mCollapseIcon = findViewById(Tools.intId("mDropdown"));

            mView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isCollapse){
                        isCollapse = false;
                        Tools.collapse(mSettingsView, 100, 0);
                        mCollapseIcon.setImageResource(Tools.intDrawable("delta_ic_dropdown"));
                    }else {
                        isCollapse = true;
                        mCollapseIcon.setImageResource(Tools.intDrawable("delta_ic_dropup"));
                        Tools.expand(mSettingsView, 100, Tools.dpToPx(mContext, 240));
                    }
                }
            });

            setBackgroundColor(Main.drawerBackground());

        }catch (Exception e){
            Tools.showToast(e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        try{
            setOpen(false);
            if(v == mDrawerHeader){
                Actions.startActivity(mContext, ProfileInfoActivity.class);
            }
            if(v.getId() == mItemIds[1]){
                Actions.startActivity(mContext, ArchivedConversationsActivity.class);
            } else if(v.getId() == mItemIds[2]){
                Actions.startActivity(mContext, StarredMessagesActivity.class);
            } else if(v.getId() == mItemIds[3]){
                Actions.startActivity(mContext, WebSessionsActivity.class);
            } else if(v.getId() == mItemIds[4]){
                Collection<String> collection  = null;
                Intent intent = new Intent(mContext, GroupMembersSelector.class);
                intent.putExtra("entry_point", 2);
                if (collection != null && !collection.isEmpty()) {
                    intent.putExtra("selected", (Serializable)new ArrayList(collection));
                }
                mContext.startActivity(intent);
            } else if(v.getId() == mItemIds[5]){
                Actions.startActivity(mContext, ListMembersSelector.class);
            } else if(v.getId() == mItemIds[6]){
                Actions.startActivity(mContext, PrivacyActivity.class);
            } else if(v.getId() == mItemIds[7]){
                HomeActivity.openSettings();
            } else if(v.getId() == mItemIds[8]){
                Actions.startActivity(mContext, SettingsAccount.class);
            } else if(v.getId() == mItemIds[9]){
                Actions.startActivity(mContext, SettingsChat.class);
            } else if(v.getId() == mItemIds[10]){
                Actions.startActivity(mContext, SettingsNotifications.class);
            } else if(v.getId() == mItemIds[11]){
                Actions.startActivity(mContext, SettingsDataUsage.class);
            } else if(v.getId() == mItemIds[12]){
                Actions.startActivity(mContext, SettingsHelp.class);
            }
        }catch (Exception e){
            Tools.showToast(e.getMessage());
        }
    }
}
