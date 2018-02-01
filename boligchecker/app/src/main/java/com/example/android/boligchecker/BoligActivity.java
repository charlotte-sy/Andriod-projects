package com.example.android.boligchecker;

import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.CursorAdapter;

import com.example.android.boligchecker.data.BoligContract;
import com.example.android.boligchecker.data.BoligContract.BoligEntry;
import com.example.android.boligchecker.data.BoligDbHelper;
import com.example.android.boligchecker.data.BoligProvider;

import static android.R.attr.id;
import static android.R.attr.name;
import static com.example.android.boligchecker.R.id.text_view_pet;
import static com.example.android.boligchecker.data.BoligContract.BASE_CONTENT_URI;
import static com.example.android.boligchecker.data.BoligContract.PATH_BOLIG;

import android.util.Log;
import android.widget.Toast;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.boligchecker.data.BoligContract;
import com.example.android.boligchecker.data.BoligContract.BoligEntry;
import com.example.android.boligchecker.data.BoligDbHelper;

//Load the data
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;


/**
 * Created by Seoyeon on 13/12/2017.
 */

public class BoligActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks <Cursor> {


    //load data
    /**
     * Identifier for the pet data loader
     */
    private static final int EXISTING_BOLIG_LOADER = 0;

    /**
     * Content URI for the existing pet (null if it's a new pet)
     */
    private Uri mCurrentBoligUri;

    //---------------------------------------------

    /**
     * EditText field to enter the pet's name
     */
    private EditText mAddressEditText;

    /**
     * EditText field to enter the pet's breed
     */
    private EditText mPostEditText;

    /**
     * EditText field to enter the pet's weight
     */
    private EditText mCityEditText;

    private EditText mLandlordEditText;

    private EditText mLengthEditText;

    private EditText mNoticeEditText;

    private EditText mRentFeeEditText;

    private EditText mDepositEditText;


