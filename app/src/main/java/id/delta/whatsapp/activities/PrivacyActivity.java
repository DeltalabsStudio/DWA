package id.delta.whatsapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.whatsapp.DialogToastActivity;
import com.whatsapp.HomeActivity;
import com.whatsapp.wallpaper.CoverWallpaperPreview;
import com.whatsapp.wallpaper.GalleryWallpaperPreview;

import java.io.File;
import java.lang.reflect.Method;

import id.delta.whatsapp.implement.ToolbarInterfaces;
import id.delta.whatsapp.utils.Actions;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;
import id.delta.whatsapp.value.Wallpaper;

/**
 * Created by DELTA on 10/2/2017.
 */

@SuppressLint("Registered")
public class PrivacyActivity extends BaseActivity{

    public static boolean isRestart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_settings_activity"));

        Toolbar mToolbar = (Toolbar)findViewById(Tools.intId("mToolbar"));
        setToolbar(mToolbar);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(Tools.intId("mContent"), new PrivacyFragment())
                    .commit();
        }
    }

    public void onBackPressed() {
        if (isRestart) {
            Process.killProcess(Process.myPid());
        }else {
            super.onBackPressed();
        }
    }


}
