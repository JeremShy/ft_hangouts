package fr.h3lp.jcamhi.ft_hangouts;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Objects;

import static fr.h3lp.jcamhi.ft_hangouts.ContactActions.MY_PERM_CALL_PHONE;
import static fr.h3lp.jcamhi.ft_hangouts.ContactActions.MY_PERM_SEND_SMS;

/**
 * Created by jcamhi on 9/7/17.
 */

@SuppressWarnings("ALL")
public class SMSActivity extends AppCompatActivity {
    private Contact     _contact;
    private ListView    _messagesList;
    private EditText    _text;
    private SMSAdapter  _adapter;
    private List<MySMS> _SMSs;

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

        _SMSs = DatabaseSingleton.getSmsDao(this).getSMSforContact(_contact);
        _adapter = new SMSAdapter(this, _SMSs);
        _messagesList.setAdapter(_adapter);
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
        if (_text.getText().toString().isEmpty())
            return ;
        _text.clearFocus();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.e("pouet", "Asking for permission."); //NON-NLS
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERM_SEND_SMS);
            return;
        }

        String message = _text.getText().toString();
        _text.setText("");

        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(_contact.get_numero(), null, message, null, null);
        _SMSs.add(DatabaseSingleton.getSmsDao(this).createSms(_contact.get_id(), true, message));
        _adapter.notifyDataSetChanged();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERM_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                sendClickedSmsActivity(_text);
        }
    }
}
