package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;

/**
 * Created by jcamhi on 9/5/17.
 */

public class DatabaseSingleton {
    private static ContactDAO _dao = null;

    public static synchronized ContactDAO getDao(Context context) {
        if (DatabaseSingleton._dao == null) {
            DatabaseSingleton._dao = new ContactDAO(context.getApplicationContext());
        }
        return DatabaseSingleton._dao;
    }

    private DatabaseSingleton(Context context) {
    }
}
