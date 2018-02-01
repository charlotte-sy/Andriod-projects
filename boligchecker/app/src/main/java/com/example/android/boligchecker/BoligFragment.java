package com.example.android.boligchecker;

/**
 * Created by Seoyeon on 14/12/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.boligchecker.base.BackButtonSupportFragment;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.data.BoligContract;
import com.example.android.boligchecker.fragments.MysideFragment;
import com.example.android.boligchecker.myside.MainMypage;

import net.skoumal.fragmentback.BackFragment;

import static android.R.attr.fragment;
import static com.mikepenz.iconics.Iconics.TAG;
import static net.skoumal.fragmentback.BackFragment.NORMAL_BACK_PRIORITY;

/**
 * Created by Seoyeon on 14/12/2017.
 */

public class BoligFragment extends BaseFragment implements LoaderManager.LoaderCallbacks <Cursor> {


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

    private EditText mAddressEditText;

    private EditText mPostEditText;

    private EditText mCityEditText;

    private EditText mLandlordEditText;

    private EditText mLengthEditText;

    private EditText mNoticeEditText;

    private EditText mRentFeeEditText;

    private EditText mDepositEditText;

    /**
     * Listen for whether changes were made. For The Desired behavior
     */
    public boolean mBoligHasChanged = false;

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume: D");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: D");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: D");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: D");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: D");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: D");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
       // newCardChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.contract_paper, container, false);

        //get Uri from catalogactivity
        // Examine the intent that was used to launch this activity,
        // in order to figure out if we're creating a new pet or editing an existing one.
        Intent intent = getActivity().getIntent();
        mCurrentBoligUri = intent.getData();


        // Find all relevant views that we will need to read user input from
        mAddressEditText = (EditText) v.findViewById(R.id.address_line);
        mPostEditText = (EditText) v.findViewById(R.id.address_post);
        mCityEditText = (EditText) v.findViewById(R.id.address_city);
        mLandlordEditText = (EditText) v.findViewById(R.id.landlord);
        mLengthEditText = (EditText) v.findViewById(R.id.lease_length);
        mNoticeEditText = (EditText) v.findViewById(R.id.notice);
        mRentFeeEditText = (EditText) v.findViewById(R.id.monthly_rent);
        mDepositEditText = (EditText) v.findViewById(R.id.deposit);


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

        return v;
    }


    /**
     * You can check if changes were made by adding a OnTouchListener
     * For The Desired behavior
     */

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mBoligHasChanged = true;
            Log.i(TAG, "OnTouchListener" + mBoligHasChanged);
            return false;
        }
    };


