package com.example.android.boligchecker;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.os.Handler;
import android.widget.ProgressBar;

//import timber.log.Timber;

/**
 * Created by Seoyeon on 14/12/2017.
 */

public class SplashActivity extends AppCompatActivity {

    /** Duration of wait **/
    private ProgressBar mProgress;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.splash);
        //    mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);

         /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

        // Start lengthy operation in a background thread
        //     new Thread(new Runnable() {
        //         public void run() {
        //         doWork();
        //         startApp();
        //        finish();
        //     }
        //    }).start();




    private void doWork() {
        for (int progress=0; progress<100; progress+=25) {
            try {
                Thread.sleep(1000);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
                //Timber.e(e.getMessage());
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }



    }

