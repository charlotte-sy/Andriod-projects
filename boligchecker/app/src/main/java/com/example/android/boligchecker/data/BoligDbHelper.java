package com.example.android.boligchecker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.boligchecker.data.BoligContract.BoligEntry;

/**
 * Created by Seoyeon on 13/12/2017.
 */

public class BoligDbHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bolig.db";


    public BoligDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOLIG_TABLE);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



    private static final String SQL_CREATE_BOLIG_TABLE =
            "CREATE TABLE " + BoligEntry.TABLE_NAME + " (" +
                    BoligEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BoligEntry.COLUMN_ADDRESS + " TEXT," +
                    BoligEntry.COLUMN_ADDRESS_POST + " TEXT," +
                    BoligEntry.COLUMN_ADDRESS_CITY + " TEXT," +
                    BoligEntry.COLUMN_LANDLORD  + " TEXT NOT NULL," +
                    BoligEntry.COLUMN_CONTRACT_LENGTH + " INTEGER NOT NULL," +
                    BoligEntry.COLUMN_CONTRACT_NOTICE + " INTEGER NOT NULL," +
                    BoligEntry.COLUMN_PRICE + " INTEGER NOT NULL," +
                    BoligEntry.COLUMN_DEPOSIT +" INTEGER NOT NULL DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BoligEntry.TABLE_NAME;


}
