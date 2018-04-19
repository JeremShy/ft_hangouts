package fr.h3lp.jcamhi.ft_hangouts;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jcamhi on 9/5/17.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COL_ID = "_id";
    public static final String COL_PRENOM = "prenom";
    public static final String COL_NOM = "nom";
    public static final String COL_NUMERO = "numero";
    public static final String COL_DOMICILE = "domicile";
    public static final String COL_ANNIVERSAIRE = "anniversaire";

    public static final String DATABASE_NAME = "contacts.db";
    public static final int DATABASE_VERSION = 1;
 
    private static final String DATABASE_CREATE = "create table "
            + MySQLiteHelper.TABLE_CONTACTS + "("
            + MySQLiteHelper.COL_ID + " integer primary key autoincrement, "
            + MySQLiteHelper.COL_NOM + " text not null, "
            + MySQLiteHelper.COL_PRENOM + " text not null, "
            + MySQLiteHelper.COL_NUMERO + " text not null, "
            + MySQLiteHelper.COL_DOMICILE + " text not null, "
            + MySQLiteHelper.COL_ANNIVERSAIRE + " int not null"
            + ");";

    public MySQLiteHelper(Context context) {
        super (context, MySQLiteHelper.DATABASE_NAME, null, MySQLiteHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
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
