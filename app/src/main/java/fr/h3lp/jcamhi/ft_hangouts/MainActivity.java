package fr.h3lp.jcamhi.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Toolbar _toolbar;
    private ListView _listView;
    private MyAdapter _adapter;

    public List<Contact> get_contacts() {
        return _contacts;
    }

    private List<Contact> _contacts;

    static final int LANGUAGE_CHANGED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sharedPref.getString("pref_language", getString(R.string.default_language));

        Locale loc = new Locale(language);
        Locale.setDefault(loc);
        Configuration config = new Configuration();
        config.locale = loc;

        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_main);

        _toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _listView = (ListView) findViewById(R.id.list_view);

        this._contacts = this.getContacts();
        _adapter = new MyAdapter(MainActivity.this, this._contacts);
        _listView.setAdapter(_adapter);
    }

    private List<Contact>getContacts(){
        List<Contact> contacts = new ArrayList<Contact>();
        for (int i = 0; i < 10; i++) {
            contacts.add(new Contact("Jeremy", "Camhi", "0610202020", getDrawable(R.mipmap.ic_person)));
            contacts.add(new Contact("Collette", "Camhi", "0610303030", getDrawable(R.mipmap.ic_person)));
            contacts.add(new Contact("Marcel", "Camhi", "0610414141", getDrawable(R.mipmap.ic_person)));
            contacts.add(new Contact("Yohan", "Camhi", "0610424242", getDrawable(R.mipmap.ic_person)));
        }
        return contacts;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
                startActivityForResult(intent, LANGUAGE_CHANGED);
                return true;
            case R.id.action_search:
                Snackbar.make(findViewById(R.id.coordinator_main), "Got click on settings", Snackbar.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void start_add_contact(View v) {
        Toast.makeText(MainActivity.this, R.string.adding_contact, Toast.LENGTH_SHORT).show();
        MainActivity.this._contacts.add(new Contact("Jeremy", "Camhi", "0610202020", getDrawable(R.mipmap.ic_person)));
        MainActivity.this._adapter.notifyDataSetChanged();
        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == LANGUAGE_CHANGED) {
            this.recreate();
        }
    }
}
