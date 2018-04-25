package fr.h3lp.jcamhi.ft_hangouts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by jcamhi on 9/6/17.
 */

public class EditContactActivity extends AppCompatActivity {
    private Contact _contact;

    private static Date _date;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        this.setContentView(R.layout.edit_contact);

        final TextInputLayout prenom_layout, nom_layout, numero_layout;

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.edit_contact));


        EditText _nom = (EditText) findViewById(R.id.edit_nom);
        EditText _prenom = (EditText) findViewById(R.id.edit_prenom);
        EditText _numero = (EditText) findViewById(R.id.edit_numero);
        EditText _domicile = (EditText) findViewById(R.id.edit_domicile);
        EditText _anniv = (EditText) findViewById(R.id.edit_anniversaire);

        prenom_layout = (TextInputLayout) findViewById(R.id.edit_prenom_layout);
        nom_layout = (TextInputLayout) findViewById(R.id.edit_nom_layout);
        numero_layout = (TextInputLayout) findViewById(R.id.edit_numero_layout);


        long id = getIntent().getLongExtra(ContactDetailsActivity.ID, -1);
        if (id == -1) {
            finish();
            return;
        }
        ContactDAO dao = DatabaseSingleton.getContactDao(this);
        this._contact = dao.getContact(id);
        if (this._contact == null) {
            finish();
            return;
        }
        _nom.setText(this._contact.get_nom());
        _prenom.setText(this._contact.get_prenom());
        _numero.setText(this._contact.get_numero());
        if (!_contact.get_domicile().equals("") && !this._contact.get_domicile().isEmpty())
            _domicile.setText(this._contact.get_domicile());
        if (_contact.get_anniversaire() != null)
            _anniv.setText(this._contact.get_anniv_as_str());

        _prenom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                prenom_layout.setError(null);
                prenom_layout.setHint(getString(R.string.prenom_hint));
            }
        });
         _nom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                nom_layout.setError(null);
                nom_layout.setHint(getString(R.string.nom_hint));
            }
        });
        _numero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                numero_layout.setError(null);
                numero_layout.setHint(getString(R.string.numero_hint));
            }
        });

        _anniv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    view.clearFocus();
                    showDatePickerDialog(view);
                }
            }
        });
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

    public void showDatePickerDialog(View v) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        DialogFragment newFragment = new EditContactActivity.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker"); //NON-NLS
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            TextInputLayout lay = getActivity().findViewById(R.id.edit_anniversaire_layout);
            EditText ed = getActivity().findViewById(R.id.edit_anniversaire);

            GregorianCalendar cal = new GregorianCalendar(year, month, day);
            Date d = cal.getTime();
            String s = Contact.get_date_as_str_stat(d);

            ed.setText(s);
            ed.clearFocus();
            lay.clearFocus();

            EditContactActivity._date = d;
        }
    }

    public void edit_contact(View v) {
        boolean error = false;
        EditText prenom, nom, numero, domicile;
        TextInputLayout prenom_layout, nom_layout, numero_layout;

        prenom = (EditText) findViewById(R.id.edit_prenom);
        nom = (EditText) findViewById(R.id.edit_nom);
        numero = (EditText) findViewById(R.id.edit_numero);
        domicile = (EditText) findViewById(R.id.edit_domicile);

        View cur = getCurrentFocus();
        if (cur != null)
            cur.clearFocus();

        prenom_layout = (TextInputLayout) findViewById(R.id.edit_prenom_layout);
        nom_layout = (TextInputLayout) findViewById(R.id.edit_nom_layout);
        numero_layout = (TextInputLayout) findViewById(R.id.edit_numero_layout);

        if (this.isEmpty(prenom)) {
            prenom_layout.setError(getString(R.string.error_prenom));
            prenom_layout.setHint(null);
            error = true;
        }
        if (this.isEmpty(nom)) {
            nom_layout.setError(getString(R.string.error_nom));
            nom_layout.setHint(null);
            error = true;
        }
        if (this.isEmpty(numero)) {
            numero_layout.setError(getString(R.string.error_numero));
            numero_layout.setHint(null);
            error = true;
        }
        if (error)
            return ;

        DatabaseSingleton.getContactDao(this).updateContact(
                this._contact.get_id(),
                nom.getText().toString(),
                prenom.getText().toString(),
                numero.getText().toString(),
                domicile.getText().toString(),
                EditContactActivity._date);

        Intent result = new Intent();
        result.putExtra(ContactDetailsActivity.CONTACT_MODIFIED, true);
        this.setResult(RESULT_OK, result);
        this.finish();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }
}
