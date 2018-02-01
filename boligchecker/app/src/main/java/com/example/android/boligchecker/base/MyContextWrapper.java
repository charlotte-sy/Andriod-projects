package com.example.android.boligchecker.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.android.boligchecker.MainActivity;
import com.example.android.boligchecker.SplashActivity;
import com.example.android.boligchecker.utilities.VersionUtils;

import java.util.Locale;

import static android.R.attr.value;
import static com.example.android.boligchecker.R.string.language;
import static com.mikepenz.iconics.Iconics.TAG;
import static java.security.AccessController.getContext;

public class MyContextWrapper extends ContextWrapper {

    public MyContextWrapper(Context base) {
        super(base);
    }

    @TargetApi(Build.VERSION_CODES.N)

    public static ContextWrapper wrap(Context context, String language) {
        Locale locale = new Locale(language);
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();

        if (VersionUtils.isAfter24()) {
            configuration.locale = locale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());

         //   configuration.setLocale(locale);

            //    LocaleList localeList = new LocaleList(locale);
            //    LocaleList.setDefault(localeList);
            //    configuration.setLocales(localeList);
            //     context = context.createConfigurationContext(configuration);
            Log.i(TAG, "VersionUtils.isAfter24()");

            Intent refresh = new Intent(context, SplashActivity.class);
            context.startActivity(refresh);

        } else if (VersionUtils.isAfter17()) {
            configuration.setLocale(locale);
            context = context.createConfigurationContext(configuration);
            Log.i(TAG, "VersionUtils.isAfter17()");
            Intent refresh = new Intent(context, SplashActivity.class);
            context.startActivity(refresh);

        } else {
            configuration.locale = locale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
            Log.i(TAG, "version else");
            Intent refresh = new Intent(context, SplashActivity.class);
            context.startActivity(refresh);
        }

        return new ContextWrapper(context);
    }

    public static void setLocalLanguageType(Context context, String autostatus) {
        saveString(context, "LocalLanguageType", autostatus);
    }

    public static String getLocalLanguageType(Context context) {
        return getString(context, "LocalLanguageType","en");
    }

    private static String SP_NAME = "Config";
    private static SharedPreferences sp;

    public static void saveString(Context context, String key, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        sp.edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        return sp.getString(key, defValue);
    }

}