package id.delta.whatsapp.value;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;

import com.whatsapp.DialogToastActivity;

import java.util.ArrayList;

import id.delta.whatsapp.R;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
import static id.delta.whatsapp.utils.Tools.intStyle;

/**
 * Created by DELTA on 10/3/2017.
 */

public class Themes {

    private Themes(){}
    public static int DEF_STATUSDARK = 40;
    public static int DEF_STATUSTRANS = 0;

    public static void setStatusBar(Activity activity){
        setStatusBarView(activity, Colors.alphaColor(Colors.setWarnaPrimer(), Themes.setStatusBarAlpha()));
    }

    public static void setHomeNavigationBar(Activity activity){
        View mNavigationBar = activity.findViewById(Tools.intId("navigation_bar_protection"));
       // if(Prefs.getBoolean(Keys.KEY_TINTNAVBAR, false))
            mNavigationBar.setBackgroundColor(Colors.setWarnaPrimer());
    }

    public static void setStatusBarView(Activity activity, int color){
        try{
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = activity.getWindow();
                window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(color);
                if(Prefs.getBoolean(Keys.KEY_TINTNAVBAR, false))window.setNavigationBarColor(Colors.setWarnaPrimer());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(!Colors.isDarken(color)) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if(!Colors.isDarken(color)) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int setStatusBarAlpha(){
        if(Prefs.getBoolean(Keys.KEY_TRANSLUCENT, true)){
            return DEF_STATUSDARK;
        }else {
            return DEF_STATUSTRANS;
        }
    }

    public static final int LIGHTTHEME = 0;
    public static final int DARKTHEME = 1;
    public static final int TRANSTHEME = 2;
    public static final int CUSTOMTHEME = 3;


    public static int customBackground(){
        return Prefs.getInt(Keys.KEY_CUSTOM, Colors.warnaNightBackground);
    }

    public static int sheetBackground(){
        int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
        if (n == LIGHTTHEME) {
            return Colors.warnaLightBackground;
        }else {
            return Colors.warnaNightBackground;
        }
    }

    public static int windowBackground(){
        int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
        if (n == LIGHTTHEME) {
            return Colors.warnaLightBackground;
        }
        if (n == DARKTHEME) {
            return Colors.warnaNightBackground;
        }
        if (n == TRANSTHEME) {
            return Colors.warnaHitam50;
        }
        if (n == CUSTOMTHEME){
            return customBackground();
        }
        return n;
    }


    public static int themedTextColor(){
        int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
        if (n == LIGHTTHEME) {
            return Colors.warnaTitle;
        }else {
            return Colors.warnaPutih;
        }
    }

    public static int secondTextColor(){
        int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
        if (n == LIGHTTHEME) {
            return Colors.warnaHitam50;
        }else {
            return Colors.warnaPutih50;
        }
    }

    public static void setHomeTheme(Activity activity){
        try {
            int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
            switch (n){
                case LIGHTTHEME:
                    activity.setTheme(intStyle("Theme.App.Home"));
                    break;
                case DARKTHEME:
                    activity.setTheme(intStyle("Theme.App.Home.Dark"));
                    break;
                case TRANSTHEME:
                    activity.setTheme(intStyle("Theme.App.Home.Trans"));
                    break;
                case CUSTOMTHEME:
                    activity.setTheme(intStyle("Theme.App.Home.Dark"));
                    activity.getWindow().setBackgroundDrawable(new ColorDrawable(windowBackground()));
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setAppTheme(Activity activity){
        try {
            int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
            switch (n){
                case LIGHTTHEME:
                    activity.setTheme(intStyle("Theme.App"));
                    break;
                case DARKTHEME:
                    activity.setTheme(intStyle("Theme.App.Dark"));
                   // activity.getWindow().setBackgroundDrawable(new ColorDrawable(windowBackground()));
                    break;
                case TRANSTHEME:
                    activity.setTheme(intStyle("Theme.App.Trans"));
                    break;
                case CUSTOMTHEME:
                    activity.setTheme(intStyle("Theme.App.Dark"));
                    activity.getWindow().setBackgroundDrawable(new ColorDrawable(windowBackground()));
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setChatTheme(Activity activity){
        try {
            int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
            switch (n){
                case LIGHTTHEME:
                    activity.setTheme(intStyle("Theme.App.CondensedActionBar"));
                    break;
                case DARKTHEME:
                    activity.setTheme(intStyle("Theme.App.CondensedActionBar.Dark"));
                    break;
                case TRANSTHEME:
                    activity.setTheme(intStyle("Theme.App.CondensedActionBar.Trans"));
                    break;
                case CUSTOMTHEME:
                    activity.setTheme(intStyle("Theme.App.CondensedActionBar.Dark"));
                    activity.getWindow().setBackgroundDrawable(new ColorDrawable(windowBackground()));
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
