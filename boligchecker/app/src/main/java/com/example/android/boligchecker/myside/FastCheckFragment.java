package com.example.android.boligchecker.myside;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.fragments.ContractFragment;
import com.example.android.boligchecker.fragments.MysideFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.io.*;
import java.net.*;

import static android.R.id.button1;
import static android.R.id.button2;
import static android.R.id.button3;
import static com.example.android.boligchecker.data.BoligProvider.LOG_TAG;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.boligchecker.utilities.NetworkUtil;

import java.io.IOException;
import java.net.URL;


/**
 * Created by Seoyeon on 16/12/2017.
 */

public class FastCheckFragment extends BaseFragment implements  View.OnClickListener {

    URL url = createUrl("http://www.yahoo.com/");




    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fastCheckView = inflater.inflate(R.layout.fast_check, container, false);

    //    Button button1 = (Button) fastCheckView.findViewById(R.id.webpage_button);

     //   button1.setOnClickListener(this);

        return fastCheckView;
    }

    @Override
    public void onClick(View v) {

    }




    @Override
    protected String getTitle() {
        return getString(R.string.fast_safety_check);
    }

}
