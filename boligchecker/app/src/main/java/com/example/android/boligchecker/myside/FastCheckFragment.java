package com.example.android.boligchecker.myside;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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

public class FastCheckFragment extends BaseFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fastCheckView = inflater.inflate(R.layout.fast_check_fragment, container, false);

        final WebView myWebView = (WebView) fastCheckView.findViewById(R.id.fast_check_webview);
        Button webViewButton = (Button) fastCheckView.findViewById(R.id.fast_check_webpage_button);

        webViewButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myWebView.getSettings().setJavaScriptEnabled(true);
                myWebView.loadUrl("http://boligejer.dk");

            }
        }));


        return fastCheckView;
    }



    @Override
    protected String getTitle() {
        return getString(R.string.fast_safety_check);
    }

}
