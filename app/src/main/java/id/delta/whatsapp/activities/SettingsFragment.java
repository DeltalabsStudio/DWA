package id.delta.whatsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;

import static id.delta.whatsapp.value.Colors.setWarnaPrimer;

/**
 * Created by DELTA on 10/3/2017.
 */

public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    private Callback mCallback;

    public interface Callback {

        public void onNestedPreferenceSelected(int key);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

        if (activity instanceof Callback) {
            mCallback = (Callback) activity;
        } else {
            throw new IllegalStateException("Owner must implement URLCallback interface");
        }
    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(Prefs.PREF_NAME);
        addPreferencesFromResource(Tools.intXml("delta_main_settings"));

        Preference mHome = findPreference(Keys.KEY_PREF_HOME);
        mHome.setOnPreferenceClickListener(this);

        Preference mChat = findPreference(Keys.KEY_PREF_CHAT);
        mChat.setOnPreferenceClickListener(this);

        Preference mMedia = findPreference(Keys.KEY_PREF_MEDIA);
        mMedia.setOnPreferenceClickListener(this);

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Preference mCustomTheme = findPreference(Keys.KEY_CUSTOM);
        int theme = (Integer.parseInt((String) Prefs.getString(Keys.KEY_THEME, Keys.DEFAULT_THEME)));
        if(theme == Themes.CUSTOMTHEME){
            preferenceScreen.addPreference(mCustomTheme);
        }else {
            preferenceScreen.removePreference(mCustomTheme);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        switch (key) {
            case Keys.KEY_PRIMARY_COLOR:
                getActivity().recreate();
               // SettingsActivity.isRestart = true;
                break;
            case Keys.KEY_ACCENT_COLOR:
                getActivity().recreate();
                break;
            case Keys.KEY_THEME:
                getActivity().recreate();
                break;
            case Keys.KEY_CUSTOM:
                getActivity().recreate();
                break;
            case Keys.KEY_DISABLE_INTERNET:
                SettingsActivity.isRestart = true;
                break;
            case Keys.KEY_TRANSLUCENT:
                if(sp.getBoolean(Keys.KEY_TRANSLUCENT, false)){
                    Themes.setStatusBarView(getActivity(), Colors.alphaColor(setWarnaPrimer(), Themes.DEF_STATUSDARK));
                }else {
                    Themes.setStatusBarView(getActivity(), Colors.alphaColor(setWarnaPrimer(), Themes.DEF_STATUSTRANS));
                }
            case Keys.KEY_TINTNAVBAR:
                if(sp.getBoolean(Keys.KEY_TINTNAVBAR, false)){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getActivity().getWindow().setNavigationBarColor(setWarnaPrimer());
                        return;
                    }
                }
                break;
            case Keys.KEY_REPORT_PROBLEM:
                SettingsActivity.isRestart = true;
                break;
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case Keys.KEY_PREF_HOME:
                mCallback.onNestedPreferenceSelected(Keys.PREF_HOME);
                break;
            case Keys.KEY_PREF_CHAT:
                mCallback.onNestedPreferenceSelected(Keys.PREF_CHAT);
                break;
            case Keys.KEY_PREF_MEDIA:
                mCallback.onNestedPreferenceSelected(Keys.PREF_MEDIA);
                break;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(Tools.intLayout("delta_list_fragment"), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            ListView mList = view.findViewById(android.R.id.list);
            mList.setDivider(new ColorDrawable(Color.TRANSPARENT));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

}
