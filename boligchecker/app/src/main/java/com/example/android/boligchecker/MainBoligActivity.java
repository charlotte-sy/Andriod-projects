package com.example.android.boligchecker;

import android.app.Activity;
import android.app.Fragment;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CursorAdapter;

import com.example.android.boligchecker.data.BoligContract;
import com.example.android.boligchecker.data.BoligContract.BoligEntry;
import com.example.android.boligchecker.data.BoligDbHelper;
import com.example.android.boligchecker.data.BoligProvider;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static android.R.attr.id;
import static com.example.android.boligchecker.R.id.text_view_pet;
import static com.example.android.boligchecker.data.BoligContract.BASE_CONTENT_URI;
import static com.example.android.boligchecker.data.BoligContract.PATH_BOLIG;

import android.util.Log;
/**
 * Created by Seoyeon on 14/12/2017.
 */

public class MainBoligActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks <Cursor> {

    // class PetDbHelper에서 카드 한장을 가져온다
    private BoligDbHelper mDbHelper;

    // see oncreate -----loader
    BoligCursorAdapter mCursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bolig);


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainBoligActivity.this, BoligActivity.class);
                startActivity(intent);
            }
        });


        // Find the ListView which will be populated with the pet data
        ListView boligListView = (ListView) findViewById(R.id.text_view_pet);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        boligListView.setEmptyView(emptyView);


        //click view
        // Setup the item click listener
        boligListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(MainBoligActivity.this, BoligActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentPetUri = ContentUris.withAppendedId(BoligEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentPetUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });



        // ------Loader ------------------
        mCursorAdapter = new BoligCursorAdapter(this, null);
        boligListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(BOLIG_LOADER,null,this);

    }


    //--------------------Cursor Loader------------------------------------

    private static final int BOLIG_LOADER = 0;

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                BoligEntry._ID,
                BoligEntry.COLUMN_ADDRESS,
                BoligEntry.COLUMN_ADDRESS_POST,
                BoligEntry.COLUMN_ADDRESS_CITY,
                BoligEntry.COLUMN_LANDLORD,
                BoligEntry.COLUMN_CONTRACT_LENGTH,
                BoligEntry.COLUMN_CONTRACT_NOTICE,
                BoligEntry.COLUMN_PRICE,
                BoligEntry.COLUMN_DEPOSIT,};

        return new CursorLoader(this,
                BoligEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mCursorAdapter.swapCursor(null);
    }

}
