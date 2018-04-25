package fr.h3lp.jcamhi.ft_hangouts;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * Created by jcamhi on 9/5/17.
 */

public class DatabaseSingleton {
    @SuppressLint("StaticFieldLeak")
    private static MySQLiteHelper _helper = null;
    private static ContactDAO _ContactDao = null;
    private static SMSDAO _SMSDAO = null;

    public static synchronized ContactDAO getContactDao(Context context) {
        if (DatabaseSingleton._ContactDao == null) {
            DatabaseSingleton._ContactDao = new ContactDAO(context.getApplicationContext());
        }
        return DatabaseSingleton._ContactDao;
    }

    public static synchronized SMSDAO getSmsDao(Context context) {
        if (DatabaseSingleton._SMSDAO == null) {
            DatabaseSingleton._SMSDAO = new SMSDAO(context.getApplicationContext());
        }
        return DatabaseSingleton._SMSDAO;
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
