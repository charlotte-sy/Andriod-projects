package com.example.android.boligchecker.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


import com.example.android.boligchecker.data.BoligContract.BoligEntry;

import static android.R.attr.name;

/**
 * Created by Seoyeon on 13/12/2017.
 */

public class BoligProvider extends ContentProvider {

    /** URI matcher code for the content URI for the pets table */
    private static final int BOLIG = 100;
    /** URI matcher code for the content URI for a single pet in the pets table */
    private static final int BOLIG_ID = 101;

    /** URI matcher object to match a context URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        // The content URI of the form "content://com.example.android.pets/pets" will map to the
        // integer code {@link #PETS}. This URI is used to provide access to MULTIPLE rows
        // of the pets table.
        sUriMatcher.addURI(BoligContract.CONTENT_AUTHORITY, BoligContract.PATH_BOLIG, BOLIG);

        // The content URI of the form "content://com.example.android.pets/pets/#" will map to the
        // integer code {@link #PETS_ID}. This URI is used to provide access to ONE single row
        // of the pets table.

        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        // For example, "content://com.example.android.pets/pets/3" matches, but
        // "content://com.example.android.pets/pets" (without a number at the end) doesn't match.
        sUriMatcher.addURI(BoligContract.CONTENT_AUTHORITY, BoligContract.PATH_BOLIG + "/#", BOLIG_ID);
    }


    /** Tag for the log messages */
    public static final String LOG_TAG = BoligProvider.class.getSimpleName();

    /**
     * Initialize the provider and the database helper object.
     */

    //** Database Helper object
    private BoligDbHelper mDbHelper;


    @Override
    public boolean onCreate() {
        // TODO: Create and initialize a PetDbHelper object to gain access to the pets database.
        // Make sure the variable is a global variable, so it can be referenced from other
        // ContentProvider methods.

        mDbHelper = new BoligDbHelper(getContext());

        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case BOLIG:
                // For the PETS code, query the pets table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the pets table.
                cursor = database.query(BoligEntry.TABLE_NAME, projection, null, null,
                        null, null, sortOrder);

                break;
            case BOLIG_ID:
                // For the PET_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.pets/pets/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = BoligEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(BoligEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);}

        // so we know what content URI the Cursor was created
        // if the data at this URI changes, then we know we need to update the cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOLIG:
                return insertPet(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }}

    // Too long code from top, so is made for extra coding

    private Uri insertPet (Uri uri, ContentValues values){

        // Check that the name is not null
        String address = values.getAsString(BoligEntry.COLUMN_ADDRESS);
        if (address == null) {
            throw new IllegalArgumentException("Contract requires address");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer post = values.getAsInteger(BoligEntry.COLUMN_ADDRESS_POST);
        if (post != null && post < 0) {
            throw new IllegalArgumentException("Contract requires valid post code");
        }

        // Check that the name is not null
        String cityname = values.getAsString(BoligEntry.COLUMN_ADDRESS_CITY);
        if (cityname == null) {
            throw new IllegalArgumentException("Contract requires city name");
        }

        // Check that the name is not null
        String landlord = values.getAsString(BoligEntry.COLUMN_LANDLORD);
        if (landlord == null) {
            throw new IllegalArgumentException("Contract requires a landlord name");
        }


        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer rentlength = values.getAsInteger(BoligEntry.COLUMN_CONTRACT_LENGTH);
        if (rentlength != null && rentlength < 0) {
            throw new IllegalArgumentException("Contract requires valid rent length");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer notice = values.getAsInteger(BoligEntry.COLUMN_CONTRACT_NOTICE);
        if (notice != null && notice < 0) {
            throw new IllegalArgumentException("Contract requires valid notice period");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer price = values.getAsInteger(BoligEntry.COLUMN_PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Contract requires valid monthly expenses");
        }

        // If the weight is provided, check that it's greater than or equal to 0 kg
        Integer deposit = values.getAsInteger(BoligEntry.COLUMN_DEPOSIT);
        if (deposit != null && deposit < 0) {
            throw new IllegalArgumentException("Contract requires valid deposit");
        }


        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Insert the new pet with the given values
        long id = database.insert(BoligEntry.TABLE_NAME, null, values);
        // If the ID is -1, then the insertion failed. Log an error and return null.
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }


        //-------------loader -------------------
        //Notify all listeners that the data has changed for the pet content URI
        //uri:contect://com.example.android.pets/pets
        getContext().getContentResolver().notifyChange(uri,null);


        // Return the new URI with the ID (of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }





    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOLIG:
                return updateBolig(uri, contentValues, selection, selectionArgs);
            case BOLIG_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = BoligEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateBolig(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * Update pets in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more pets).
     * Return the number of rows that were successfully updated.
     */
    private int updateBolig(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // If the {@link PetEntry#COLUMN_PET_NAME} key is present,
        // check that the name value is not null.


        if (values.containsKey(BoligEntry.COLUMN_ADDRESS)) {
            String name = values.getAsString(BoligEntry.COLUMN_ADDRESS);
            if (name == null) {
                throw new IllegalArgumentException("Contract requires address");
            }
        }


        // No need to check the breed, any value is valid (including null).

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();



        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(BoligEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;

    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOLIG:
                rowsDeleted = database.delete(BoligEntry.TABLE_NAME, selection, selectionArgs);
                return database.delete(BoligEntry.TABLE_NAME, selection, selectionArgs);
            case BOLIG_ID:
                // Delete a single row given by the ID in the URI
                selection = BoligEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(BoligEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // If 1 or more rows were deleted, then notify all listeners that the data at the
        // given URI has changed
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows deleted
        return rowsDeleted;

    }



    /**
     * Returns the MIME type of data for the content URI.
     *  return a String that describes the type of the data stored at the input Uri.
     */

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BOLIG:
                return BoligEntry.CONTENT_LIST_TYPE;
            case BOLIG_ID:
                return BoligEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }






}
