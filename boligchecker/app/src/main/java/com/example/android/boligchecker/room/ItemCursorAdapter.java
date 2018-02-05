package com.example.android.boligchecker.room;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.boligchecker.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.checked;

/**
 * Created by Seoyeon on 04/02/2018.
 */

public class ItemCursorAdapter extends SimpleCursorAdapter {

   // private Context mContext;
   // private ArrayList<String> id;
  //  private ArrayList<String> firstName;
 //   private ArrayList<Boolean> ConFirm; //better name it confirm, it's one word

    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    //  private final LayoutInflater inflater;
    boolean[] checked;

    List<Integer> selectedItemsPositions;


    private CheckListDataManager dbManager;

    public ItemCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public View newView(Context _context, Cursor _cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.check_list_context, parent, false);
        return view;
    }


    @Override
    public void bindView(View view, final Context Context, Cursor cursor) {

        String title = cursor.getString(cursor.getColumnIndex(TodoListSQLHelper.COLUMN_CL));
        TextView formtitle = (TextView) view.findViewById(R.id.check_list_text);
        formtitle.setText(title);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_list_checkbox);
        final int position = cursor.getPosition();
        checkBox.setOnCheckedChangeListener(null);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Toast.makeText(Context,"Hello Javatpoint" + position ,Toast.LENGTH_SHORT).show();

                dbManager = new CheckListDataManager(Context);
                dbManager.open();
                dbManager.update(position, 1);

            }
        });


    }

    /* ListView Item Click Listener */
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v.findViewById(R.id.check_list_checkbox);
        //  cb.setOnCheckedChangeListener(null);
        cb.toggle();
        // cb.setOnCheckedChangeListener(this);
        //int position = (Integer)v.getTag();
        // checked[position] = cb.isChecked(); } /* CheckBox changed Listener */
        }

    private void init() {
        checked = new boolean[getCount()];
    }




}
