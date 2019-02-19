package id.delta.whatsapp.value;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;


/**
 * Created by DELTA on 10/3/2017.
 */

public class Colors {
    public static final int LIGHTTHEME = 0;
    public static final int DARKTHEME = 1;
    public static final int TRANSTHEME = 2;
    public static final int CUSTOMTHEME = 3;

    public static int primaryColor = Tools.getColor("delta_primarycolor");
    public static int accentColor = Tools.getColor("delta_accentcolor");
    public static int warnaPutih = Tools.getColor("delta_white");
    public static int warnaHitam = Tools.getColor("delta_black");
    public static int warnaTitle = Tools.getColor("delta_darkicon");
    public static int warnaPutih50 = Tools.getColor("delta_white50");
    public static int warnaHitam50 = Tools.getColor("delta_black50");
    public static int warnaOutBubble = Tools.getColor("delta_outcolor");
    public static int warnaPress = Tools.getColor("delta_pressed");
    public static int warnaLightBackground = Tools.getColor("delta_lightbg");
    public static int warnaDarkBackground = Tools.getColor("delta_darkbg");
    public static int warnaNightBackground = Tools.getColor("twitter_night");

    public static int alphaColor (int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    public static boolean isDarken(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return false; // It's a light color
        } else {
            return true; // It's a dark color
        }
    }

    public static int setWarnaPrimer (){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_PRIMARY_COLOR), false)){
            return Prefs.getInt(Keys.KEY_PRIMARY_COLOR, Colors.primaryColor);
        }else {
            return Colors.primaryColor;
        }
    }

    public static int setWarnaAksen (){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_ACCENT_COLOR), false)){
            return Prefs.getInt(Keys.KEY_ACCENT_COLOR, Colors.accentColor);
        }else {
            return Colors.accentColor;
        }
    }

    public static int warnaAutoTitle (){
        if(isDarken(setWarnaPrimer())){
            return warnaPutih;
        }else {
            return warnaTitle;
        }
    }

    public static int warnaAutoSubtitle(){
        if(isDarken(setWarnaPrimer())){
            return warnaPutih50;
        }else {
            return warnaHitam50;
        }
    }

    private static int warnaNavigation(int active){
        if(active==1){
            return Colors.warnaAutoTitle();
        }else {
            return Colors.warnaAutoTitle();
        }
    }

    public static int naviconColor(int active){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_NAVICON_COLOR), false)){
            return Prefs.getInt(Keys.KEY_NAVICON_COLOR, warnaNavigation(active));
        }else {
            return warnaNavigation(active);
        }
    }

    public static int themedTextColor(){
        int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
        if (n == LIGHTTHEME) {
            return Colors.warnaTitle;
        }else {
            return Colors.warnaPutih;
        }
    }

    public static int warnaFab(){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_FABCOLOR), false)){
            return Prefs.getInt(Keys.KEY_FABCOLOR, setWarnaAksen());
        }else {
            return setWarnaAksen();
        }
    }

    public static int warnaFabIcon(){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_FABICON), false)){
            return Prefs.getInt(Keys.KEY_FABICON, warnaAutoIconFab());
        }else {
            return warnaAutoIconFab();
        }
    }

    public static int warnaAutoIconFab (){
        if(Colors.isDarken(warnaFab())){
            return Colors.warnaPutih;
        }else {
            return Colors.warnaTitle;
        }
    }

    public static void setupActionBar(AppCompatActivity activity){
        try{
            ActionBar mActionBar = activity.getSupportActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(setWarnaPrimer()));
            CharSequence title = mActionBar.getTitle();
            mActionBar.setTitle(getActionBarTitle(title));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static SpannableString getActionBarTitle(CharSequence title){
        SpannableString s1 = new SpannableString(title);
        s1.setSpan(new ForegroundColorSpan(Colors.warnaAutoTitle()), 0, s1.length(), 0);
        return s1;
    }
}