//private before
    public void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.

        Log.i(TAG, "showUnsavedChangesDialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem someMenuItem = menu.findItem(R.id.action_delete);
        someMenuItem.setVisible(false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        inflater.inflate(R.menu.menu_editor, menu);
        return;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveBolig();
                // Exit activity
                getActivity().getSupportFragmentManager().popBackStack();
                //        FragmentManager fragmentManager = getFragmentManager();
            //    fragmentManager.popBackStack("D",2);
                //         FragmentTransaction ft = fragmentManager.beginTransaction();
                //         ft.remove(this);

              //  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                // ft.commit();
                //       .remove(fragmentManager.findFragmentById(R.id.content_frame))
                //       .add(R.id.content_frame, new MysideFragment(), "D")
                //      .commit();

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
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                Log.i(TAG, "discardButtonClickListener");
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.

                                  NavUtils.navigateUpFromSameTask(getActivity());
                              //  getActivity().getSupportFragmentManager().popBackStack();
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                Log.i(TAG, "discardButtonClickListener2");
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        Log.i(TAG, "super.onOptionsItemSelected(item)");
        return super.onOptionsItemSelected(item);

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
        values.put(BoligContract.BoligEntry.COLUMN_ADDRESS, addressString);
        values.put(BoligContract.BoligEntry.COLUMN_ADDRESS_POST, postString);
        values.put(BoligContract.BoligEntry.COLUMN_ADDRESS_CITY, cityString);
        values.put(BoligContract.BoligEntry.COLUMN_LANDLORD, landlordString);


        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int length = 0;
        if (!TextUtils.isEmpty(lengthString)) {
            length = Integer.parseInt(lengthString);
        }
        values.put(BoligContract.BoligEntry.COLUMN_CONTRACT_LENGTH, length);


        int notice = 0;
        if (!TextUtils.isEmpty(noticeString)) {
            notice = Integer.parseInt(noticeString);
        }
        values.put(BoligContract.BoligEntry.COLUMN_CONTRACT_NOTICE, notice);

        int price = 0;
        if (!TextUtils.isEmpty(rentfeeString)) {
            price = Integer.parseInt(rentfeeString);
        }
        values.put(BoligContract.BoligEntry.COLUMN_PRICE, price);

        int deposit = 0;
        if (!TextUtils.isEmpty(depositString)) {
            deposit = Integer.parseInt(depositString);
        }
        values.put(BoligContract.BoligEntry.COLUMN_DEPOSIT, deposit);

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentBoligUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            Uri newUri = getActivity().getContentResolver().insert(BoligContract.BoligEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(getActivity(), getString(R.string.editor_insert_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(getActivity(), getString(R.string.editor_save_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getActivity().getContentResolver().update(mCurrentBoligUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(getActivity(), getString(R.string.editor_update_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(getActivity(), getString(R.string.editor_update_pet_successful),
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
                BoligContract.BoligEntry._ID,
                BoligContract.BoligEntry.COLUMN_ADDRESS,
                BoligContract.BoligEntry.COLUMN_ADDRESS_POST,
                BoligContract.BoligEntry.COLUMN_ADDRESS_CITY,
                BoligContract.BoligEntry.COLUMN_LANDLORD,
                BoligContract.BoligEntry.COLUMN_CONTRACT_LENGTH,
                BoligContract.BoligEntry.COLUMN_CONTRACT_NOTICE,
                BoligContract.BoligEntry.COLUMN_PRICE,
                BoligContract.BoligEntry.COLUMN_DEPOSIT};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getActivity(),   // Parent activity context
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
            int addressColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_ADDRESS);
            int postColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_ADDRESS_POST);
            int cityColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_ADDRESS_CITY);
            int landlordColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_LANDLORD);
            int lengthColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_CONTRACT_LENGTH);
            int noticeColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_CONTRACT_NOTICE);
            int priceColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_PRICE);
            int depositColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_DEPOSIT);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
            int rowsDeleted = getActivity().getContentResolver().delete(mCurrentBoligUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(getActivity(), getString(R.string.editor_delete_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(getActivity(), getString(R.string.editor_delete_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // .replace(R.id.content_frame, new MysideFragment())
        //         .commit();
        FragmentManager fm = getFragmentManager();
        getFragmentManager().beginTransaction()
                .remove(fm.findFragmentById(R.id.content_frame))
                .commit();
    }

    @Override
    protected String getTitle() {

        if (mCurrentBoligUri == null) {
            return getString(R.string.title_new_bolig);
        } else {

            return getString(R.string.title_edit_bolig);
        }

    }





    public void newCardChanged() {

        // If the pet hasn't changed, continue with handling back button press
        if (!mBoligHasChanged) {
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);

        }



    //   public void onResume() {
    //       super.onResume();
    //      Log.i(TAG, "onResume:");
    //    if (getView() == null) {
    //          return;
  //      }
  //      getView().setFocusableInTouchMode(true);
  //      getView().requestFocus();
  //       getView().setOnKeyListener(new View.OnKeyListener() {
  //          @Override
    //           public boolean onKey(View v, int keyCode, KeyEvent event) {

    //          if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

    //                if (!mBoligHasChanged) {
    //                   return false;
    //               }
    //             DialogInterface.OnClickListener discardButtonClickListener =
    //                      new DialogInterface.OnClickListener() {
    //                        @Override
    //                         public void onClick(DialogInterface dialogInterface, int i) {
    //                            getActivity().getSupportFragmentManager().popBackStack();
    //                       }
    //                      };
                    // Show dialog that there are unsaved changes
    //                showUnsavedChangesDialog(discardButtonClickListener);

    //                 return true;
    //             }
    //            return false;
    //        }
    //     });
    //   }


}
