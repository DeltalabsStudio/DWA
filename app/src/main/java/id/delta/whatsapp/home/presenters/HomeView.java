package id.delta.whatsapp.home.presenters;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.eightbitlab.blurview.BlurView;
import com.eightbitlab.blurview.SupportRenderScriptBlur;
import com.squareup.picasso.Picasso;
import com.whatsapp.HomeActivity;
import com.whatsapp.ThumbnailButton;
import com.whatsapp.contact.a.d;
import com.whatsapp.contact.b;
import com.whatsapp.wallpaper.WallPaperView;
import com.whatsapp.yv;

import java.io.File;

import id.delta.whatsapp.home.stock.NavigationListener;
import id.delta.whatsapp.status.FragmentStatus;
import id.delta.whatsapp.ui.views.MarqueeToolbar;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.value.Wallpaper;

import static id.delta.whatsapp.utils.Tools.intId;
import static id.delta.whatsapp.value.Main.subtitleColor;
import static id.delta.whatsapp.value.Main.titleColor;

public class HomeView {

    HomeActivity mHome;

    public HomeView (HomeActivity mHome){
        this.mHome = mHome;
    }

    public void initHome(){
        //Anti DCRC Ampuh
        //setSign(this, "9622e43e79a54fb67d3fe58ca55444666a32e0a8");
        Wallpaper.setDatafolder();
        initWall();
        initToolbar();
        initAvatar();
        initFragment();
        initBackground();
        initBlur();
    }

    private void initToolbar(){
        MarqueeToolbar mToolbar = mHome.findViewById(intId("mToolbar"));
        mHome.setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(titleColor());
        mToolbar.setSubtitleTextColor(subtitleColor());
        mToolbar.setOverflowIcon(Tools.colorDrawable("ic_more_white", Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_IN));
        final LinearLayout mAppBar = mHome.findViewById(intId("mAppBar"));
        mAppBar.setBackgroundColor(Colors.setWarnaPrimer());
    }

    private void initAvatar(){
        yv.a user = mHome.mMeManager.d();
        d picture = d.a();
        Bitmap pictureBitmap = picture.a(user, 200, -1.0f, false);
        if (pictureBitmap == null) {
            pictureBitmap = b.a().b(user);
        }
        final ThumbnailButton mAvatar = mHome.findViewById(intId("mAvatar"));
        mAvatar.setImageBitmap(pictureBitmap);
        mAvatar.startAnimation(AnimationUtils.loadAnimation(mHome, Tools.intAnim("delta_anim_pulse")));
        mHome.mNavigationDrawer = mHome.findViewById(intId("mDrawer"));
        mHome.mCurvedNavigation = mHome.findViewById(intId("mCurvedNavigation"));
        mHome.mStatusContainer = mHome.findViewById(intId("mStatusContainer"));
        mAvatar.setOnClickListener(new NavigationListener(mHome.mNavigationDrawer));

    }

    private void initFragment(){
        mHome.mStatusFragment = new FragmentStatus();
        FragmentManager mFragmentManager  = mHome.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(intId("home_fragment"), mHome.mStatusFragment);
        fragmentTransaction.replace(intId("home_fragment"), mHome.mStatusFragment);
        fragmentTransaction.commit();
    }

    private void initWall(){
        if(Prefs.getBoolean(Keys.KEY_HOME_WALLPAPER, false)){
            try{
                File homePath = new File(Wallpaper.homeBK_path);
                if(homePath.exists()){
                    WallPaperView mWallpaper = mHome.findViewById(intId("mWallpaper"));
                    Picasso.with(mHome).load(homePath).into(mWallpaper);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void initBackground(){
        View mRootView = mHome.findViewById(intId("root_view"));
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_HOME_BACKGROUND), false)){
            mRootView.setBackgroundColor(Prefs.getInt(Keys.KEY_HOME_BACKGROUND, Themes.windowBackground()));
        }
    }

    private void initBlur(){
        FrameLayout mFrame = mHome.findViewById(intId("root_view"));
        BlurView mBlur = mHome.findViewById(intId("mBlur"));

        final float radius = 10f;
        final Drawable windowBackground = mHome.getWindow().getDecorView().getBackground();
        mBlur.setupWith(mFrame)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new SupportRenderScriptBlur(mHome))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);
    }

}
