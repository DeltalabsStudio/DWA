package id.delta.whatsapp.value;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.whatsapp.AppShell;

import java.io.File;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;

public class Wallpaper {

    public static String homeBK_path;
    public static String datafolder;
    public static String coverPath;
    public static String wallpaperPath;

    public static void setDatafolder(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppShell.ctx.getFilesDir().getAbsolutePath());
        stringBuilder.append(File.separator);
        datafolder = stringBuilder.toString().replace("files/", "");
        setFilePath();
        setCoverPath();
        setWpPath();
    }

    public static void setFilePath() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(datafolder);
        stringBuilder.append("files/homeBackground.jpg");
        homeBK_path = stringBuilder.toString();
    }

    private static void setCoverPath(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(datafolder);
        stringBuilder.append("files/coverPicture.jpg");
        coverPath = stringBuilder.toString();
    }

    private static void setWpPath(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(datafolder);
        stringBuilder.append("files/wallpaper.jpg");
        wallpaperPath = stringBuilder.toString();
    }

}
