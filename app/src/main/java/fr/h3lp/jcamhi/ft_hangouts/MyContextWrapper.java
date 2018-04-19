package fr.h3lp.jcamhi.ft_hangouts;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by jcamhi on 9/4/17.
 */

@SuppressWarnings("unused")
public class MyContextWrapper extends android.content.ContextWrapper {

    public MyContextWrapper(Context base) {
        super(base);
    }

    public static MyContextWrapper wrap(Context context, Locale newLocale) {

        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();

        configuration.setLocale(newLocale);
        context = context.createConfigurationContext(configuration);

        return new MyContextWrapper(context);
    }
}