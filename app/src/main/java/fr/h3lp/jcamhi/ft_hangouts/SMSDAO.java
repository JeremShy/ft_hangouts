package fr.h3lp.jcamhi.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.res.ResourcesCompat;

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
            MySQLiteHelper.COL_FROM_ME
    };

    SMSDAO(Context context) {
        this._context = context;
        this._dbHelper = DatabaseSingleton.getSqliteHelper(context);
    }

    public void open() throws SQLException {
        this._database = _dbHelper.getWritableDatabase();
    }

    public void close() {
        this._dbHelper.close();
    }

    public MySMS createSms(int destinataire, boolean fromMe, String message) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_DEST_SMS, destinataire);
        values.put(MySQLiteHelper.COL_FROM_ME, fromMe ? 1 : 0);
        values.put(MySQLiteHelper.COL_MESSAGE, message);

        long insertId = this._database.insert(MySQLiteHelper.TABLE_SMS, null, values);
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_SMS, allColumns, MySQLiteHelper.COL_ID_SMS + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        MySMS ret = this.cursorToSMS(cursor);
        cursor.close();
        return (ret);
    }

    public void updateSms(long id, int destinataire, boolean fromMe, String message) {

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
                cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COL_FROM_ME)) == 1, //fromMe
                cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COL_MESSAGE)), // Message
                DatabaseSingleton.getContactDao(_context).getContact(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COL_DEST_SMS))))); // destinataire
    }
}