    /**
     * Listen for whether changes were made. For The Desired behavior
     */
    private boolean mBoligHasChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contract_paper);


        //get Uri from catalogactivity
        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new pet or editing an existing one.
        Intent intent = getIntent();
        mCurrentBoligUri = intent.getData();


        // If the intent DOES NOT contain a pet content URI, then we know that we are
        // creating a new pet.
        if (mCurrentBoligUri == null) {
            // This is a new pet, so change the app bar to say "Add a Pet"
            setTitle(getString(R.string.title_new_bolig));

            // Invalidate the options menu, so the "Delete" menu option can be hidden.
            // (It doesn't make sense to delete a pet that hasn't been created yet.)
            invalidateOptionsMenu();
        } else {
            // Otherwise this is an existing pet, so change app bar to say "Edit Pet"
            setTitle(getString(R.string.title_edit_bolig));

            // Initialize a loader to read the pet data from the database
            // and display the current values in the editor
            getLoaderManager().initLoader(EXISTING_BOLIG_LOADER, null, this);
        }


        // Find all relevant views that we will need to read user input from
        mAddressEditText = (EditText) findViewById(R.id.address_line);
        mPostEditText = (EditText) findViewById(R.id.address_post);
        mCityEditText = (EditText) findViewById(R.id.address_city);
        mLandlordEditText = (EditText) findViewById(R.id.landlord);
        mLengthEditText = (EditText) findViewById(R.id.lease_length);
        mNoticeEditText = (EditText) findViewById(R.id.notice);
        mRentFeeEditText = (EditText) findViewById(R.id.monthly_rent);
        mDepositEditText = (EditText) findViewById(R.id.deposit);


        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mAddressEditText.setOnTouchListener(mTouchListener);
        mPostEditText.setOnTouchListener(mTouchListener);
        mCityEditText.setOnTouchListener(mTouchListener);
        mLandlordEditText.setOnTouchListener(mTouchListener);
        mLengthEditText.setOnTouchListener(mTouchListener);
        mNoticeEditText.setOnTouchListener(mTouchListener);
        mRentFeeEditText.setOnTouchListener(mTouchListener);
        mDepositEditText.setOnTouchListener(mTouchListener);




    }




    /**
     * You can check if changes were made by adding a OnTouchListener
     * For The Desired behavior
     */

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mBoligHasChanged = true;
            return false;
        }
    };


    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }




    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mCurrentBoligUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveBolig();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the pet hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mBoligHasChanged) {
                    NavUtils.navigateUpFromSameTask(BoligActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(BoligActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the pet hasn't changed, continue with handling back button press
        if (!mBoligHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }


    /**
     * Get user input from editor and save pet into database.
     */
    private void saveBolig() {
        //적혀있는 글자를 불러온다
        // @trim = erase any space
        String addressString = mAddressEditText.getText().toString().trim();
        String postString = mPostEditText.getText().toString().trim();
        String cityString = mCityEditText.getText().toString().trim();
        String landlordString = mLandlordEditText.getText().toString().trim();
        String lengthString = mLengthEditText.getText().toString().trim();
        String noticeString = mNoticeEditText.getText().toString().trim();
        String rentfeeString = mRentFeeEditText.getText().toString().trim();
        String depositString = mDepositEditText.getText().toString().trim();


        //-------------------Prevent null input -

        // Check if this is supposed to be a new pet
        // and check if all the fields in the editor are blank
        if (mCurrentBoligUri == null &&
                TextUtils.isEmpty(addressString) && TextUtils.isEmpty(postString) &&
                TextUtils.isEmpty(cityString) && TextUtils.isEmpty(landlordString)
                && TextUtils.isEmpty(lengthString) && TextUtils.isEmpty(noticeString)
                && TextUtils.isEmpty(rentfeeString) && TextUtils.isEmpty(depositString)) {
            // Since no fields were modified, we can return early without creating a new pet.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }


        // Create database helper
        //   PetDbHelper mDbHelper = new PetDbHelper(this);

        // Gets the database in write mode
        //  SQLiteDatabase db = mDbHelper.getWritableDatabase();


        //새로운 비어있는 값 생성 안드로이드 함수 이용
        ContentValues values = new ContentValues();
        //원하는 값들을 넣어준다
        values.put(BoligEntry.COLUMN_ADDRESS, addressString);
        values.put(BoligEntry.COLUMN_ADDRESS_POST, postString);
        values.put(BoligEntry.COLUMN_ADDRESS_CITY, cityString);
        values.put(BoligEntry.COLUMN_LANDLORD, landlordString);


        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int length = 0;
        if (!TextUtils.isEmpty(lengthString)) {
            length = Integer.parseInt(lengthString);
        }
        values.put(BoligEntry.COLUMN_CONTRACT_LENGTH, length);


        int notice = 0;
        if (!TextUtils.isEmpty(noticeString)) {
            notice = Integer.parseInt(noticeString);
        }
        values.put(BoligEntry.COLUMN_CONTRACT_NOTICE, notice);

        int price = 0;
        if (!TextUtils.isEmpty(rentfeeString)) {
            price = Integer.parseInt(rentfeeString);
        }
        values.put(BoligEntry.COLUMN_PRICE, price);

        int deposit = 0;
        if (!TextUtils.isEmpty(depositString)) {
            deposit = Integer.parseInt(depositString);
        }
        values.put(BoligEntry.COLUMN_DEPOSIT, deposit);

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentBoligUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getContentResolver().insert(BoligEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentBoligUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Insert a new row for pet in the database, returning the ID of that new row.
        //  long newRowId = db.insert(PetEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        //   if (newRowId == -1) {
        // If the row ID is -1, then there was an error with insertion.
        //        Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        //  } else {
        // Otherwise, the insertion was successful and we can display a toast with the row ID.
        //     Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();

    }


//------------------------Load the data

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                BoligEntry._ID,
                BoligEntry.COLUMN_ADDRESS,
                BoligEntry.COLUMN_ADDRESS_POST,
                BoligEntry.COLUMN_ADDRESS_CITY,
                BoligEntry.COLUMN_LANDLORD,
                BoligEntry.COLUMN_CONTRACT_LENGTH,
                BoligEntry.COLUMN_CONTRACT_NOTICE,
                BoligEntry.COLUMN_PRICE,
                BoligEntry.COLUMN_DEPOSIT};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentBoligUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Bail early if the cursor is null or there is less than 1 row in the cursor
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int addressColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_ADDRESS);
            int postColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_ADDRESS_POST);
            int cityColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_ADDRESS_CITY);
            int landlordColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_LANDLORD);
            int lengthColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_CONTRACT_LENGTH);
            int noticeColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_CONTRACT_NOTICE);
            int priceColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_PRICE);
            int depositColumnIndex = cursor.getColumnIndex(BoligEntry.COLUMN_DEPOSIT);

            // Extract out the value from the Cursor for the given column index
            String address = cursor.getString(addressColumnIndex);
            String post = cursor.getString(postColumnIndex);
            String city = cursor.getString(cityColumnIndex);
            String landlord = cursor.getString(landlordColumnIndex);
            int length = cursor.getInt(lengthColumnIndex);
            int notice = cursor.getInt(noticeColumnIndex);
            int price = cursor.getInt(priceColumnIndex);
            int deposit = cursor.getInt(depositColumnIndex);

            // Update the views on the screen with the values from the database
            mAddressEditText.setText(address);
            mPostEditText.setText(post);
            mCityEditText.setText(city);
            mLandlordEditText.setText(landlord);
            mLengthEditText.setText(Integer.toString(length));
            mNoticeEditText.setText(Integer.toString(notice));
            mRentFeeEditText.setText(Integer.toString(price));
            mDepositEditText.setText(Integer.toString(deposit));

        }
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mAddressEditText.setText("");
        mPostEditText.setText("");
        mCityEditText.setText("");
        mLandlordEditText.setText("");
        mLengthEditText.setText("");
        mNoticeEditText.setText("");
        mRentFeeEditText.setText("");
        mDepositEditText.setText("");

    }

    /**
     * Prompt the user to confirm that they want to delete this pet.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteBolig();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteBolig() {
        // Only perform the delete if this is an existing pet.
        if (mCurrentBoligUri != null) {
            // Call the ContentResolver to delete the pet at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentPetUri
            // content URI already identifies the pet that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentBoligUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }





}
