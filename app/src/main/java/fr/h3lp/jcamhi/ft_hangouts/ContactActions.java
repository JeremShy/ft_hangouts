package fr.h3lp.jcamhi.ft_hangouts;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

class ContactActions{
    static private long _actualId = -1;
    static private Activity _actualActivity = null;
    static private Contact _actualContact = null;
    static private int MY_PERM_CALL_PHONE = 0;
    static private ContactActions _singleton = null;

    static public void callContact(Activity activity, long id) {
        if (_singleton == null) {
            _singleton = new ContactActions();
        }
        if (id != _actualId && _actualActivity != activity) {
            _actualActivity = activity;
            _actualId = id;
            _actualContact = DatabaseSingleton.getContactDao(activity).getContact(id);
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.e("pouet", "Asking for permission."); //NON-NLS
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, MY_PERM_CALL_PHONE);
            return;
        }
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        Log.e("pouet", Uri.parse(_actualContact.get_numero()).toString()); //NON-NLS
        phoneIntent.setData(Uri.parse("tel:" + _actualContact.get_numero())); //NON-NLS

        _actualId = -1;
        _actualContact = null;
        _actualActivity = null;
        if (phoneIntent.resolveActivity(activity.getPackageManager()) != null)
            try {
                activity.startActivity(phoneIntent);
            } catch (SecurityException e) {
                Log.e("Exception", "ERROR ! SECURITY EXCEPTION: " + e.getLocalizedMessage()); //NON-NLS
            }
    }

    public static void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERM_CALL_PHONE) {
            Log.e("pouet", "Received permission response.");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                callContact(_actualActivity, _actualId);
            else {
                _actualId = -1;
                _actualContact = null;
                _actualActivity = null;
            }

        }
    }
}