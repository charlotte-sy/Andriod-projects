package com.example.android.boligchecker.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.boligchecker.BoligActivity;
import com.example.android.boligchecker.BoligCursorAdapter;
import com.example.android.boligchecker.BoligFragment;
import com.example.android.boligchecker.MainActivity;
import com.example.android.boligchecker.R;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
//import android.app.LoaderManager;
//import android.content.CursorLoader;
//import android.content.Loader;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;


import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.boligchecker.base.AddFragmentHandler;
import com.example.android.boligchecker.base.BackButtonSupportFragment;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.data.BoligContract.BoligEntry;
import com.example.android.boligchecker.data.BoligDbHelper;
import com.example.android.boligchecker.myside.MainMypage;

import net.skoumal.fragmentback.BackFragment;

import static android.R.attr.fragment;
import static com.mikepenz.iconics.Iconics.TAG;
import static net.skoumal.fragmentback.BackFragment.NORMAL_BACK_PRIORITY;

/**
 * Created by Seoyeon on 12/12/2017.
 */

public class MysideFragment extends BaseFragment implements  LoaderManager.LoaderCallbacks <Cursor> {

    // see oncreate -----loader
    BoligCursorAdapter mCursorAdapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: C");
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Setup FAB to open EditorActivity
        View v= inflater.inflate(R.layout.add_bolig, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getActivity(), BoligActivity.class);
                startActivity(intent);

            //    Fragment fragment = new BoligFragment();
                //     FragmentManager fragmentManager = getFragmentManager();
                //    FragmentTransaction ft = fragmentManager.beginTransaction();
                //       ft.remove(fragmentManager.findFragmentById(R.id.content_frame))
                //        .add(R.id.content_frame, fragment)
                //        .addToBackStack(null)
                //        .commit();


            }
        });

        // Find the ListView which will be populated with the pet data
        ListView boligListView = (ListView) v.findViewById(R.id.text_view_pet);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = v.findViewById(R.id.empty_view);
        boligListView.setEmptyView(emptyView);


        //click view
        // Setup the item click listener
        boligListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(getActivity(), BoligActivity.class);

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
        mCursorAdapter = new BoligCursorAdapter(getActivity().getApplicationContext(), null);
        boligListView.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(BOLIG_LOADER,null,this);

        return v;

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

        return new CursorLoader(getActivity(),
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


    @Override
    protected String getTitle() {
        return getString(R.string.mysidefragment);
    }



 //     @Override
    //    public void onResume() {
    //        super.onResume();
 //       Log.i(TAG, "onResume: C");
    //       FragmentManager fm = getFragmentManager();
    //       for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
    //           Log.i(TAG, "Found fragment: In C " + fm.getBackStackEntryAt(entry).getId());
    //       }
    //      if (getView() == null) {
    //          return;
    //      }

    //    getView().setFocusableInTouchMode(true);
    //      getView().requestFocus();
    //      getView().setOnKeyListener(new View.OnKeyListener() {
    //         @Override
    //          public boolean onKey(View v, int keyCode, KeyEvent event) {

    //              if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {


    //                 FragmentManager fm = getFragmentManager();
    //                 fm.popBackStack("B",FragmentManager.POP_BACK_STACK_INCLUSIVE);
    //                 getFragmentManager().beginTransaction()
                             // .remove(fm.findFragmentById(R.id.content_frame))
    //                          .replace(R.id.content_frame, new MainMypage())
                            //  .addToBackStack(null)
    //                         .commit();

    //             }
    //              return false;
    //          }

    //      });

    //   }
}
