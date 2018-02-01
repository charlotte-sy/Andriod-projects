package com.example.android.boligchecker.fragments;

import com.example.android.boligchecker.base.BaseFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BaseFragment;

import butterknife.ButterKnife;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;


/**
 * Created by Seoyeon on 26/01/2018.
 */

public class MoveFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.move_out, container, false);
        ButterKnife.bind(this, view);

        // get our html content
        String htmlAsString = getString(R.string.move_out_content);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);

        TextView textView = (TextView) view.findViewById(R.id.move_out);
        textView.setText(htmlAsSpanned);

        return view;
    }


    @Override
    protected String getTitle() {
        return getString(R.string.move_out);
    }
}
