package id.delta.whatsapp.value;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;

import com.whatsapp.AppShell;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;


/**
 * Created by DELTALABS on 7/16/2017.
 */

public class Avatar {

    public static float avatarRadius(float def){
        return (float)Prefs.getInt(Keys.KEY_AVATAR_ROUNDED, -1);
    }

    public static float avatarBorderSize(float def){
        return (float)Prefs.getInt(Keys.KEY_AVATAR_BORDER_SIZE, -1);
    }

    public static int avatarBorderColor (){
        return Prefs.getInt(Keys.KEY_AVATAR_BORDER_COLOR, Colors.warnaPutih);
    }

    public static int leftTop(){
        return Prefs.getInt(Keys.KEY_LTOP, 10);
    }

    public static int rightTop(){
        return Prefs.getInt(Keys.KEY_RTOP, 10);
    }

    public static int leftBottom(){
        return Prefs.getInt(Keys.KEY_LBOT, 10);
    }

    public static int rightBottom(){
        return Prefs.getInt(Keys.KEY_RBOT, 10);
    }

    public static int lebarBorder(){
        return Prefs.getInt(Keys.KEY_WEIGHTBORDER, Tools.dpToPx(AppShell.ctx, 1));
    }

    public static int warnaBorder(){
        return Prefs.getInt(Keys.KEY_COLORBORDER, Colors.warnaPutih);
    }

    public static boolean setCircleView(){
        return Prefs.getBoolean(Keys.KEY_CIRCLE, true);
    }

    public static void grayScaleMode(ImageView imageView){
        if(Prefs.getBoolean(Keys.KEY_GRAYSCALE, false)) setGrayScale(imageView);
    }

    @SuppressWarnings("deprecation")
    public static void setGrayScale(ImageView image){
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  				//0 means grayscale
        ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(matrix);
        image.setColorFilter(colorFilter);
        image.setAlpha(255);
    }


}
