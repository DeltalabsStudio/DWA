package id.delta.whatsapp.value;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.a;
import android.support.v7.widget.ao;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.whatsapp.MentionableEntry;
import com.whatsapp.WaImageButton;
import com.whatsapp.wallpaper.WallPaperView;

import id.delta.whatsapp.activities.SettingsActivity;
import id.delta.whatsapp.dialog.DialogDnd;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

import static android.graphics.PorterDuff.Mode.SRC_IN;
import static id.delta.whatsapp.utils.Tools.intStyle;
import static id.delta.whatsapp.value.Themes.customBackground;

public class Conversation {

    public static int BIGMENU = 100;

    public static void setInputBackground(Drawable drawable){
        try{
            drawable.setColorFilter(inputBackground(), PorterDuff.Mode.MULTIPLY);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setEntryTextColor(MentionableEntry mentionableEntry){
        try {
            if(Colors.isDarken(inputBackground())){
                mentionableEntry.setTextColor(Colors.warnaPutih);
            }else {
                mentionableEntry.setTextColor(Colors.warnaTitle);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int themedInput(){
        int n = Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME));
        if (n == Themes.LIGHTTHEME) {
            return Colors.warnaPutih;
        }
        if (n == Themes.DARKTHEME) {
            return Colors.warnaDarkBackground;
        }
        if (n == Themes.TRANSTHEME) {
            return Colors.warnaHitam50;
        }
        if (n == Themes.CUSTOMTHEME){
            return customBackground();
        }
        return n;
    }

    public static int disableTheme(){
        if(Prefs.getBoolean(Keys.KEY_INPUT_DISABLE, true)){
            return themedInput();
        }else {
            return Colors.warnaPutih;
        }
    }

    public static int inputBackground(){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_INPUT_BACKGROUND), false)){
            return Prefs.getInt(Keys.KEY_INPUT_BACKGROUND, disableTheme());
        }else {
            return disableTheme();
        }
    }

    public static void titleColor(TextView mTextView){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_CHAT_TITLE_COLOR), false)){
            mTextView.setTextColor(Prefs.getInt(Keys.KEY_CHAT_TITLE_COLOR, Colors.warnaAutoTitle()));
        }else {
            mTextView.setTextColor(Colors.warnaAutoTitle());
        }
    }

    public static void subtitleColor(TextView mTextView){
        if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_CHAT_SUBTITLE_COLOR), false)){
            mTextView.setTextColor(Prefs.getInt(Keys.KEY_CHAT_SUBTITLE_COLOR, Colors.warnaAutoSubtitle()));
        }else {
            mTextView.setTextColor(Colors.warnaAutoSubtitle());
        }
    }

    public static void iconBackColor(ViewGroup viewGroup){
        try{
            ImageView mBack = viewGroup.findViewById(Tools.intId("mBack"));
            if (mBack != null) {
                Drawable d = mBack.getDrawable();
                if (d != null) {
                    if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_CHAT_BACK_COLOR), false)){
                        d.setColorFilter(Prefs.getInt(Keys.KEY_CHAT_BACK_COLOR, Colors.warnaAutoTitle()), PorterDuff.Mode.SRC_IN);
                    }else {
                        d.setColorFilter(Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_IN);
                    }
                    mBack.setImageDrawable(d);
                }
            }

        }catch (Exception e){}

    }

    public static void iconOverflow(final ViewGroup vg){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    ao mIcon;
                    if (vg.getChildAt(1) instanceof ao) {
                        mIcon = (ao) vg.getChildAt(1);
                    } else {
                        mIcon = (ao) vg.getChildAt(2);
                    }

                    if ((vg.getChildAt(0) instanceof TextView) && (Prefs.getBoolean(Keys.CHECK(Keys.KEY_CHAT_TITLE_COLOR), false))) {
                        ((TextView) vg.getChildAt(0)).setTextColor(Prefs.getInt(Keys.KEY_CHAT_TITLE_COLOR, Colors.warnaAutoTitle()));
                    }
                    for (int i = 0; i < mIcon.getChildCount(); i++) {
                        if (mIcon.getChildAt(i) instanceof ImageView) {
                            ImageView img = (ImageView) mIcon.getChildAt(i);
                            if(Prefs.getBoolean(Keys.CHECK(Keys.KEY_CHAT_BACK_COLOR), false)){
                                img.setColorFilter(Prefs.getInt(Keys.KEY_CHAT_BACK_COLOR, Colors.warnaAutoTitle()), PorterDuff.Mode.SRC_IN);
                            }else {
                                img.setColorFilter(Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_IN);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, 0);
    }

    public static void actionBarBackground(a mActionBar){
        mActionBar.a(new ColorDrawable(Colors.setWarnaPrimer()));
    }

}
