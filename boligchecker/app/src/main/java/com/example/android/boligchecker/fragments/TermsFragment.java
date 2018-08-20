package com.example.android.boligchecker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by Seoyeon on 23/02/2018.
 */

public class TermsFragment extends BaseFragment {


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.legal_aid, container, false);
        ButterKnife.bind(this, view);

        // get our html content
        String htmlAsString = getString(R.string.about_content);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);

        TextView textView = (TextView) view.findViewById(R.id.legal_aid);
        textView.setText(htmlAsSpanned);

        return view;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.terms);
    }

}
