package com.example.android.boligchecker.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.SplashActivity;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.base.MyContextWrapper;
import com.example.android.boligchecker.utilities.VersionUtils;

import java.util.Locale;
import java.util.prefs.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import butterknife.ButterKnife;

import static android.R.attr.button;
import static android.R.id.toggle;
import static android.content.Context.MODE_PRIVATE;
import static com.mikepenz.iconics.Iconics.TAG;


/**
 * Created by Seoyeon on 12/12/2017.
 */

public class SettingFragment extends BaseFragment {

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.setting, container, false);
        ButterKnife.bind(this, view);

        final Switch switch_nor = (Switch) view.findViewById(R.id.switch_nor);
        final Switch switch_dan = (Switch) view.findViewById(R.id.switch_dan);
        final Switch switch_eng = (Switch) view.findViewById(R.id.switch_eng);

        SharedPreferences sharedPrefs = getActivity().getSharedPreferences("com.example.android.boligchecker.fragments", MODE_PRIVATE);
        switch_nor.setChecked(sharedPrefs.getBoolean("norway", false));
        switch_dan.setChecked(sharedPrefs.getBoolean("denmark", false));
        switch_eng.setChecked(sharedPrefs.getBoolean("english", true));


        switch_nor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    switch_dan.setChecked(false);
                    switch_eng.setChecked(false);
                    Button button_language = (Button) view.findViewById(R.id.button_language);
                    button_language.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("com.example.android.boligchecker.fragments", MODE_PRIVATE).edit();
                            editor.putBoolean("norway", true);
                            editor.putBoolean("denmark", false);
                            editor.putBoolean("english", false);
                            editor.commit();
                            editor.apply();
                            //  setLocale(getActivity(), "no");
                             MyContextWrapper.setLocalLanguageType(getActivity(),"no");
                            MyContextWrapper.wrap(getActivity(), "no");
                        }
                    });
                }
            }
        });

        switch_dan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    switch_nor.setChecked(false);
                    switch_eng.setChecked(false);
                    Button button_language = (Button) view.findViewById(R.id.button_language);
                    button_language.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("com.example.android.boligchecker.fragments", MODE_PRIVATE).edit();
                            editor.putBoolean("norway", false);
                            editor.putBoolean("denmark", true);
                            editor.putBoolean("english", false);
                            editor.commit();
                            editor.apply();
                            //  setLocale(getActivity(), "da");
                              MyContextWrapper.setLocalLanguageType(getActivity(),"da");
                              MyContextWrapper.wrap(getActivity(), "da");
                            }
                    });

                }
            }
        });

        switch_eng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    switch_dan.setChecked(false);
                    switch_nor.setChecked(false);
                    Button button_language = (Button) view.findViewById(R.id.button_language);
                    button_language.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("com.example.android.boligchecker.fragments", MODE_PRIVATE).edit();
                            editor.putBoolean("norway", false);
                            editor.putBoolean("denmark", false);
                            editor.putBoolean("english", true);
                            editor.commit();
                            editor.apply();
                            //   setLocale(getActivity(), "en");
                              MyContextWrapper.setLocalLanguageType(getActivity(),"en");
                              MyContextWrapper.wrap(getActivity(), "en");
                           }
                    });
                }
            }
        });



        return view;
    }



    public void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        Intent refresh = new Intent(context, SplashActivity.class);

        startActivity(refresh);
        getActivity().finish();

    }




    @Override
    protected String getTitle() {
        return getString(R.string.setting);
    }


}
