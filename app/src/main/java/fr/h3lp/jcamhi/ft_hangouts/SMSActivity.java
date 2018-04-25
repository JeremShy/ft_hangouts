package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.Objects;

/**
 * Created by jcamhi on 9/7/17.
 */

@SuppressWarnings("ALL")
public class SMSActivity extends AppCompatActivity {
    private Contact _contact;
    private ListView _messagesList;
    private EditText   _text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.sms_activity);

        long id = getIntent().getLongExtra(MyAdapter.EXTRA_ID, -1);
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

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(this._contact.get_nom_prenom());

        _messagesList = (ListView)findViewById(R.id.list_view_sms);
        _text = (EditText)findViewById(R.id.sms_message_edit);
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

    public void sendClickedSmsActivity(View v) {
        Log.e("pouet", "sendClicked");
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(_text.getWindowToken(), 0);
        _text.clearFocus();
        _text.setText("");
    }

}
