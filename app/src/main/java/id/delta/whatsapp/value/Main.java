package id.delta.whatsapp.value;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.a;
import android.support.v7.widget.ActionBarContextView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.whatsapp.AppShell;
import com.whatsapp.HomeActivity;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.utils.WaPrefs;

import static android.graphics.PorterDuff.Mode.SRC_IN;

public class Main {

    public static void Countbar(TextView tv, boolean bool) {
        tv.setTextColor(titleColor());
    }

    public static void actionBarTitle(Activity mActivity, int string){
        mActivity.setTitle(setTitle(string));
    }

    public static SpannableString setTitle(int title){
        SpannableString s1 = new SpannableString(AppShell.ctx.getResources().getString(title));
        s1.setSpan(new ForegroundColorSpan(Colors.warnaAutoTitle()), 0, s1.length(), 0);
        return s1;
    }

    public static void headerBackground(AppCompatActivity mActivity, ActionBar mActionBar){
        try{
            actionBarBackground(mActionBar);
            Themes.setStatusBar(mActivity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void actionBarBackground(ActionBar mActionBar){
        mActionBar.setBackgroundDrawable(new ColorDrawable(Colors.setWarnaPrimer()));
    }


    public static MenuItem menuColor(MenuItem m, int i) {
        if (m != null) {
            m.setIcon(i);
            m.getIcon().mutate();
            m.getIcon().setColorFilter(Colors.warnaAutoTitle(), SRC_IN);
        }
        return m;
    }

    public static MenuItem menuColor(Activity a, MenuItem m, int i) {
        if (m != null) {
            m.setIcon(i);
            m.getIcon().mutate();
            m.getIcon().setColorFilter(Colors.warnaAutoTitle(), SRC_IN);
        }
        return m;
    }

    public static void setActionMode(ActionBarContextView a, View v) {
        if (a != null && v != null) {
            ImageView iv = (ImageView) v;
            if(iv!=null){
                a.getBackground().setColorFilter(Colors.setWarnaPrimer(), SRC_IN);
                iv.setColorFilter(Main.titleColor(), SRC_IN);
            }
        }
    }

    public static Drawable searchIcon(){
        return Tools.colorDrawable("delta_ic_search", Colors.warnaAutoTitle(), SRC_IN);
    }

    public static int dimenNol(){
        return Tools.intDimen("dimen_nol");
    }

    public static int titleColor(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_MAINTITLE), false)){
            return Prefs.getInt(Keys.KEY_MAINTITLE, Colors.warnaAutoTitle());
        }else {
            return Colors.warnaAutoTitle();
        }
    }

    public static int subtitleColor(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_MAINSUBTITLE), false)){
            return Prefs.getInt(Keys.KEY_MAINSUBTITLE, Colors.warnaAutoTitle());
        }else {
            return Colors.warnaAutoTitle();
        }
    }

    public static void menuIconColor(MenuItem item) {
        try{
            Drawable normalDrawable = item.getIcon();
            normalDrawable.setColorFilter(Colors.warnaAutoTitle(), SRC_IN);
            item.setIcon(normalDrawable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showMenu(HomeActivity a, int i){
        try{
            View mTopLayout = a.findViewById(Tools.intId("mTopLayout"));
            View mBottomLayout = a.findViewById(Tools.intId("mBottomLayout"));
            if(i == 0){
                mTopLayout.setVisibility(View.VISIBLE);
                mBottomLayout.setVisibility(View.VISIBLE);
                Themes.setStatusBar(a);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void hideMenu(HomeActivity a){
        try{
            View mTopLayout = a.findViewById(Tools.intId("mTopLayout"));
            View mBottomLayout = a.findViewById(Tools.intId("mBottomLayout"));
            mTopLayout.setVisibility(View.GONE);
            mBottomLayout.setVisibility(View.GONE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setupToolbar(a mActionBar){
        if(Prefs.getBoolean(Keys.KEY_TOOLBAR_SUBTITLE, false)){
            mActionBar.b(WaPrefs.getString("my_current_status", ""));
        }
    }

    public static String homeTitle(){
        if (Prefs.getBoolean(Keys.KEY_TOOLBAR_TITLE, false)) {
            return WaPrefs.getString("push_name", /*"WhatsApp"*/ FancyText.convertText("WhatsApp", FancyText.styleArray(3)));
        }else {
            return "WhatsApp";
        }
    }

    public static void toolbarColor(Toolbar mToolbar){
        mToolbar.setBackgroundColor(Colors.setWarnaPrimer());
    }

    //Status Recycler
    public static int statusTitle(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_STATUSTITLE_COLOR), false)){
            return Prefs.getInt(Keys.KEY_STATUSTITLE_COLOR, Themes.themedTextColor());
        }else {
            return Themes.themedTextColor();
        }
    }

    public static int statusSeenColor (){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_STATUSSEEN_COLOR), false)){
            return Prefs.getInt(Keys.KEY_STATUSSEEN_COLOR, 0xffbbbec4);
        }else {
            return 0xffbbbec4;
        }
    }

    public static int statusUnseenColor (){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_STATUSUNSEEN_COLOR), false)){
            return Prefs.getInt(Keys.KEY_STATUSUNSEEN_COLOR, Colors.setWarnaAksen());
        }else {
            return Colors.setWarnaAksen();
        }
    }


    public static int drawerTitle (){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_DRAWER_TITLE), false)){
            return Prefs.getInt(Keys.KEY_DRAWER_TITLE, Colors.warnaAutoTitle());
        }else {
            return Colors.warnaAutoTitle();
        }
    }

    public static int drawerLabel (){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_DRAWER_LABEL), false)){
            return Prefs.getInt(Keys.KEY_DRAWER_LABEL, Themes.themedTextColor());
        }else {
            return Themes.themedTextColor();
        }
    }

    public static int drawerBackground(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_DRAWER_BG), false)){
           return Prefs.getInt(Keys.KEY_DRAWER_BG, Themes.sheetBackground());
        }else {
            return Themes.sheetBackground();
        }

    }
}
