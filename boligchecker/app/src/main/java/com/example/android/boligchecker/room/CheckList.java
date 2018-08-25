package com.example.android.boligchecker.room;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.widget.SimpleCursorAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.boligchecker.BoligActivity;
import com.example.android.boligchecker.BoligCursorAdapter;
import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BaseFragment;

import static android.R.attr.id;
import static android.R.attr.value;


/**
 * Created by Seoyeon on 22/12/2017.
 */

public class CheckList extends AppCompatActivity {

    private CheckListDataManager dbManager;
    private ItemCursorAdapter adapter;
    private ListView listView;


    final String[] from =  new String[]{TodoListSQLHelper._ID, TodoListSQLHelper.COLUMN_CL, TodoListSQLHelper.COLUMN_CL_BUTTON};

    final int[] to = new int[] { R.id.check_list_checkbox, R.id.check_list_text };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.check_list);
        setTitle(R.string.ch_title);


        dbManager = new CheckListDataManager(this);
        dbManager.open();
        Cursor cursor = dbManager.fetch();


        listView = (ListView) findViewById(R.id.check_list_view);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        adapter = new ItemCursorAdapter(this, R.layout.check_list_context, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        // OnCLickListiner For List Items
         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
              TextView descTextView = (TextView) view.findViewById(R.id.check_list_text);
         //     CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_list_checkbox);

             String desc = descTextView.getText().toString();
              Intent modify_intent = new Intent(getApplicationContext(), ModifyCountryActivity.class);
             modify_intent.putExtra("columncl", desc);
               startActivity(modify_intent);
          }
           });


    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_check_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.action_add) {

                Intent add_mem = new Intent(this, AddCountryActivity.class);
                startActivity(add_mem);

            }
            return super.onOptionsItemSelected(item);
    }



}
