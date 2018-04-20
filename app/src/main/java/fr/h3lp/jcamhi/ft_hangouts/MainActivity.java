package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Locale;

import static fr.h3lp.jcamhi.ft_hangouts.MyAdapter.EXTRA_ID;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_LANGUAGE = "pref_language"; //NON-NLS
    public static final String ID_EXTRA = "id"; //NON-NLS
    private MyAdapter _adapter;

    private List<Contact> _contacts;

    static final int LANGUAGE_CHANGED = 1;
    static final int CONTACT_ADDED = 2;
    static final int CONTACT_CHANGED = 3;

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

        Toolbar _toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);

        DatabaseSingleton.getDao(this).open();

        ListView _listView = (ListView) findViewById(R.id.list_view);
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
                intent.putExtra(EXTRA_ID, hold.id);
                //startActivity(intent);
                startActivityForResult(intent, CONTACT_CHANGED);
            }
        });


    }

    private List<Contact>getContacts() {
        return DatabaseSingleton.getDao(this).getAllContacts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void  onPause() {
        super.onPause();
    }

    @Override
    protected void  onResume() {
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
                Log.e("fr", "id : " + Long.toString(id)); //NON-NLS
                Contact cont = DatabaseSingleton.getDao(this).getContact(id);
                Log.e("fr", "Adding contact : " + cont.get_nom_prenom()); //NON-NLS
                this._contacts.add(cont);
                this._adapter.notifyDataSetChanged();
            }
        } else if (requestCode == CONTACT_CHANGED) {
            if (resultCode == RESULT_OK) {
                Long id = data.getLongExtra(EXTRA_ID, 0);
                Log.e("fr", "Removing contact " + Long.toString(id));
                Contact fakeContact = new Contact(id, "", "", "", ResourcesCompat.getDrawable(this.getResources(), R.mipmap.ic_person, null));
                this._contacts.remove(fakeContact);
                this._adapter.notifyDataSetChanged();
            }
        }
    }
}
