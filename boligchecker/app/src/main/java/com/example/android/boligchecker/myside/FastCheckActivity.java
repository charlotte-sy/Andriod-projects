package com.example.android.boligchecker.myside;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.utilities.NetworkUtil;

import java.io.IOException;
import java.net.URL;

import static android.R.attr.button;


public class FastCheckActivity  extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView mSearchResultsTextView;
    private WebView mSearchWebView;
    private Button fastcheckButton;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fast_check);

        setTitle(getString(R.string.title_fast_check));

        mSearchBoxEditText = (EditText) findViewById(R.id.fast_check_address);
        mSearchResultsTextView = (TextView) findViewById(R.id.fast_check_result);
        mSearchWebView = (WebView)findViewById(R.id.fast_check_webview_result);
        fastcheckButton = (Button) findViewById(R.id.fast_check_webpage_button);
            fastcheckButton.setOnClickListener(fastchecklistener);

    }

    private View.OnClickListener fastchecklistener = new View.OnClickListener() {
     @Override
     public void onClick(View v) {
          makeGithubSearchQuery();
          mSearchWebView.loadUrl("http://boligejer.dk/Start/0/2");
                 Editable addressText = mSearchBoxEditText.getText();
                 Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://stackoverflow.com/"));
                intent.putExtra(SearchManager.QUERY, mSearchBoxEditText.getText().toString());
                  startActivity(intent);

      }
      };

    /**
     * This method retrieves the search text from the EditText, constructs the URL
     * that URL in a TextView, and finally fires off an AsyncTask to perform the GET request using
     * our (not yet created) {@link GithubQueryTask}
     */
      private void makeGithubSearchQuery() {
         String githubQuery = mSearchBoxEditText.getText().toString();
          URL githubSearchUrl = NetworkUtil.buildUrl(githubQuery);

        new GithubQueryTask().execute(githubSearchUrl);
      }



    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

       @Override
       protected String doInBackground(URL... params) {
        URL searchUrl = params[0];
         String githubSearchResults = null;

          try{
             githubSearchResults = NetworkUtil.getResponseFromHttpUrl(searchUrl);
           }
           catch (IOException e){
              e.printStackTrace();
           }
          return githubSearchResults;
      }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            if(githubSearchResults != null && !githubSearchResults.equals("")){

         //      mSearchResultsTextView.setText(githubSearchResults);


          }
        }


      }




}
