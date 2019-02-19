package id.delta.whatsapp.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.utils.SPriv;
import id.delta.whatsapp.utils.Tools;

/**
 * Created by DELTA on 10/3/2017.
 */

public class PrivacyFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, SharedPreferences.OnSharedPreferenceChangeListener{

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        PreferenceManager manager = getPreferenceManager();
        manager.setSharedPreferencesName(SPriv.PREF_NAME);
        addPreferencesFromResource(Tools.intXml("delta_privacy_settings"));

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if(sp!=null){
            PrivacyActivity.isRestart = true;
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()){

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
