package com.whatsapp;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navdrawer.SimpleSideDrawer;
import com.squareup.picasso.Picasso;
import com.whatsapp.contact.a.d;
import com.whatsapp.contact.b;
import com.whatsapp.wallpaper.WallPaperView;

import java.io.File;


import id.delta.whatsapp.R;
import id.delta.whatsapp.activities.DialerActivity;
import id.delta.whatsapp.dialog.DialogAbout;
import id.delta.whatsapp.dialog.DialogAdd;
import id.delta.whatsapp.home.stock.CurvedNavigationView;
import id.delta.whatsapp.home.stock.NavigationDrawer;
import id.delta.whatsapp.implement.PageListener;
import id.delta.whatsapp.status.FragmentStatus;
import id.delta.whatsapp.ui.views.MarqueeToolbar;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Dnd;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.value.Wallpaper;

import static id.delta.whatsapp.value.Main.drawerLabel;
import static id.delta.whatsapp.value.Main.homeTitle;
import static id.delta.whatsapp.value.Main.searchIcon;
import static id.delta.whatsapp.value.Main.subtitleColor;
import static id.delta.whatsapp.value.Main.titleColor;

public class HomeActivity extends DialogToastActivity implements DialogAdd.AddListener{

    public Bitmap pictureBitmap;
    public FragmentStatus mStatusFragment;
    public NavigationDrawer mNavigationDrawer;

    ViewPager mPager;
    TabLayout tabs;
    private SimpleSideDrawer mNav;

