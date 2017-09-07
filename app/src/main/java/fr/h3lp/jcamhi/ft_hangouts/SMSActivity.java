package fr.h3lp.jcamhi.ft_hangouts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by jcamhi on 9/7/17.
 */

public class SMSActivity extends AppCompatActivity {
    private TextView _nom;
    private TextView _prenom;
    private TextView _numero;
    private TextView _domicile;
    private TextView _anniv;
    private Contact _contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sms_activity);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        getSupportActionBar().setTitle(this._contact.get_nom_prenom());
    }
}
