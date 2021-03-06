package me.tatarka.simplefragment.sample;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

import me.tatarka.simplefragment.SimpleFragment;
import me.tatarka.simplefragment.SimpleFragmentAppCompatActivity;
import me.tatarka.simplefragment.SimpleFragmentIntent;
import me.tatarka.simplefragment.widget.SimpleFragmentPagerAdapter;

public class MainActivity extends SimpleFragmentAppCompatActivity {
    private static final List<Class<? extends SimpleFragment>> FRAGMENTS = Arrays.asList(
            FragmentFromLayout.class,
            FragmentWithBackStack.class,
            FragmentDialogs.class,
            FragmentActivityForResult.class
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new Adapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class Adapter extends SimpleFragmentPagerAdapter {
        public Adapter() {
            super(MainActivity.this);
        }

        @Override
        public SimpleFragmentIntent getItem(int position) {
            return SimpleFragmentIntent.of(FRAGMENTS.get(position));
        }

        @Override
        public int getCount() {
            return FRAGMENTS.size();
        }
    }
}
