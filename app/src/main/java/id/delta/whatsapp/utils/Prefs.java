package id.delta.whatsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.whatsapp.AppShell;

/**
 * Created by DELTALABS on 6/18/2017.
 */

public class Prefs {

    public static String PREF_NAME = "id.delta.whatsapp";

    public static SharedPreferences getPreferences() {
      //  return PreferenceManager.getDefaultSharedPreferences(AppShell.ctx);
        return AppShell.ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static SharedPreferences.Editor getEditor() {
        return getPreferences().edit();
    }

    public static void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public static float getFloat(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    public static void putString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public static void putInt(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

    public static void remove(String key) {
        getEditor().remove(key).apply();
    }

    public static void clear() {
        getEditor().clear().commit();
    }
}
