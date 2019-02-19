package id.delta.whatsapp.value;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.whatsapp.MentionableEntry;
import com.whatsapp.TextStatusComposerActivity;

import id.delta.whatsapp.dialog.DialogDnd;
import id.delta.whatsapp.dialog.DialogStyle;
import id.delta.whatsapp.utils.Tools;

import static android.graphics.PorterDuff.Mode.SRC_IN;

public class FancyText {

    public static final int FANCY = 0;
    public static final int JUNGKEL = 1;
    public static final int SQUARE = 2;
    public static final int BLACKBUBBLE = 3;
    public static final int TEXTBUBBLE= 4;
    public static final int  BIG = 5;
    public static final int NORMAL = 6;
    public static final int WHITEBRACKET = 7;
    public static final int BLACKBRACKET = 8;

    private static String [] charStr = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
    private static String [] whiteBracket = {"『a』","『b』","『c』","『d』","『e』","『f』","『g』","『h』","『i』","『j』","『k』","『l』","『m』","『n』","『o』","『p』","『q』","『r』","『s』","『t』","『u』","『v』","『w』","『x』","『y』","『z』","『1』","『2』","『3』","『4』","『5』","『6』","『7』","『8』","『9』","『0』"};
    private static String [] blackBracket = {"【a】","【b】","【c】","【d】","【e】","【f】","【g】","【h】","【i】","【j】","【k】","【l】","【m】","【n】","【o】","【p】","【q】","【r】","【s】","【t】","【u】","【v】","【w】","【x】","【y】","【z】","【1】","【2】","【3】","【4】","【5】","【6】","【7】","【8】","【9】","【0】"};
    private static String [] fancyStyle = {"Ꮧ","Ᏸ","ፈ","Ꮄ","Ꮛ","Ꭶ","Ꮆ","Ꮒ","Ꭵ","Ꮰ","Ꮶ","Ꮭ","Ꮇ","Ꮑ","Ꭷ","Ꭾ","Ꭴ","Ꮢ","Ꮥ","Ꮦ","Ꮼ","Ꮙ","Ꮗ","ጀ","Ꭹ","ፚ"};
    private static String [] jungkelText = {"ɐ","b","ɔ","d","ǝ","ɟ","ƃ","ɥ","!","ɾ","ʞ","ן","ɯ","n","o","d","b","ɹ","s","ʇ","n","ʌ","ʍ","x","ʎ","z"};
    private static String [] squareText = {	"\uD83C\uDD30","\uD83C\uDD31","\uD83C\uDD32","\uD83C\uDD33","\uD83C\uDD34","\uD83C\uDD35","\uD83C\uDD36","\uD83C\uDD37","\uD83C\uDD38","\uD83C\uDD39","\uD83C\uDD3A","\uD83C\uDD3B","\uD83C\uDD3C","\uD83C\uDD3D","\uD83C\uDD3E","\uD83C\uDD3F","\uD83C\uDD40","\uD83C\uDD41","\uD83C\uDD42","\uD83C\uDD43","\uD83C\uDD44","\uD83C\uDD45","\uD83C\uDD46","\uD83C\uDD47","\uD83C\uDD48","\uD83C\uDD49"};
    private static String [] bubbleBlack = {"🅐","🅑","🅒","🅓","🅔","🅕","🅖","🅗","🅘","🅙","🅚","🅛","🅜","🅝","🅞","🅟","🅠","🅡","🅢","🅣","🅤","🅥","🅦","🅧","🅨","🅩"};
    private static String [] bubbleText = {"ⓐ", "ⓑ", "ⓒ","ⓓ","ⓔ","ⓕ","ⓖ","ⓗ","ⓘ","ⓙ","ⓚ","ⓛ","ⓜ","ⓝ","ⓞ","ⓟ","ⓠ","ⓡ","ⓢ","ⓣ","ⓤ","ⓥ","ⓦ","ⓧ","ⓨ","ⓩ"};
    private static String [] bigText = { "\uD83C\uDDE6\u200B", "\uD83C\uDDE7\u200B", "\uD83C\uDDE8\u200B", "\uD83C\uDDE9\u200B", "\uD83C\uDDEA\u200B", "\uD83C\uDDEB\u200B", "\uD83C\uDDEC\u200B", "\uD83C\uDDED\u200B", "\uD83C\uDDEE\u200B",
            "\uD83C\uDDEF\u200B", "\uD83C\uDDF0\u200B", "\uD83C\uDDF1\u200B", "\uD83C\uDDF2\u200B", "\uD83C\uDDF3\u200B", "\uD83C\uDDF4\u200B", "\uD83C\uDDF5\u200B", "\uD83C\uDDF6\u200B", "\uD83C\uDDF7\u200B", "\uD83C\uDDF8\u200B",
            "\uD83C\uDDF9\u200B", "\uD83C\uDDFa\u200B", "\uD83C\uDDFb\u200B", "\uD83C\uDDFc\u200B", "\uD83C\uDDFd\u200B", "\uD83C\uDDFe\u200B", "\uD83C\uDDFf\u200B"};