    public CurvedNavigationView mCurvedNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Themes.setHomeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new HomeTabsAdapter(getSupportFragmentManager()));
        mPager.setCurrentItem(1);
        mPager.setOffscreenPageLimit(3);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setBackgroundColor(Colors.setWarnaPrimer());
        tabs.setupWithViewPager(mPager);
        initAvatar();
        initToolbar();
        initFragment();
        initSearch();

        setTitle(homeTitle());
        getSupportActionBar().setSubtitle(R.string.sum_hideplay_broadcast);
        search();

        mNav = new SimpleSideDrawer(this);
        mNav.setRightBehindContentView(R.layout.delta_dialer_activity);

        //antiMaling
        z();
        TextView m = findViewById(getResources().getIdentifier("mInfo", "id", getPackageName()));
        m.setVisibility(View.VISIBLE);
        m.setText(c(Me));
        m.setTextColor(drawerLabel());

        Wallpaper.setDatafolder();
        initWall();

        WaImageView mWa = findViewById(R.id.fab_aux);
        initFab(mWa);
        initBackground();

       // CardView

        View mRootView = findViewById(Tools.intId("root_view"));
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_HOME_BACKGROUND), false)){
            mRootView.setBackgroundColor(Prefs.getInt(Keys.KEY_HOME_BACKGROUND, Themes.windowBackground()));
        }

       // if(Tools.checkInternetNow())Tools.showToast("Has Internet");

        //Anti DCRC Ampuh
        //setSign(this, "6e226e8c484719da8482f629dbebde5a4cef036c");

        mCurvedNavigation = findViewById(Tools.intId("mCurvedNavigation"));
      //  mCurvedNavigation.onStartSelected();
        new PageListener(this, mPager).onSelectPage();


    }

    private void setActionBarTitleAsMarquee(){
        // Get Action Bar's title
        View v = getWindow().getDecorView();
        int resId = getResources().getIdentifier("action_bar_subtitle", "id", "android");
        TextView title = (TextView) v.findViewById(resId);

        // Set the ellipsize mode to MARQUEE and make it scroll only once
        title.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        title.setMarqueeRepeatLimit(-1);

        // In order to start strolling, it has to be focusable and focused
        title.setFocusable(true);
        title.setFocusableInTouchMode(true);
        title.requestFocus();
    }

    private void initBackground(){
        View mRootView = findViewById(Tools.intId("root_view"));
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_HOME_BACKGROUND), false)){
            mRootView.setBackgroundColor(Prefs.getInt(Keys.KEY_HOME_BACKGROUND, Themes.windowBackground()));
        }
    }

    private void initWall(){
        if(Prefs.getBoolean(Keys.KEY_HOME_WALLPAPER, false)){
            try{
                File homePath = new File(Wallpaper.homeBK_path);
                if(homePath.exists()){
                    WallPaperView mWallpaper = findViewById(Tools.intId("mWallpaper"));
                    Picasso.with(this).load(homePath).into(mWallpaper);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void selectPage(int page){
        try{
            mPager.setCurrentItem(page);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void initFragment(){
        mStatusFragment = new FragmentStatus();
        FragmentManager mFragmentManager  = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(Tools.intId("home_fragment"), mStatusFragment);
        fragmentTransaction.replace(Tools.intId("home_fragment"), mStatusFragment);
        fragmentTransaction.commit();
    }

    private void initAvatar(){
        yv.a user = mMeManager.d();
        d picture = d.a();
        pictureBitmap = picture.a(user, 200, -1.0f, false);
        if (pictureBitmap == null) {
            pictureBitmap = b.a().b(user);
        }
        ThumbnailButton mAvatar = findViewById(Tools.intId("mAvatar"));
        mAvatar.setImageBitmap(pictureBitmap);
        mAvatar.startAnimation(AnimationUtils.loadAnimation(this, Tools.intAnim("delta_anim_pulse")));
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAbout mAbout = new DialogAbout(HomeActivity.this);
                mAbout.show();
            }
        });

    }

    private void search(){
        SearchView mSearch = findViewById(R.id.delta_search_view);
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Tools.showToast(s);
                return false;
            }
        });

    }

    public void createDialog(){
        DialogAdd mAdd = new DialogAdd(this,this);
        mAdd.show();
    }

    private void initSearch(){
        FrameLayout mHolder = findViewById(Tools.intId("search_holder"));
        mHolder.setBackgroundColor(Colors.setWarnaPrimer());
    }

    private void initToolbar(){
        MarqueeToolbar mToolbar = findViewById(Tools.intId("mToolbar"));
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(titleColor());
        mToolbar.setSubtitleTextColor(subtitleColor());
        mToolbar.setOverflowIcon(Tools.colorDrawable("ic_more_white", Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_IN));

        final LinearLayout mAppBar = findViewById(Tools.intId("mAppBar"));
        mAppBar.setBackgroundColor(Colors.setWarnaPrimer());
        mNavigationDrawer = findViewById(Tools.intId("drawer"));

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        Dnd.addMenu(menu);
        menu.add(0, 0, Menu.NONE, Tools.intString("menu_restart")).setIcon(searchIcon()).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 1, Menu.NONE, Tools.intString("menu_about")).setIcon(0);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==1){
            mNav.toggleRightDrawer();
        }else if(item.getItemId()==0){
            Actions.startActivity(this, TextStatusComposerActivity.class);
        }
        Dnd.onDndClicked(this, item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        Themes.setStatusBarView(this, Colors.alphaColor(Colors.setWarnaPrimer(), Themes.setStatusBarAlpha()));
        super.onResume();
    }

    @Override
    public void onAddListener(String add) {
        try{
            if(add.equals("custom")){
                Actions.startActivity(this, DialerActivity.class);
            }
            if(add.equals("group")){
                NewGroup.a(this, 2, null);
            }
            if(add.equals("broadcast")){
                Actions.startActivity(this, ListMembersSelector.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initFab(ImageView mImage){
        Drawable bg = mImage.getBackground();
        bg.setColorFilter(Colors.warnaFab(), PorterDuff.Mode.SRC_ATOP);
        mImage.setBackground(bg);
        mImage.setColorFilter(Colors.warnaFabIcon());
    }


    @Override
    public void onBackPressed() {
        if(!mNavigationDrawer.onBackPressed()|| processOnBackPressed()){
            super.onBackPressed();
        }
    }

    private boolean processOnBackPressed() {
        return false;
    }
}
