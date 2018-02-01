package com.example.android.boligchecker.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
/**
 * Created by Seoyeon on 13/12/2017.
 */

public class BoligContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private BoligContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.boligchecker";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.android.pets/pets/ is a valid path for
     * looking at pet data. content://com.example.android.pets/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_BOLIG = "bolig";


    /* Inner class that defines the table contents */
    public static class BoligEntry implements BaseColumns {

        /**
         * The content URI to access the BOLIG data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOLIG);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of BOLIG.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOLIG;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single BOLIG.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOLIG;


        public static final String TABLE_NAME = "bolig";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_ADDRESS_POST = "address_post";
        public static final String COLUMN_ADDRESS_CITY = "city";
        public static final String COLUMN_LANDLORD = "landlord";
        public static final String COLUMN_CONTRACT_LENGTH = "contract_length";
        public static final String COLUMN_CONTRACT_NOTICE = "contract_notice";
        public static final String COLUMN_PRICE = "rent_price";
        public static final String COLUMN_DEPOSIT = "deposit_price";



        public static boolean isValidAdress(String address) {
            if (address != null) {
                return true;
            }
            return false;
        }

    }

}
