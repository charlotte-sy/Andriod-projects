package com.example.android.boligchecker;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.boligchecker.data.BoligContract;

// For unknown breed
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Seoyeon on 13/12/2017.
 */

public class BoligCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link BoligCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BoligCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.main_card_list, parent, false);

    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView addressTextView = (TextView) view.findViewById(R.id.detailed_address);
        TextView postTextView = (TextView) view.findViewById(R.id.post_city);

        // Extract index number from cursor
        int addressColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_ADDRESS);
        int postColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_ADDRESS_POST);
        int cityColumnIndex = cursor.getColumnIndex(BoligContract.BoligEntry.COLUMN_ADDRESS_CITY);

        String roomAddress = cursor.getString(addressColumnIndex);
        String roomPost = cursor.getString(postColumnIndex);
        String roomCity = cursor.getString(cityColumnIndex);

        // If the pet breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(roomAddress)) {
            roomAddress = context.getString(R.string.unknown_address);
        }


        // Populate fields with extracted properties
        addressTextView.setText(roomAddress);
        postTextView.setText(roomPost);
        postTextView.setText(roomCity);
    }

}
