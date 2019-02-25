package com.whatsapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.navdrawer.SimpleSideDrawer;
import com.whatsapp.observablelistview.ObservableListView;
import com.whatsapp.observablelistview.ObservableScrollViewCallbacks;
import com.whatsapp.observablelistview.ScrollState;


import id.delta.whatsapp.R;
import id.delta.whatsapp.activities.SettingsActivity;
import id.delta.whatsapp.home.stock.CurvedNavigationView;
import id.delta.whatsapp.home.stock.NavigationDrawer;
import id.delta.whatsapp.implement.OnPageSelected;
import id.delta.whatsapp.status.FragmentStatus;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Dnd;
import id.delta.whatsapp.home.presenters.HomeView;
import id.delta.whatsapp.value.Themes;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static id.delta.whatsapp.utils.Tools.intId;
import static id.delta.whatsapp.value.Main.homeTitle;
import static id.delta.whatsapp.value.Main.searchIcon;

public class HomeActivity extends DialogToastActivity implements ObservableScrollViewCallbacks {
    private int mBaseTranslationY;

    public static final String ARG_INITIAL_POSITION = "ARG_INITIAL_POSITION";

    public FragmentStatus mStatusFragment;
    public NavigationDrawer mNavigationDrawer;
    public CurvedNavigationView mCurvedNavigation;
    public View mStatusContainer;

    //DrawerSimple if use custom icon drawer is lagging //
    public SimpleSideDrawer mNav;

    ViewPager viewPager;
    HomePagerSlidingTabStrip homePagerSlidingTabStrip;

    public static HomeActivity mHome;
    public int o;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Themes.setHomeTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new HomeTabsAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(1);
        viewPager.setOffscreenPageLimit(3);
        homePagerSlidingTabStrip = (HomePagerSlidingTabStrip) findViewById(R.id.tabs);
        homePagerSlidingTabStrip.setViewPager(viewPager);
        homePagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
       // mNav.setLeftBehindContentView(R.layout.conversations_row);

        //Include
        initDelta();

        setTitle(homeTitle());
        getSupportActionBar().setSubtitle(R.string.sum_hideplay_broadcast);

        FrameLayout mSearchHolder = findViewById(intId("search_holder"));
        mSearchHolder.setBackgroundColor(Colors.setWarnaPrimer());

        customSearch();

        mHome = HomeActivity.this;
    }

    private void initDelta(){
        new HomeView(this).initHome();
    }

    public void selectPage(int page){
        try{
            viewPager.setCurrentItem(page);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void createNew(){
        HomeActivity mHome = HomeActivity.this;
        ws ws2 = HomeActivity.e(mHome, HomeActivity.f(mHome, mHome.o));
        if (ws2 != null) {
            ws2.m_();
        }
    }

    public static void openSettings(){
        Actions.startSettings(mHome, SettingsActivity.STOCK);
    }

    public static void newGroup(){
        NewGroup.a(mHome, 2, null);
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
        if(!mNav.isClosed()){
            mNav.closeLeftSide();
            return;
        }
        if(mNavigationDrawer.isOpen()){
            mNavigationDrawer.onBackPressed();
            return;
        }
        super.onBackPressed();
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
            d(true);
        }else {
            d(false);
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

    private void d(boolean bl2){
        slide(bl2);
    }

    private void slide(boolean bl2) {
        if(Prefs.getBoolean(Keys.KEY_HIDESTATUS, false)){
            if(bl2){
                mStatusContainer.setVisibility(View.VISIBLE);
            }else {
                mStatusContainer.setVisibility(View.GONE);
            }
        }

    }


}
