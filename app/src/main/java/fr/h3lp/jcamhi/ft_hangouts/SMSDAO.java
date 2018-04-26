package fr.h3lp.jcamhi.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jcamhi on 9/5/17.
 */

public class SMSDAO {
    private SQLiteDatabase _database;
    private MySQLiteHelper _dbHelper;
    private Context _context;
    private String[] allColumns = {
            MySQLiteHelper.COL_ID_SMS,
            MySQLiteHelper.COL_DEST_SMS,
            MySQLiteHelper.COL_FROM_ME,
            MySQLiteHelper.COL_MESSAGE
    };

    SMSDAO(Context context) {
        this._context = context;
        this._dbHelper = DatabaseSingleton.getSqliteHelper(context);
        open();
    }

    public void open() throws SQLException {
        this._database = _dbHelper.getWritableDatabase();
        if (this._database == null)
        {
            Log.e("pouet", "ERROR : Could not get a writable database handle.");
        }
    }

    public void close() {
        this._dbHelper.close();
    }

    public MySMS createSms(long destinataire, boolean fromMe, String message) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_DEST_SMS, destinataire);
        values.put(MySQLiteHelper.COL_FROM_ME, fromMe ? 1 : 0);
        values.put(MySQLiteHelper.COL_MESSAGE, message);

        long insertId = this._database.insert(MySQLiteHelper.TABLE_SMS, null, values);
        if (insertId == -1)
            Log.e("pouet", "Error : Could not insert sms into the database.");
        else
            Log.e("pouet", "Insert id  : " + insertId);
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_SMS, allColumns, MySQLiteHelper.COL_ID_SMS + " = " + insertId, null, null, null, null);
        if (cursor.getCount() == 0)
            Log.e("pouet", "Error : Empty cursor.");
        cursor.moveToFirst();
        MySMS ret = this.cursorToSMS(cursor);
        cursor.close();
        return (ret);
    }

    public List<MySMS> getSMSforContact(Contact contact)
    {
        List<MySMS> ret = new ArrayList<>();
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_SMS, allColumns, MySQLiteHelper.COL_DEST_SMS + " = " + contact.get_id(), null, null, null, null);
        if (cursor.getCount() == 0)
            return ret;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ret.add(cursorToSMS(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return (ret);
    }

    private void updateSms(long id, long destinataire, boolean fromMe, String message) { // Shouldn't be used

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_DEST_SMS, destinataire);
        values.put(MySQLiteHelper.COL_FROM_ME, fromMe ? 1 : 0);
        values.put(MySQLiteHelper.COL_MESSAGE, message);

        this._database.update(MySQLiteHelper.TABLE_SMS, values, MySQLiteHelper.COL_ID_SMS + " = " + id, null);
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_SMS, allColumns, MySQLiteHelper.COL_ID_SMS + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        cursor.close();
    }


    public void deleteSms(MySMS SMS) {
        long id = SMS.getId();
        _database.delete(MySQLiteHelper.TABLE_SMS, MySQLiteHelper.COL_ID_SMS + " = " + id, null);
    }

    public MySMS getSMS(long id) {
        if (this._database == null)
            return null;
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_SMS, allColumns, MySQLiteHelper.COL_ID_SMS + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.isAfterLast() || cursor.isBeforeFirst())
            return (null);
        MySMS ret = this.cursorToSMS(cursor);
        cursor.close();
        return ret;
    }

    private MySMS cursorToSMS(Cursor cursor){
        return (new MySMS(
                cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_ID_SMS)), // id
                cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_FROM_ME)) == 1, //fromMe
                cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_MESSAGE)), // Message
                DatabaseSingleton.getContactDao(_context).getContact(cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_DEST_SMS))))); // destinataire
    }
}
