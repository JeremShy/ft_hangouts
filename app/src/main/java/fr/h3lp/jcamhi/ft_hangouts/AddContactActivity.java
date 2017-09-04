package fr.h3lp.jcamhi.ft_hangouts;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jcamhi on 9/4/17.
 */

public class AddContactActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        ImageView iv = (ImageView)findViewById(R.id.avatar_page_contact);
        iv.setImageDrawable(getDrawable(R.mipmap.ic_person));

        final EditText prenom, nom, numero;
        final TextInputLayout prenom_layout, nom_layout, numero_layout;

        prenom = findViewById(R.id.edit_prenom);
        nom = findViewById(R.id.edit_nom);
        numero = findViewById(R.id.edit_numero);

        prenom_layout = findViewById(R.id.edit_prenom_layout);
        nom_layout = findViewById(R.id.edit_nom_layout);
        numero_layout = findViewById(R.id.edit_numero_layout);

        prenom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
            public void onFocusChange(View view, boolean b) {
               prenom_layout.setError(null);
               prenom_layout.setHint(getString(R.string.prenom_hint));
               Toast.makeText(AddContactActivity.this, "here", Toast.LENGTH_SHORT).show();
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
    }

    public void add_contact(View v) {
        boolean error = false;
        EditText prenom, nom, numero;
        TextInputLayout prenom_layout, nom_layout, numero_layout;

        prenom = findViewById(R.id.edit_prenom);
        nom = findViewById(R.id.edit_nom);
        numero = findViewById(R.id.edit_numero);

        prenom_layout = findViewById(R.id.edit_prenom_layout);
        nom_layout = findViewById(R.id.edit_nom_layout);
        numero_layout = findViewById(R.id.edit_numero_layout);

        if (this.isEmpty(prenom)) {
            prenom_layout.setError(getString(R.string.error_prenom));
            prenom_layout.setHint(null);
            prenom.clearFocus();
            error = true;
        }
        if (this.isEmpty(nom)) {
            nom_layout.setError(getString(R.string.error_nom));
            nom_layout.setHint(null);
            nom.clearFocus();
            error = true;
        }
        if (this.isEmpty(numero)) {
            numero_layout.setError(getString(R.string.error_numero));
            numero_layout.setHint(null);
            numero.clearFocus();
            error = true;
        }
        if (error)
            return ;

        Snackbar.make(findViewById(R.id.coor_add), getString(R.string.contact_edited), Snackbar.LENGTH_SHORT).show();
//        this.finish();
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }
}
