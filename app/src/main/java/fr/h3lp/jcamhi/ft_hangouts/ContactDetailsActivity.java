package fr.h3lp.jcamhi.ft_hangouts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import static fr.h3lp.jcamhi.ft_hangouts.MyAdapter.EXTRA_ID;

/**
 * Created by jcamhi on 9/6/17.
 */

public class ContactDetailsActivity extends AppCompatActivity {
    public static final int MODIFY_CONTACT = 1;
    public static final String CONTACT_MODIFIED = "CONTACT_MODIFIED"; //NON-NLS
    public static final String ID = "ID"; //NON-NLS

    private static final int MY_PERM_CALL_PHONE = 0;

    private Contact _contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.contact_details);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.contact_details));
        ImageView i = (ImageView) findViewById(R.id.avatar_details_contact);
        i.setImageDrawable(getDrawable(R.mipmap.ic_person));

        TextView _nom = (TextView) findViewById(R.id.details_nom);
        TextView _prenom = (TextView) findViewById(R.id.details_prenom);
        TextView _numero = (TextView) findViewById(R.id.details_numero);
        TextView _domicile = (TextView) findViewById(R.id.details_domicile);
        TextView _anniv = (TextView) findViewById(R.id.details_anniv);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);
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
        _nom.setText(this._contact.get_nom());
        _prenom.setText(this._contact.get_prenom());
        _numero.setText(this._contact.get_numero());
        if (!_contact.get_domicile().equals("") && !this._contact.get_domicile().isEmpty())
            _domicile.setText(this._contact.get_domicile());
        else {
            _domicile.setText(getString(R.string.not_suppliedf));
            _domicile.setAlpha(150f / 255f);
        }
        if (_contact.get_anniversaire() != null)
            _anniv.setText(this._contact.get_anniv_as_str());
        else {
            _anniv.setText(getString(R.string.not_suppliedm));
            _anniv.setAlpha(150f / 255f);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details_contact, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.setResult(RESULT_CANCELED);
                this.finish();
                return true;
            case R.id.action_call:
                this.callContact();
                return true;
            case R.id.action_message:
                Intent intent = new Intent(ContactDetailsActivity.this, SMSActivity.class);
                intent.putExtra(EXTRA_ID, this._contact.get_id());
                startActivity(intent);
                return true;
            case R.id.action_delete:
                this.deleteContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callContact() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("pouet", "Asking for permission."); //NON-NLS
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERM_CALL_PHONE);
            return ;
        }
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        Log.e("pouet", Uri.parse(_contact.get_numero()).toString()); //NON-NLS
        phoneIntent.setData(Uri.parse("tel:" + _contact.get_numero())); //NON-NLS
        if (phoneIntent.resolveActivity(getPackageManager()) != null)
            try {
                startActivity(phoneIntent);
            } catch (SecurityException e){
                Log.e("Exception", "ERROR ! SECURITY EXCEPTION: " + e.getLocalizedMessage()); //NON-NLS
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERM_CALL_PHONE)
        {
            Log.e("pouet", "Received permission response.");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                callContact();
        }
    }

    private void deleteContact() {
        Log.e("ft_hangouts", "Delete contact clicked.");

        Intent result = new Intent();
        result.putExtra(EXTRA_ID, _contact.get_id());
        DatabaseSingleton.getDao(this).deleteContact(this._contact);
        this.setResult(RESULT_OK, result);
        this.finish();

    }

    public void start_modif_contact(View v) {
        Intent intent = new Intent(ContactDetailsActivity.this, EditContactActivity.class);
        intent.putExtra(ContactDetailsActivity.ID, this._contact.get_id());
        this.startActivityForResult(intent, ContactDetailsActivity.MODIFY_CONTACT);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ContactDetailsActivity.MODIFY_CONTACT :
                if (resultCode == RESULT_OK && data != null) {
                    if (data.getBooleanExtra(ContactDetailsActivity.CONTACT_MODIFIED, false))
                        this.recreate();
                }
                break;
        }
    }
}
/*

    --------- beginning of crash
04-22 12:44:22.402 5879-5879/? E/AndroidRuntime: FATAL EXCEPTION: main
    Process: fr.h3lp.jcamhi.ft_hangouts, PID: 5879
    java.lang.RuntimeException: Unable to start activity ComponentInfo{fr.h3lp.jcamhi.ft_hangouts/fr.h3lp.jcamhi.ft_hangouts.ContactDetailsActivity}: java.lang.NullPointerException: Attempt to invoke virtual method 'android.database.Cursor android.database.sqlite.SQLiteDatabase.query(java.lang.String, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String)' on a null object reference
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2665)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2726)
        at android.app.ActivityThread.-wrap12(ActivityThread.java)
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1477)
        at android.os.Handler.dispatchMessage(Handler.java:102)
        at android.os.Looper.loop(Looper.java:154)
        at android.app.ActivityThread.main(ActivityThread.java:6119)
        at java.lang.reflect.Method.invoke(Native Method)
        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:886)
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:776)
     Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'android.database.Cursor android.database.sqlite.SQLiteDatabase.query(java.lang.String, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, java.lang.String, java.lang.String)' on a null object reference
        at fr.h3lp.jcamhi.ft_hangouts.ContactDAO.getContact(ContactDAO.java:108)
        at fr.h3lp.jcamhi.ft_hangouts.ContactDetailsActivity.onCreate(ContactDetailsActivity.java:62)
        at android.app.Activity.performCreate(Activity.java:6679)
        at android.app.Instrumentation.callActivityOnCreate(Instrumentation.java:1118)
        at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:2618)
        at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:2726) 
        at android.app.ActivityThread.-wrap12(ActivityThread.java) 
        at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1477) 
        at android.os.Handler.dispatchMessage(Handler.java:102) 
        at android.os.Looper.loop(Looper.java:154) 
        at android.app.ActivityThread.main(ActivityThread.java:6119) 
        at java.lang.reflect.Method.invoke(Native Method) 
        at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:886) 
        at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:776) 
 */