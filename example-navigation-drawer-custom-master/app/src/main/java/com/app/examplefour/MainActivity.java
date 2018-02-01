package com.app.examplefour;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.examplefour.adapter.DrawerItem;
import com.app.examplefour.adapter.DrawerSpaceItem;
import com.app.examplefour.fragments.FragmentPageOne;
import com.app.examplefour.fragments.FragmentPageThree;
import com.app.examplefour.fragments.FragmentPageTwo;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Drawer result = null;
    private ArrayList<String> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Toolbar
        setSupportActionBar(mToolbar);

        fragmentList = new ArrayList<>();
        fragmentList.add("fragmentPageOne");
        fragmentList.add("fragmentPageTwo");
        fragmentList.add("fragmentPageThree");

        createNavigationDrawer(savedInstanceState);

        selectFragment(result.getCurrentSelectedPosition(), fragmentList.get(result.getCurrentSelectedPosition() - 1));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                result.openDrawer();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createNavigationDrawer(Bundle savedInstanceState) {
        // Apply color to the background image
        Drawable background = ContextCompat.getDrawable(this, R.drawable.background);
        background.setColorFilter(ContextCompat.getColor(this, R.color.background), PorterDuff.Mode.SRC_ATOP);

        // Get screen width
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dpWidth = Math.round(displayMetrics.widthPixels / displayMetrics.density);

        // Create Drawer
        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(MainActivity.this)
                .withSelectedItem(1)
                .withToolbar(mToolbar)
                .withDrawerWidthDp(dpWidth)
                .withSliderBackgroundDrawable(background)
                .withStickyFooter(R.layout.adapter_drawer_login_item)
                .withStickyFooterShadow(false)
                .addDrawerItems(
                        new DrawerSpaceItem().withHeightDp(16),
                        new DrawerItem().withName("Home").withIdentifier(1),
                        new DrawerItem().withName("Locations").withIdentifier(2),
                        new DrawerItem().withName("Favorites").withIdentifier(3),
                        new DrawerItem().withName("Wishlist").withIdentifier(3),
                        new DrawerItem().withName("Profile").withIdentifier(2),
                        new DrawerItem().withName("Settings").withIdentifier(1)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        if (iDrawerItem != null) {
                            selectFragment((int) iDrawerItem.getIdentifier(), fragmentList.get((int) iDrawerItem.getIdentifier() - 1));
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .withActionBarDrawerToggleAnimated(true);

        result = drawerBuilder.build();

        // Footer Events
        setupFooterEvents();
    }

    private void setupFooterEvents() {
        result.getStickyFooter().findViewById(R.id.item_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.closeDrawer();
                result.setSelection(-1);
                Toast.makeText(MainActivity.this, "Log In", Toast.LENGTH_SHORT).show();
            }
        });

        result.getStickyFooter().findViewById(R.id.item_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.closeDrawer();
                result.setSelection(-1);
                Toast.makeText(MainActivity.this, "Register", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void selectFragment(int position, String fragmentTag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);

        if (fragment == null) {
            switch (position) {
                case 1:
                    fragment = FragmentPageOne.newInstance();
                    break;
                case 2:
                    fragment = FragmentPageTwo.newInstance();
                    break;
                case 3:
                    fragment = FragmentPageThree.newInstance();
                    break;
            }
        }

        if (fragment != null)
            replaceFragment(getSupportFragmentManager(), R.id.frame_container, fragment, fragmentTag);
    }

    private void replaceFragment(FragmentManager manager, int parent, Fragment fragment, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(parent, fragment, tag);
        ft.commit();
    }
}
