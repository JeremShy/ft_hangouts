package fr.h3lp.jcamhi.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_LANGUAGE = "pref_language";
    public static final String ID_EXTRA = "id";
    private Toolbar _toolbar;
    private ListView _listView;
    private MyAdapter _adapter;

    public List<Contact> get_contacts() {
        return _contacts;
    }

    private List<Contact> _contacts;

    static final int LANGUAGE_CHANGED = 1;
    static final int CONTACT_ADDED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sharedPref.getString(PREF_LANGUAGE, getString(R.string.default_language));
        Locale loc = new Locale(language);
        Locale.setDefault(loc);
        Configuration config = new Configuration();
        config.locale = loc;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        this.setContentView(R.layout.activity_main);

        _toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        DatabaseSingleton.getDao(this).open();

        _listView = (ListView) findViewById(R.id.list_view);
        this._contacts = this.getContacts();
        _adapter = new MyAdapter(MainActivity.this, this._contacts);
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ContactViewHolder hold = (ContactViewHolder) view.getTag();
                if (hold == null)
                    return ;
                Intent intent = new Intent(MainActivity.this, ContactDetailsActivity.class);
                intent.putExtra(MyAdapter.EXTRA_ID, hold.id);
                startActivity(intent);
                return ;
            }
        });


    }

    private List<Contact>getContacts() {
        List<Contact> contacts = new ArrayList<Contact>();
        contacts = DatabaseSingleton.getDao(this).getAllContacts();
        return contacts;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void  onPause() {
//        DatabaseSingleton.getDao(this).close();
        super.onPause();
    }

    @Override
    protected void  onResume() {
//        DatabaseSingleton.getDao(this).open();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        DatabaseSingleton.getDao(this).close();
        super.onDestroy();
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void start_add_contact(View v) {
        Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
        startActivityForResult(intent, CONTACT_ADDED);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LANGUAGE_CHANGED) {
            this.recreate();
        } else if (requestCode == CONTACT_ADDED) {
            if (resultCode == RESULT_OK && data != null) {
                Long id = data.getLongExtra(ID_EXTRA, 0);
                Log.e("fr", "id : " + Long.toString(id));
                Contact cont = DatabaseSingleton.getDao(this).getContact(id);
                Log.e("fr", "Adding contact : " + cont.get_nom_prenom());
                this._contacts.add(cont);
                this._adapter.notifyDataSetChanged();
            }
        }
    }
}
