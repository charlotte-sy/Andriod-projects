package com.example.android.boligchecker.room;

/**
 * Created by Seoyeon on 04/02/2018.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.R.attr.name;


public class CheckListDataManager {

    private TodoListSQLHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public CheckListDataManager(Context c) {
        context = c;
    }

    public CheckListDataManager open() throws SQLException {
        dbHelper = new TodoListSQLHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String columncl, String clbutton) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(TodoListSQLHelper.COLUMN_CL, columncl);
        contentValue.put(TodoListSQLHelper.COLUMN_CL_BUTTON, clbutton);
        database.insert(TodoListSQLHelper.TABLE_NAME, null, contentValue);
    }


    public Cursor fetch() {
        String[] columns = new String[] { TodoListSQLHelper._ID, TodoListSQLHelper.COLUMN_CL, TodoListSQLHelper.COLUMN_CL_BUTTON};
        Cursor cursor = database.query(TodoListSQLHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String columncl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListSQLHelper.COLUMN_CL, columncl);

        int i = database.update(TodoListSQLHelper.TABLE_NAME, contentValues, TodoListSQLHelper._ID + " = " + _id, null);
        return i;
    }

    public int update(long _id, Integer number) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TodoListSQLHelper.COLUMN_CL_BUTTON, String.valueOf(number));

        int i = database.update(TodoListSQLHelper.TABLE_NAME, contentValues, TodoListSQLHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(TodoListSQLHelper.TABLE_NAME, TodoListSQLHelper._ID + "=" + _id, null);
    }

}
