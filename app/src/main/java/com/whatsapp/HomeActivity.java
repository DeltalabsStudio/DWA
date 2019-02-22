package com.whatsapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eightbitlab.blurview.BlurView;
import com.eightbitlab.blurview.SupportRenderScriptBlur;
import com.navdrawer.SimpleSideDrawer;
import com.squareup.picasso.Picasso;
import com.whatsapp.contact.a.d;
import com.whatsapp.contact.b;
import com.whatsapp.observablelistview.ObservableListView;
import com.whatsapp.observablelistview.ObservableScrollViewCallbacks;
import com.whatsapp.observablelistview.ScrollState;
import com.whatsapp.util.cv;
import com.whatsapp.wallpaper.WallPaperView;

import java.io.File;


import id.delta.whatsapp.R;
import id.delta.whatsapp.activities.DialerActivity;
import id.delta.whatsapp.dialog.DialogAbout;
import id.delta.whatsapp.dialog.DialogAdd;
import id.delta.whatsapp.home.stock.CurvedNavigationView;
import id.delta.whatsapp.home.stock.NavigationDrawer;
import id.delta.whatsapp.implement.NewChatListener;
import id.delta.whatsapp.implement.OnPageSelected;
import id.delta.whatsapp.status.FragmentStatus;
import id.delta.whatsapp.ui.views.CurvedBottom;
import id.delta.whatsapp.ui.views.MarqueeToolbar;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Dnd;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.value.Wallpaper;

import static id.delta.whatsapp.value.Main.homeTitle;
import static id.delta.whatsapp.value.Main.searchIcon;
import static id.delta.whatsapp.value.Main.subtitleColor;
import static id.delta.whatsapp.value.Main.titleColor;

public class HomeActivity extends DialogToastActivity implements DialogAdd.AddListener, ObservableScrollViewCallbacks {

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";
    public Bitmap pictureBitmap;
    public FragmentStatus mStatusFragment;
    public NavigationDrawer mNavigationDrawer;

    ViewPager mPager;
    HomePagerSlidingTabStrip tabs;
    private SimpleSideDrawer mNav;

    public CurvedNavigationView mCurvedNavigation;
    public View mStatusContainer;

    int badge = 1;
    public int o;

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
        tabs = (HomePagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mPager);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int i) {
                new OnPageSelected(HomeActivity.this, i).onPageListener();
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });


        //SimpleSideDrawer
        mNav = new SimpleSideDrawer(this);
        mNav.setRightBehindContentView(R.layout.delta_dialer_activity);

        //Include
        initAvatar();
        initToolbar();
        initFragment();
        initSearch();
        Wallpaper.setDatafolder();
        initWall();
        initBackground();
        initBlur();

        setTitle(homeTitle());
        getSupportActionBar().setSubtitle(R.string.sum_hideplay_broadcast);

        customSearch();


        // if(Tools.checkInternetNow())Tools.showToast("Has Internet");

        //antiMaling
      /*  z();
        TextView m = findViewById(getResources().getIdentifier("mInfo", "id", getPackageName()));
        m.setVisibility(View.GONE);
        m.setText(c(Me));
        m.setTextColor(drawerLabel());*/

        //Anti DCRC Ampuh
        //setSign(this, "9622e43e79a54fb67d3fe58ca55444666a32e0a8");

       // showToast(String.valueOf(badge));

        initImg();
    }

    private void initBlur(){
        FrameLayout mFrame = findViewById(Tools.intId("root_view"));
        BlurView mBlur = findViewById(Tools.intId("mBlur"));

        final float radius = 10f;
        final Drawable windowBackground = getWindow().getDecorView().getBackground();
        mBlur.setupWith(mFrame)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new SupportRenderScriptBlur(this))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);
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
        mCurvedNavigation = findViewById(Tools.intId("mCurvedNavigation"));
        mStatusContainer = findViewById(Tools.intId("mStatusContainer"));
    }

    public void createNew(){
        HomeActivity mHome = HomeActivity.this;
        ws ws2 = HomeActivity.e(mHome, HomeActivity.f(mHome, mHome.o));
        if (ws2 != null) {
            ws2.m_();
        }
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

    public void statusView(String posisi){
        if(posisi.equals("BAWAH")){
            mStatusContainer.setVisibility(View.GONE);
        }else {
            mStatusContainer.setVisibility(View.VISIBLE);
        }
    }


    private void customSearch(){
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

    @Override
    public void onBackPressed() {
        if(!mNavigationDrawer.onBackPressed()|| processOnBackPressed()){
            super.onBackPressed();
        }
    }

    private boolean processOnBackPressed() {
        return false;
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        if(scrollState.equals(ScrollState.DOWN)){
            statusView("ATAS");
        }else {
            statusView("BAWAH");
        }
    }

    public void initScroll(Fragment fragment, View view){
        Activity activity = fragment.getActivity();
        final ObservableListView mList = view.findViewById(android.R.id.list);
        if (activity instanceof ObservableScrollViewCallbacks) {
            mList.setScrollViewCallbacks((ObservableScrollViewCallbacks) activity);
        }

    }

    public void showToast(String toast){
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, 0, Menu.NONE, Tools.intString("menu_restart")).setIcon(searchIcon()).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        Dnd.addMenu(menu);
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

    public static ws e(HomeActivity homeActivity, int n2) {
       /* int n3 = HomeActivity.h(homeActivity, n2);
        for (android.support.v4.app.h h2 : homeActivity.aa()) {
            if (n3 == 1 && h2 instanceof ConversationsFragment) {
                return (ws)h2;
            }
            if (n3 == 2 && h2 instanceof StatusesFragment) {
                return (ws)h2;
            }
            if (n3 != 3 || !(h2 instanceof CallsFragment)) continue;
            return (ws)h2;
        }*/
        return null;
    }

    public static int f(HomeActivity homeActivity, int n2) {
        return 0;//HomeActivity.b(homeActivity.aJ, n2);
    }

    private void initImg(){
        ImageView mImage = findViewById(Tools.intId("fab_aux"));
        mImage.setOnClickListener(new NewChatListener(this));
        initFab(mImage);
    }
}
