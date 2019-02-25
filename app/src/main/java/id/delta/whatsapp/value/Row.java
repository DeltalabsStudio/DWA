package id.delta.whatsapp.value;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

public class Row {

    public static Drawable listDivider(){
        if(Prefs.getBoolean(Keys.KEY_DISABLE_DIVIDER, false)){
            return new ColorDrawable(Color.TRANSPARENT);
        }else {
            return Tools.getDrawable("conversations_list_divider");
        }
    }

    public static int conversationsRow(){
        if(Prefs.getBoolean(Keys.KEY_ENABLE_CARD, false)){
            return Tools.intLayout("conversations_row_card");
        }else {
            return Tools.intLayout("conversations_row");
        }
    }

    public static int cardColor(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_CARD_BACKGROUND), false)){
            return Prefs.getInt(Keys.KEY_CARD_BACKGROUND, 0xff9e9e9e);
        }else {
            return 0xff9e9e9e;
        }
    }

    public static int listDate(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_ROW_DATE), false)){
            return Prefs.getInt(Keys.KEY_ROW_DATE, Themes.secondTextColor());
        }else {
            return Themes.secondTextColor();
        }
    }

    public static void listDate(TextView textView){
        textView.setTextColor(listDate());
    }

    public static int listName(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_ROW_CONTACTNAME), false)){
           return Prefs.getInt(Keys.KEY_ROW_CONTACTNAME, Themes.themedTextColor());
        }else {
            return Themes.themedTextColor();
        }
    }

    public static void listName(TextView textView){
        textView.setTextColor(listName());
    }

    public static int listMessage(){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_ROW_MESSAGE), false)){
            return Prefs.getInt(Keys.KEY_ROW_MESSAGE, Themes.secondTextColor());
        }else {
            return Themes.secondTextColor();
        }
    }

    public static void listMessage(TextView textView){
        textView.setTextColor(listMessage());
    }

    public static void listSingleMessage(TextView textView){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_ROW_SINGLEMESSAGE), false)){
            textView.setTextColor(Prefs.getInt(Keys.KEY_ROW_SINGLEMESSAGE, Themes.secondTextColor()));
        }
    }

    public static void listArchive(TextView textView){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_ROW_ARCHIVE), false)){
            textView.setTextColor(Prefs.getInt(Keys.KEY_ROW_ARCHIVE, Themes.themedTextColor()));
            Drawable bg = textView.getBackground();
            bg.setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_IN);
            textView.setBackground(bg);
        }
    }

    public static void listBadge(TextView textView){
        if(Prefs.getBoolean(Tools.CHECK(Keys.KEY_ROW_BADGE), false)){
           // textView.setTextColor(Prefs.getInt(Keys.KEY_ROW_BADGE, Colors.setWarnaAksen()));
            Drawable bg = textView.getBackground();
            bg.setColorFilter(Colors.setWarnaAksen(), PorterDuff.Mode.SRC_IN);
            textView.setBackground(bg);
        }
    }

}
