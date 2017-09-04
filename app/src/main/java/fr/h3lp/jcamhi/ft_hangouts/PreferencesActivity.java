package fr.h3lp.jcamhi.ft_hangouts;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

import static fr.h3lp.jcamhi.ft_hangouts.R.string.settings;

public class PreferencesActivity extends AppCompatActivity {

    public static PreferencesActivity pref;
    public static Resources res;
    public static final String INTENT_EXTRA_CHANGED = "intent_extra_changed";

    protected void  onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pref_with_toolbar);

        pref = this;
        res = getApplicationContext().getResources();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_pref);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.settings));

        getFragmentManager().beginTransaction().replace(R.id.content_frame_pref, new SettingsFragment()).commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class SettingsFragment extends PreferenceFragment  implements OnSharedPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);
            Preference languagePref = findPreference("pref_language");
            ListPreference lp = (ListPreference) findPreference("pref_language");
            languagePref.setSummary(lp.getEntry());
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

            Preference languagePref = findPreference("pref_language");
            ListPreference lp = (ListPreference) findPreference("pref_language");
            languagePref.setSummary(lp.getEntry());
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