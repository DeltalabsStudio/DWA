package id.delta.whatsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.whatsapp.AppShell;

/**
 * Created by DELTALABS on 6/18/2017.
 */

public class SPriv {

    public static String PREF_NAME = "BegalDevPrivacy";

    public static SharedPreferences getPreferences() {
        return AppShell.ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static boolean getBoolean(String key) {
        return getPreferences().getBoolean(key, false);
    }

}