    private static String convertChart(char c, String[] style) {
        char uChar = Character.toUpperCase(c);
        if (uChar >= 'A' && uChar <= 'Z') {
            return style[uChar - 65];
        }

        if (c == ' ') {
            return "    ";
        }
        return String.valueOf(c);
    }
    
    public static String convertText(String text, String[] style) {
        if (text.isEmpty()) {
            return "";
        }
        char[] arrChar = text.toCharArray();
        String s = "";
        for (int i = 0; i < text.length(); i++) {
            s = new StringBuilder(String.valueOf(s)).append(convertChart(arrChar[i], style)).toString();
        }
        return s;
    }

    public static String normalText(String text, String[] style) {
        if (text.isEmpty()) {
            return "";
        }
        String str = text;
        for (int i = 0; i < charStr.length; i++) {
            str = str.replaceAll(style[i], charStr[i]).replaceAll("    ", " ");
        }
        return str;
    }

    public static String [] styleArray(int style) {
        String [] styleText = {""};
        switch (style){
            case FANCY:
                styleText = fancyStyle;
                break;
            case JUNGKEL:
                styleText = jungkelText;
                break;
            case SQUARE:
                styleText = squareText;
                break;
            case BLACKBUBBLE:
                styleText = bubbleBlack;
                break;
            case TEXTBUBBLE:
                styleText = bubbleText;
                break;
            case BIG:
                styleText = bigText;
                break;
            case WHITEBRACKET:
                styleText = whiteBracket;
                break;
            case BLACKBRACKET:
                styleText = blackBracket;
                break;
        }

        return styleText;
    }


    public static void addMenu(Menu menu){
        try{
            menu.add(0, Conversation.BIGMENU, Menu.NONE, Tools.intString("menu_fancytext")).setIcon(fancyIcon()).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static Drawable fancyIcon(){
        return Tools.colorDrawable("delta_ic_fancytext", Colors.warnaAutoTitle(), SRC_IN);
    }

    public static void onMenuClicked(final com.whatsapp.Conversation activity, MenuItem item){
        try{
            if(item.getItemId()== Conversation.BIGMENU){
                final MentionableEntry mEntry = activity.findViewById(Tools.intId("entry"));
                if(activity.isStyle){
                    activity.isStyle = false;
                    mEntry.setText(normalText(mEntry.getText().toString(),styleArray( activity.style)));
                }else {
                    if(!mEntry.getText().toString().isEmpty()){
                        DialogStyle dialogStyle = new DialogStyle(activity, mEntry.getText().toString(), new DialogStyle.StyleListener() {
                            @Override
                            public void onStyleSelected(int style, String value) {
                                mEntry.setText(value);
                                activity.style = style;
                                if(style!=NORMAL){
                                    activity.isStyle = true;
                                }
                            }
                        });
                        dialogStyle.show();
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void onStatusClicked(final TextStatusComposerActivity activity){
        try{
            final EditText mEntry = activity.findViewById(Tools.intId("entry"));
            if(activity.isStyle){
                activity.isStyle = false;
                mEntry.setText(normalText(mEntry.getText().toString(),styleArray( activity.style)));
            }else {
                if(!mEntry.getText().toString().isEmpty()){
                    DialogStyle dialogStyle = new DialogStyle(activity, mEntry.getText().toString(), new DialogStyle.StyleListener() {
                        @Override
                        public void onStyleSelected(int style, String value) {
                            mEntry.setText(value);
                            activity.style = style;
                            if(style!=NORMAL){
                                activity.isStyle = true;
                            }
                        }
                    });
                    dialogStyle.show();
                }
            }
    }catch (Exception e){}
}
}

