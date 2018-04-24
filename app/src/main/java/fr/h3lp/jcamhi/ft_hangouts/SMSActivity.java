package fr.h3lp.jcamhi.ft_hangouts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

/**
 * Created by jcamhi on 9/7/17.
 */

@SuppressWarnings("ALL")
public class SMSActivity extends AppCompatActivity {
    private TextView _nom;
    private TextView _prenom;
    private TextView _numero;
    private TextView _domicile;
    private TextView _anniv;
    private Contact _contact;
    private ListView _messagesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sms_activity);

        long id = getIntent().getLongExtra(MyAdapter.EXTRA_ID, -1);
        if (id == -1) {
            finish();
            return;
        }
        ContactDAO dao = DatabaseSingleton.getDao(this);
        this._contact = dao.getContact(id);
        if (this._contact == null) {
            finish();
            return;
        }

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(this._contact.get_nom_prenom());

        _messagesList = (ListView)findViewById(R.id.list_view_sms);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.setResult(RESULT_CANCELED);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
