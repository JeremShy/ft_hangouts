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
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Created by jcamhi on 9/4/17.
 */

public class AddContactActivity extends AppCompatActivity {
    private static final String EXTRA_ID = "id"; //NON-NLS
    private static Date _date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        AddContactActivity._date = null;

        this.setResult(RESULT_CANCELED);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.add_a_contact));


        ImageView iv = (ImageView)findViewById(R.id.avatar_page_contact);
        iv.setImageDrawable(getDrawable(R.mipmap.ic_person));

        final EditText prenom, nom, numero, anniversaire;
        final TextInputLayout prenom_layout, nom_layout, numero_layout;

        prenom = (EditText)findViewById(R.id.edit_prenom);
        nom = (EditText)findViewById(R.id.edit_nom);
        numero = (EditText)findViewById(R.id.edit_numero);
        anniversaire = (EditText)findViewById(R.id.edit_anniversaire);

        prenom_layout = (TextInputLayout) findViewById(R.id.edit_prenom_layout);
        nom_layout = (TextInputLayout) findViewById(R.id.edit_nom_layout);
        numero_layout = (TextInputLayout) findViewById(R.id.edit_numero_layout);

        prenom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
            public void onFocusChange(View view, boolean b) {
               prenom_layout.setError(null);
               prenom_layout.setHint(getString(R.string.prenom_hint));
            }
        });
        nom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                nom_layout.setError(null);
                nom_layout.setHint(getString(R.string.nom_hint));
            }
        });
        numero.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                numero_layout.setError(null);
                numero_layout.setHint(getString(R.string.numero_hint));
            }
        });

        anniversaire.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker"); //NON-NLS
    }

    public void add_contact(View v) { // Called on Fab click
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

        Contact cont = DatabaseSingleton.getDao(this).createContact(
                nom.getText().toString(),
                prenom.getText().toString(),
                numero.getText().toString(),
                domicile.getText().toString(),
                AddContactActivity._date);

        Intent result = new Intent();
        result.putExtra(EXTRA_ID, cont.get_id());
        this.setResult(RESULT_OK, result);
        this.finish();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
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

            AddContactActivity._date = d;
        }
    }
}
