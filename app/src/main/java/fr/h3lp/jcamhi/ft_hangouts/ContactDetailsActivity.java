package fr.h3lp.jcamhi.ft_hangouts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static fr.h3lp.jcamhi.ft_hangouts.R.id.toolbar;

/**
 * Created by jcamhi on 9/6/17.
 */

public class ContactDetailsActivity extends AppCompatActivity {
    private TextView _nom;
    private TextView _prenom;
    private TextView _numero;
    private TextView _domicile;
    private TextView _anniv;
    private Contact _contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_details);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.contact_details));
        ImageView i = (ImageView) findViewById(R.id.avatar_details_contact);
        i.setImageDrawable(getDrawable(R.mipmap.ic_person));

        this._nom = (TextView) findViewById(R.id.details_nom);
        this._prenom = (TextView) findViewById(R.id.details_prenom);
        this._numero = (TextView) findViewById(R.id.details_numero);
        this._domicile = (TextView) findViewById(R.id.details_domicile);
        this._anniv = (TextView) findViewById(R.id.details_anniv);

        long id = getIntent().getLongExtra(MyAdapter.EXTRA_ID, -1);
        if (id == -1)
            finish();

        ContactDAO dao = DatabaseSingleton.getDao(this);
        this._contact = dao.getContact(id);
        if (this._contact == null)
            finish();
        this._nom.setText(this._contact.get_nom());
        this._prenom.setText(this._contact.get_prenom());
        this._numero.setText(this._contact.get_numero());
        if (_contact.get_domicile() != "" && !this._contact.get_domicile().isEmpty())
            this._domicile.setText(this._contact.get_domicile());
        else {
            this._domicile.setText(getString(R.string.not_suppliedf));
            this._domicile.setAlpha(150f / 255f);
        }
        if (_contact.get_anniversaire() != null)
            this._anniv.setText(this._contact.get_anniv_as_str());
        else {
            this._anniv.setText(getString(R.string.not_suppliedm));
            this._anniv.setAlpha(150f / 255f);
        }
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
