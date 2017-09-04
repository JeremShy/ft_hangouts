package fr.h3lp.jcamhi.ft_hangouts;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
    }

    public void add_contact(View v) {
        boolean error = false;
        EditText prenom, nom, numero;

        prenom = findViewById(R.id.edit_prenom);
        nom = findViewById(R.id.edit_nom);
        numero = findViewById(R.id.edit_numero);

        if (this.isEmpty(prenom)) {
            prenom.setError(getString(R.string.error_prenom));
            error = true;
        }
        if (this.isEmpty(nom)) {
            nom.setError(getString(R.string.error_nom));
            error = true;
        }
        if (this.isEmpty(numero)) {
            numero.setError(getString(R.string.error_numero));
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
