package com.example.android.boligchecker;


import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebViewFragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.boligchecker.base.AddFragmentHandler;
import com.example.android.boligchecker.base.BackButtonSupportFragment;
import com.example.android.boligchecker.base.BaseActivity;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.base.MyContextWrapper;
import com.example.android.boligchecker.fragments.ContractFragment;
import com.example.android.boligchecker.fragments.HomeFragment;
import com.example.android.boligchecker.fragments.LegalFragment;
import com.example.android.boligchecker.fragments.MoveFragment;
import com.example.android.boligchecker.fragments.MysideFragment;
import com.example.android.boligchecker.fragments.RentFragment;
import com.example.android.boligchecker.fragments.SettingFragment;
import com.example.android.boligchecker.myside.MainMypage;


import net.skoumal.fragmentback.BackFragmentHelper;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnItemClick;

import static com.example.android.boligchecker.R.id.toolbar;
import static com.example.android.boligchecker.base.MyContextWrapper.wrap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static String TAG2 = "MyActivity";
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private AddFragmentHandler fragmentHandler;
    private FragmentManager fragmentManager;
    private ActionBarDrawerToggle drawerToggle;
    private ActionBarDrawerToggle toggle;

    final View.OnClickListener navigationBackPressListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fragmentManager.popBackStack();
        }
    };

    FragmentManager.OnBackStackChangedListener backStackListener = new FragmentManager.OnBackStackChangedListener() {
        @Override
        public void onBackStackChanged() {
            onBackStackChangedEvent();
        }
    };

    private void onBackStackChangedEvent() {
        syncDrawerToggleState();
    }

    private void syncDrawerToggleState() {
        drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawerToggle == null) {
            return;
        }
        if (fragmentManager.getBackStackEntryCount() > 1) {
            //open nav menu drawer
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(drawerToggle.getToolbarNavigationClickListener());
            // drawerToggle.setDrawerIndicatorEnabled(false);
            //   drawerToggle.setToolbarNavigationClickListener(navigationBackPressListener); //pop backstack
            Log.i(TAG2, "Toolbar: open nav menu drawer");
        } else {
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.setToolbarNavigationClickListener(drawerToggle.getToolbarNavigationClickListener()); //open nav menu drawer
            Log.i(TAG2, "Toolbar: open nav menu drawer");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String localLanguageType = MyContextWrapper.getLocalLanguageType(this);
        Log.i(TAG2, "localLanguageType" + localLanguageType);
        if ("no".equals(localLanguageType)) {
            Locale myLocale = new Locale("no");
            Resources res = this.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);


        } else if ("da".equals(localLanguageType)){
            Locale myLocale = new Locale("da");
            Resources res = this.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
        }
        else{
            Locale myLocale = new Locale("en");
            Resources res = this.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);

        }

        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        fragmentHandler = new AddFragmentHandler(fragmentManager);
        fragmentManager.addOnBackStackChangedListener(backStackListener);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showHome();


    }


    @Override
    protected void onDestroy() {
        fragmentManager.removeOnBackStackChangedListener(backStackListener);
        fragmentManager = null;
        super.onDestroy();
    }

    public void add(BaseFragment fragment) {
        fragmentHandler.add(fragment);}

    private void showHome() {
        add(HomeFragment.newInstance());
    }


    @Override
     public void onBackPressed() {
        Log.i(TAG2, "onBackPressed: Start");

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            Log.i(TAG2, "onBackPressed: Drawerclose");
        }
        //  else { super.onBackPressed();}

        //   Log.i(TAG2, "onBackPressed: BoligFragment");
        //  BoligFragment bf = new BoligFragment();
        //  bf.getActivity();
        //   bf.newCardChanged();

        else {
            //close the activity if back is pressed on the root fragment
            if (fragmentManager.getBackStackEntryCount() == 1) {
                Log.i(TAG2, "onBackPressed: getBackStackEntryCount() == 0");
                finish();
            }


                if (sendBackPressToFragmentOnTop()) {
                    // fragment on top consumed the back press
                    Log.i(TAG2, "onBackPressed: sendBackPressToFragmentOnTop");
                    return;
                }

                //let the android system handle the back press, usually by popping the fragment
                //   super.onBackPressed();

                int count = getFragmentManager().getBackStackEntryCount();
                if (count == 0) {
                    super.onBackPressed();
                } else {
                    getFragmentManager().popBackStack();
                }



        }
    }


    public boolean sendBackPressToFragmentOnTop() {
        BaseFragment fragmentOnTop = fragmentHandler.getCurrentFragment();
        if (fragmentOnTop == null) {
            return false;
        }
        if (!(fragmentOnTop instanceof BackButtonSupportFragment)) {
            return false;
        }
        boolean consumedBackPress = ((BackButtonSupportFragment) fragmentOnTop).onBackPressed();
        return consumedBackPress;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.home) {
         //   drawer.openDrawer(GravityCompat.START);
        }
        else if (id == R.id.nav_zero_fragment) {
            fragment = new MainMypage();
        } else if (id == R.id.nav_first_fragment) {
            fragment = new HomeFragment();
        } else if (id == R.id.nav_second_fragment) {
            fragment = new RentFragment();
        } else if (id == R.id.nav_third_fragment) {
            fragment = new ContractFragment();
        } else if (id == R.id.nav_move_fragment) {
            fragment = new MoveFragment();
        } else if (id == R.id.nav_fourth_fragment) {
            fragment = new LegalFragment();
        } else if (id == R.id.setting_fragment) {
            fragment = new SettingFragment();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
