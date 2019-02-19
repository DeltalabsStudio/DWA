package id.delta.whatsapp.views.preference;

import android.content.Context;
import android.os.Environment;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.CopyTask;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Wallpaper;

public class ActionPreference extends Preference {

    public ActionPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ActionPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionPreference(Context context) {
        super(context);
    }

    @Override
    protected void onClick() {
        super.onClick();
        try {
            switch (getKey()) {
                case Keys.KEY_BACKUP:
                    if (new File(Environment.getDataDirectory(), "data/"+getContext().getPackageName()).exists()) {
                        if (!new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").exists()){
                            new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").mkdir();
                        }
                        if (!new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/"+getContext().getPackageName()).exists()){
                            new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/"+getContext().getPackageName()).mkdir();
                        }
                        new CopyTask(getContext(), true, new File(Environment.getDataDirectory(), "data/"+getContext().getPackageName()), new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/"+getContext().getPackageName())).execute(new File[0]);
                    } else {
                        Toast.makeText(getContext(), "Can't find a Data!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.KEY_RESTORE:
                    if (new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").exists() && new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/"+getContext().getPackageName()).exists()) {
                        new CopyTask(getContext(), false, new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/"+getContext().getPackageName()), new File(Environment.getDataDirectory(), "data/"+getContext().getPackageName())).execute(new File[0]);
                    } else {
                        Toast.makeText(getContext(), "Can't find a backup in '/sdcard/WhatsApp/DELTABackup'!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Keys.KEY_BACKUP_THEME:
                    if (new File(Environment.getDataDirectory(), "data/"+getContext().getPackageName()).exists()) {
                        if (!new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").exists()){
                            new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup").mkdir();
                        }
                        if (!new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes").exists()){
                            new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes").mkdir();
                        }
                        saveThemeHomeWp(getContext());
                        saveThemeCoverWp(getContext());
                        saveThemeWp(getContext());
                        new CopyTask(getContext(), true, new File(Environment.getDataDirectory(), "data/"+getContext().getPackageName()+"/shared_prefs/"+Prefs.PREF_NAME+".xml"), new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/"+Prefs.PREF_NAME+".xml")).execute(new File[0]);
                    } else {
                        Toast.makeText(getContext(), "Can't find a preferences!", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case Keys.KEY_RESTORE_THEME:
                    loadThemeHomeWp(getContext());
                    loadThemeCoverWp(getContext());
                    loadThemeWp(getContext());
                    loadThemeXml(getContext());
                    break;
                case Keys.KEY_RESET:
                    Prefs.clear();
                    Actions.restartApp();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveThemeHomeWp(Context ctx) {
        if (new File(Wallpaper.homeBK_path).exists()) {
            new CopyTask(ctx, true, new File(Wallpaper.homeBK_path), new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/homeBackground.jpg")).execute(new File[0]);
        } else {
            Log.d("info", "Can't find a homeBackground.jpg!");
           // Toast.makeText(ctx, "Can't find a homeBackground.jpg!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveThemeCoverWp(Context ctx) {
        if (new File(Wallpaper.coverPath).exists()) {
            new CopyTask(ctx, true, new File(Wallpaper.coverPath), new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/coverPicture.jpg")).execute(new File[0]);
        }
        else {
            Log.d("info", "Can't find a coverPicture.jpg!");
           // Toast.makeText(ctx, "Can't find a coverPicture.jpg!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void saveThemeWp(Context ctx) {
        if (new File(Wallpaper.wallpaperPath).exists()) {
            new CopyTask(ctx, true, new File(Wallpaper.wallpaperPath), new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/wallpaper.jpg")).execute(new File[0]);
        } else {
            Log.d("info", "Can't find a wallpaper.jpg!");
           // Toast.makeText(ctx, "Can't find a wallpaper.jpg!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void loadThemeXml(Context ctx) {
        if (new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes").exists() && new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/"+Prefs.PREF_NAME+".xml").exists()) {
            new CopyTask(ctx, false, new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/"+Prefs.PREF_NAME+".xml"), new File(Environment.getDataDirectory(), "data/"+ctx.getPackageName()+"/shared_prefs/"+Prefs.PREF_NAME+".xml")).execute(new File[0]);
        } else {
            Toast.makeText(ctx, "Can't find a backup in '/sdcard/WhatsApp/DELTABackup/Themes'!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void loadThemeHomeWp(Context ctx) {
        if (new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes").exists() && new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/homeBackground.jpg").exists()) {
            new CopyTask(ctx, false, new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/homeBackground.jpg"), new File(Wallpaper.homeBK_path)).execute(new File[0]);
        } else {
            Toast.makeText(ctx, "Can't find a backup in '/sdcard/WhatsApp/DELTABackup/Themes/homeBackground.jpg'!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void loadThemeCoverWp(Context ctx) {
        if (new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes").exists() && new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/coverPicture.jpg").exists()) {
            new CopyTask(ctx, false, new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/coverPicture.jpg"), new File(Wallpaper.coverPath)).execute(new File[0]);
        } else {
            Toast.makeText(ctx, "Can't find a backup in '/sdcard/WhatsApp/DELTABackup/Themes/coverPicture.jpg'!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void loadThemeWp(Context ctx) {
        if (new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes").exists() && new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/wallpaper.jpg").exists()) {
            new CopyTask(ctx, false, new File(Environment.getExternalStorageDirectory(), "WhatsApp/DELTABackup/Themes/wallpaper.jpg"), new File(Wallpaper.wallpaperPath)).execute(new File[0]);
        } else {
            Toast.makeText(ctx, "Can't find a backup in '/sdcard/WhatsApp/DELTABackup/Themes/wallpaper.jpg'!", Toast.LENGTH_SHORT).show();
        }
    }

}
