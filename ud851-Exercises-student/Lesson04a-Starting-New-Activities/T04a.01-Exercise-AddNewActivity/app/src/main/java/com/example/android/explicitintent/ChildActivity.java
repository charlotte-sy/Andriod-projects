package com.example.android.explicitintent;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Seoyeon on 05/01/2018.
 */

public class ChildActivity extends AppCompatActivity {

    public TextView mtext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);


        mtext = (TextView) findViewById(R.id.tv_display);
    }

}
