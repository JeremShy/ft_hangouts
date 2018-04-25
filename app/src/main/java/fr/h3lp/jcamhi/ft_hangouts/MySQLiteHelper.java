package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jcamhi on 9/5/17.
 */

@SuppressWarnings("ALL")
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COL_ID_CONTACTS = "_id";
    public static final String COL_PRENOM_CONTACTS = "prenom";
    public static final String COL_NOM_CONTACTS = "nom";
    public static final String COL_NUMERO_CONTACTS = "numero";
    public static final String COL_DOMICILE_CONTACTS = "domicile";
    public static final String COL_ANNIVERSAIRE_CONTACTS = "anniversaire";


    public static final String TABLE_SMS = "sms";
    public static final String COL_ID_SMS = "_id";
    public static final String COL_DEST_SMS = "destinataire";
    public static final String COL_FROM_ME = "from_me";

    private static final String DATABASE_NAME = "ft_hangouts.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CONTACT_CREATE = "create table "
            + MySQLiteHelper.TABLE_CONTACTS + "("
            + MySQLiteHelper.COL_ID_CONTACTS + " integer primary key autoincrement, "
            + MySQLiteHelper.COL_NOM_CONTACTS + " text not null, "
            + MySQLiteHelper.COL_PRENOM_CONTACTS + " text not null, "
            + MySQLiteHelper.COL_NUMERO_CONTACTS + " text not null, "
            + MySQLiteHelper.COL_DOMICILE_CONTACTS + " text not null, "
            + MySQLiteHelper.COL_ANNIVERSAIRE_CONTACTS + " int not null"
            + ");";

    private static final String SMS_CREATE = "create table "
            + MySQLiteHelper.TABLE_SMS + "("
            + MySQLiteHelper.COL_ID_SMS + " integer primary key autoincrement, "
            + MySQLiteHelper.COL_DEST_SMS + " text not null, "
            + MySQLiteHelper.COL_FROM_ME + " int not null"
            + ");";


    MySQLiteHelper(Context context) {
        super (context, MySQLiteHelper.DATABASE_NAME, null, MySQLiteHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CONTACT_CREATE);
        sqLiteDatabase.execSQL(SMS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  TABLE_CONTACTS); //NON-NLS
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int old, int newV) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +  TABLE_CONTACTS); //NON-NLS
        onCreate(sqLiteDatabase);
    }

}
