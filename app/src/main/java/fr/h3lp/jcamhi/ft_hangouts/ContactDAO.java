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

public class ContactDAO {
    private SQLiteDatabase _database;
    private MySQLiteHelper _dbHelper;
    private Context _context;
    private String[] allColumns = {
            MySQLiteHelper.COL_ID_CONTACTS,
            MySQLiteHelper.COL_NOM_CONTACTS,
            MySQLiteHelper.COL_PRENOM_CONTACTS,
            MySQLiteHelper.COL_NUMERO_CONTACTS,
            MySQLiteHelper.COL_DOMICILE_CONTACTS,
            MySQLiteHelper.COL_ANNIVERSAIRE_CONTACTS
    };

    ContactDAO(Context context) {
        this._context = context;
        this._dbHelper = DatabaseSingleton.getSqliteHelper(context);
    }

    public void open() throws SQLException {
        this._database = _dbHelper.getWritableDatabase();
    }

    public void close() {
        this._dbHelper.close();
    }

    public Contact createContact(String nom, String prenom, String numero, String domicile, Date anniversaire) {
        Long d;
        if (anniversaire != null)
            d = anniversaire.getTime();
        else
            d = (long) -1;

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_NOM_CONTACTS, nom);
        values.put(MySQLiteHelper.COL_PRENOM_CONTACTS, prenom);
        values.put(MySQLiteHelper.COL_NUMERO_CONTACTS, numero);
        values.put(MySQLiteHelper.COL_DOMICILE_CONTACTS, domicile);
        values.put(MySQLiteHelper.COL_ANNIVERSAIRE_CONTACTS, d);

        long insertId = this._database.insert(MySQLiteHelper.TABLE_CONTACTS, null, values);
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_CONTACTS, allColumns, MySQLiteHelper.COL_ID_CONTACTS + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Contact ret = this.cursorToContact(cursor);
        cursor.close();
        return (ret);
    }

    public void updateContact(long id, String nom, String prenom, String numero, String domicile, Date anniversaire) {
        Long d;
        if (anniversaire != null)
            d = anniversaire.getTime();
        else
            d = (long) -1;

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_NOM_CONTACTS, nom);
        values.put(MySQLiteHelper.COL_PRENOM_CONTACTS, prenom);
        values.put(MySQLiteHelper.COL_NUMERO_CONTACTS, numero);
        values.put(MySQLiteHelper.COL_DOMICILE_CONTACTS, domicile);
        values.put(MySQLiteHelper.COL_ANNIVERSAIRE_CONTACTS, d);

        this._database.update(MySQLiteHelper.TABLE_CONTACTS, values, MySQLiteHelper.COL_ID_CONTACTS + " = " + id, null);
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_CONTACTS, allColumns, MySQLiteHelper.COL_ID_CONTACTS + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        cursor.close();
    }


    public void deleteContact(Contact contact) {
        long id = contact.get_id();
        _database.delete(MySQLiteHelper.TABLE_CONTACTS, MySQLiteHelper.COL_ID_CONTACTS + " = " + id, null);
    }

    public List<Contact> getAllContacts() {
        List<Contact> ret = new ArrayList<>();

        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_CONTACTS, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact cont = this.cursorToContact(cursor);
            ret.add(cont);
            cursor.moveToNext();
        }
        cursor.close();
        return ret;
    }

    public Contact getContact(long id) {
        if (this._database == null)
            return null;
        Cursor cursor = this._database.query(MySQLiteHelper.TABLE_CONTACTS, allColumns, MySQLiteHelper.COL_ID_CONTACTS + " = " + id, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.isAfterLast() || cursor.isBeforeFirst())
            return (null);
        Contact ret = this.cursorToContact(cursor);
        cursor.close();
        return ret;
    }

    private Contact cursorToContact(Cursor cursor){
        Long l = cursor.getLong(cursor.getColumnIndex(MySQLiteHelper.COL_ANNIVERSAIRE_CONTACTS));
        Date d;
        if (l <= 0)
            d = null;
        else
            d = new Date(l);
        return (new Contact(
                cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_ID_CONTACTS)),
                cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COL_NOM_CONTACTS)),
                cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COL_PRENOM_CONTACTS)),
                cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COL_NUMERO_CONTACTS)),
                ResourcesCompat.getDrawable(this._context.getResources(), R.mipmap.ic_person, null), // Contact's picture.
                cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COL_DOMICILE_CONTACTS)),
                d));
    }
}
