package id.delta.whatsapp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
public class SettingsActivity extends BaseActivity implements SettingsFragment.Callback, NestedFragment.NestedCallback{

    private static final String TAG_NESTED = "TAG_NESTED";
    public static boolean isRestart = false;

    public static int STOCK = 0;
    public static int DWA = 1;

    //Menu
    private static final int MENU_ABOUT = Menu.FIRST;
    private static final int MENU_RESET = Menu.FIRST + 1;
    private static final int MENU_RESTART = Menu.FIRST + 2;

    //Permission
    public boolean hasPermission = false;
    private Object[] WRITE_STORAGE;
    public static int REQ_PICK_HOME = 100;
    public static int REQ_SEND_HOME = 101;
    public static int REQ_PICK_COVER = 200;
    public static int REQ_SEND_COVER = 201;
    public static int REQ_CUSTOM_ICON = 300;
    private int REQUEST_CODE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("delta_settings_activity"));

        Toolbar mToolbar = (Toolbar)findViewById(Tools.intId("mToolbar"));
        setToolbar(mToolbar);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(Tools.intId("mContent"), new SettingsFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            if (isRestart) {
                Actions.restartApp();
            }else {
                if(Prefs.getInt("home", STOCK)==STOCK){
                    Actions.reCreate(this, HomeActivity.class);
                }else {
                    Actions.reCreate(this, MainActivity.class);
                }
            }
        } else {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onNestedPreferenceSelected(int key) {
        getFragmentManager().beginTransaction().replace(Tools.intId("mContent"), NestedFragment.newInstance(key), TAG_NESTED).addToBackStack(TAG_NESTED).commit();
    }

    @Override
    public void onSelectImage(int requestCode) {
        REQUEST_CODE = requestCode;
        if (Build.VERSION.SDK_INT < 23) {
            hasPermission = true;
            pickImage(requestCode);
        }
        requestPermission();

    }

    @Override
    public boolean onPermissionRequest() {
        REQUEST_CODE = REQ_CUSTOM_ICON;
        if (Build.VERSION.SDK_INT < 23) {
            hasPermission = true;
        }
        requestPermission();
        return false;
    }

    private void pickImage(int requestCode){
        try{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, requestCode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        menu.add(0, MENU_RESTART, Menu.NONE, Tools.intString("menu_restart")).setIcon(Tools.colorDrawable("delta_ic_restart", Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_ATOP)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, MENU_ABOUT, Menu.NONE, Tools.intString("menu_about")).setIcon(0);
        menu.add(0, MENU_RESET, Menu.NONE, Tools.intString("menu_reset")).setIcon(0);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU_ABOUT:
                break;
            case MENU_RESET:
                Prefs.clear();
                Actions.restartApp();
                break;
            case MENU_RESTART:
                Actions.restartApp();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getData() == null) {
            Tools.showToast("You haven't picked an Image");
        } else {
            if(requestCode == REQ_PICK_HOME){
               // Tools.showToast("WALLPAPER");
                startResult(GalleryWallpaperPreview.class, data.getData(), Wallpaper.homeBK_path, REQ_SEND_HOME );
            }
            if(requestCode == REQ_PICK_COVER){
               // Tools.showToast("COVER");
                startResult(CoverWallpaperPreview.class, data.getData(), Wallpaper.coverPath, REQ_SEND_COVER );
            }
            if(requestCode == REQ_SEND_HOME) {
                if (resultCode == -1) {
                    Prefs.putBoolean(Keys.KEY_HOME_WALLPAPER, Boolean.TRUE);
                    isRestart = true;
                    Tools.showToast("Wallpaper set successful");
                } else if (resultCode != 0) {
                    Tools.showToast("Something went wrong. Try again!");
                }
            }
            if(requestCode == REQ_SEND_COVER) {
                if (resultCode == -1) {
                    Prefs.putBoolean(Keys.KEY_HOME_COVER, Boolean.TRUE);
                    isRestart = true;
                    Tools.showToast("Cover set successful");
                } else if (resultCode != 0) {
                    Tools.showToast("Something went wrong. Try again!");
                }
            }

        }
    }

    private void startResult(Class target, Uri data, String pathBackground, int requestCode){
        Intent intent2 = new Intent(this, target);
        intent2.setData(data);
        intent2.putExtra("output", Uri.fromFile(new File(pathBackground)));
        startActivityForResult(intent2, requestCode);
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (Integer.parseInt(Activity.class.getMethod("checkSelfPermission", new Class[]{String.class}).invoke(this, new Object[]{"android.permission.WRITE_EXTERNAL_STORAGE"}).toString()) == 0) {
                    hasPermission = true;
                }
            } catch (Exception e) {}
            if (!hasPermission) {
                try {
                    Method q = Activity.class.getMethod("requestPermissions", new Class[]{String[].class, Integer.TYPE});
                    WRITE_STORAGE = new Object[2];
                    WRITE_STORAGE[0] = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
                    WRITE_STORAGE[1] = Integer.valueOf(74565);
                    q.invoke(this, WRITE_STORAGE);
                  //  finish();
                } catch (Exception e2) {
                }
            }else {
                if(REQUEST_CODE!=REQ_CUSTOM_ICON){
                    pickImage(REQUEST_CODE);
                }
            }
        }
    }
}
