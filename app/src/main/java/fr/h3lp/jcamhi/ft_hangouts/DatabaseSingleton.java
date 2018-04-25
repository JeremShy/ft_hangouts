package fr.h3lp.jcamhi.ft_hangouts;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by jcamhi on 9/5/17.
 */

public class DatabaseSingleton {
    @SuppressLint("StaticFieldLeak")
    private static MySQLiteHelper _helper = null;
    private static ContactDAO _dao = null;

    public static synchronized ContactDAO getDao(Context context) {
        if (DatabaseSingleton._dao == null) {
            DatabaseSingleton._dao = new ContactDAO(context.getApplicationContext());
        }
        return DatabaseSingleton._dao;
    }

    public static synchronized MySQLiteHelper getSqliteHelper(Context context)
    {
        if (DatabaseSingleton._helper == null) {
            DatabaseSingleton._helper = new MySQLiteHelper(context);
        }
        return DatabaseSingleton._helper;
    }

    private DatabaseSingleton(Context context) {
    }
}
