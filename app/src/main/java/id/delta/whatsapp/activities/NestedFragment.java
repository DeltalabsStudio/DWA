package id.delta.whatsapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.utils.WaPrefs;
import id.delta.whatsapp.views.preference.SeekBarPreference;


/**
 * Created by DELTA on 10/3/2017.
 */

public class NestedFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG_KEY = "NESTED_KEY";
    private NestedCallback mCallback;

    public static NestedFragment newInstance(int key){
        NestedFragment fragment = new NestedFragment();
        Bundle args = new Bundle();
        args.putInt(TAG_KEY, key);
        fragment.setArguments(args);
        return fragment;
    }

    public interface NestedCallback {
        public void onSelectImage(int requestCode);

        public boolean onPermissionRequest();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NestedCallback) {
            mCallback = (NestedCallback) activity;
        } else {
            throw new IllegalStateException("Owner must implement NestedCallback interface");
        }
    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(Prefs.PREF_NAME);
        prefResource();
    }

    private void prefResource(){
        int key = getArguments().getInt(TAG_KEY);
        switch (key) {
            case Keys.PREF_HOME:
                addPreferencesFromResource(Tools.intXml("delta_home_settings"));

                Preference mWallpaper = findPreference(Keys.KEY_PREF_WALLPAPER);
                mWallpaper.setOnPreferenceClickListener(this);

                Preference mCover = findPreference(Keys.KEY_PREF_COVER);
                mCover.setOnPreferenceClickListener(this);

                break;

            case Keys.PREF_CHAT:
                addPreferencesFromResource(Tools.intXml("delta_chat_settings"));
                break;

            case Keys.PREF_MEDIA:
                addPreferencesFromResource(Tools.intXml("delta_media_settings"));
                break;


        }
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
    public void onSharedPreferenceChanged(SharedPreferences sPrefs, String key) {
        switch (key){
            case Keys.KEY_IMAGE_RESOLUTION:
                int value = sPrefs.getInt(Keys.KEY_IMAGE_RESOLUTION, 1);
                if(value == 0){
                    Prefs.putInt(Keys.KEY_IMAGE_RESOLUTION, 1);
                    value = 1;
                }
                int i = value > 1 ? 100 : 80;
                value *= 1024;

                //Chat
                WaPrefs.putInt("image_max_edge", value);
                WaPrefs.putInt("image_max_kbytes", value);
                WaPrefs.putInt("image_quality", i);

                //Status
                WaPrefs.putInt("status_image_quality", i);
                WaPrefs.putInt("status_image_max_edge", value);

                SettingsActivity.isRestart = true;

                break;

            case Keys.KEY_FIVE_MINUTES_STATUS:
                if(sPrefs.getBoolean(Keys.KEY_FIVE_MINUTES_STATUS, false)){
                    WaPrefs.putInt("status_video_max_duration", 300);
                }else {
                    WaPrefs.remove("status_video_max_duration");
                }

                SettingsActivity.isRestart = true;

                break;

            case Keys.KEY_CUSTOM_ICON:
                if(sPrefs.getBoolean(Keys.KEY_CUSTOM_ICON, false)){
                    mCallback.onPermissionRequest();
                }
                break;
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



    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){
            case Keys.KEY_PREF_WALLPAPER:
                mCallback.onSelectImage(SettingsActivity.REQ_PICK_HOME);
                break;
            case Keys.KEY_PREF_COVER:
                mCallback.onSelectImage(SettingsActivity.REQ_PICK_COVER);
                break;
        }
        return false;
    }


}
