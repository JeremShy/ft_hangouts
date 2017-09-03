package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
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

public class MainActivity extends AppCompatActivity {

    private Toolbar _toolbar;
    private ListView _listView;
    private MyAdapter _adapter;

    public List<Contact> get_contacts() {
        return _contacts;
    }

    private List<Contact> _contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        _listView = (ListView) findViewById(R.id.list_view);

        this._contacts = this.getContacts();
        _adapter = new MyAdapter(MainActivity.this, this._contacts);
        _listView.setAdapter(_adapter);
        ((FloatingActionButton)findViewById(R.id.fab)).setOnClickListener(this.add_contact);
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
                Toast.makeText(MainActivity.this, "Got click on settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                Toast.makeText(MainActivity.this, "Got click on search", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private View.OnClickListener add_contact = new View.OnClickListener() {
        @Override
        public void onClick(View v){
            Toast.makeText(MainActivity.this, "Adding a contact", Toast.LENGTH_SHORT).show();
            MainActivity.this._contacts.add(new Contact("Jeremy", "Camhi", "0610202020", getDrawable(R.mipmap.ic_person)));
            MainActivity.this._adapter.notifyDataSetChanged();
        }
    };
}
