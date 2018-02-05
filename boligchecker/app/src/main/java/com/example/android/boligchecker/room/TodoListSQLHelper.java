package com.example.android.boligchecker.room;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


/**
 * Created by Seoyeon on 03/02/2018.
 */

public class TodoListSQLHelper extends SQLiteOpenHelper {


    public static final String _ID = BaseColumns._ID;

    public static final String TABLE_NAME = "todolist";
    public static final String COLUMN_CL = "listitems";
    public static final String  COLUMN_CL_BUTTON = "ischecked";

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "checklistdata.db";

    public TodoListSQLHelper(Context context) {
        //1 is check list database version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
                    _ID +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CL + " TEXT NOT NULL, " +
                    COLUMN_CL_BUTTON + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {

        sqlDB.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqlDB);
    }

    public void onDowngrade(SQLiteDatabase sqlDB, int oldVersion, int newVersion) {
        onUpgrade(sqlDB, oldVersion, newVersion);
    }


}
