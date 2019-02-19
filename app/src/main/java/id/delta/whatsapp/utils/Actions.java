package id.delta.whatsapp.utils;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;

import com.whatsapp.AppShell;
import com.whatsapp.HomeActivity;
import com.whatsapp.plus.ReadLogFile;

import id.delta.whatsapp.activities.MainActivity;
import id.delta.whatsapp.activities.SettingsActivity;

/**
 * Created by DELTALABS on 5/15/2017.
 */

public class Actions {

    private Actions(){}

    public static String EXTRA_CONTENT_BACKGROUND = "screenshot";


    public static void reCreate(final Activity activity, final Class target){
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(new Intent(activity, target));
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                activity.finish();
            }
        }, 100L);
    }
    public static void startSettings(Activity activity, int home){
        try{
            Prefs.putInt("home", home);
            reCreate(activity, SettingsActivity.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startBrowserIntent(final String baseUrl, Activity activity) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(baseUrl));
        activity.startActivity(browserIntent);
    }

    public static void startActivity(Activity activity, Class target){
        try{
            activity.startActivity(new Intent(activity, target));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void startActivity(Context activity, Class target){
        try{
            activity.startActivity(new Intent(activity, target));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void restartApp() {
        Class class_;
        AlarmManager alarmManager = (AlarmManager) AppShell.ctx.getSystemService(Context.ALARM_SERVICE);
        long l = System.currentTimeMillis() + (long)100;
        try {
            class_ = Class.forName((String)"com.whatsapp.Main");
        }
        catch (ClassNotFoundException var4_5) {
            NoClassDefFoundError noClassDefFoundError = new NoClassDefFoundError(var4_5.getMessage());
            throw noClassDefFoundError;
        }
        Intent intent = new Intent(AppShell.ctx, class_);
        alarmManager.set(AlarmManager.RTC, l, PendingIntent.getActivity((Context) AppShell.ctx, (int)0, (Intent)intent, (int)0));
        System.exit(0);
    }

    public static void refreshApplication(Activity activity) {
        Intent app = activity.getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(activity.getBaseContext().getPackageName());
        app.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Intent current = new Intent(activity, HomeActivity.class);
        activity.finish();
        activity.startActivity(app);
        activity.startActivity(current);
    }

    public static void starReadLog(Context a) {
        try {
            Intent intent = new Intent(a, ReadLogFile.class);
            intent.setAction("android.intent.action.GET_CONTENT");
            a.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class defaultHome(){
        if(Prefs.getBoolean(Keys.KEY_DEFAUL_HOME, false)){
            return MainActivity.class;
        }else{
            return HomeActivity.class;
        }
    }

}
