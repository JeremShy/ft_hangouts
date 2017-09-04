package fr.h3lp.jcamhi.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import static fr.h3lp.jcamhi.ft_hangouts.R.string.settings;

public class PreferencesActivity extends Activity {

    public static PreferencesActivity pref;
    public static Resources res;
    public static final String INTENT_EXTRA_CHANGED = "intent_extra_changed";

    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = this;
        res = getApplicationContext().getResources();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment  implements OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
        }
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String language = sharedPref.getString("pref_language", getString(R.string.default_language));

            Locale loc = new Locale(language);
            Locale.setDefault(loc);
            Configuration conf = getResources().getConfiguration();
            conf.locale = loc;
            getResources().updateConfiguration(conf, getResources().getDisplayMetrics());
            pref.recreate();
        }
        @Override
        public void onResume(){
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause(){
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }
    }
}