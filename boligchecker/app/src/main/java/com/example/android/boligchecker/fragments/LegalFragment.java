package com.example.android.boligchecker.fragments;

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
 * Created by Seoyeon on 12/12/2017.
 */

public class LegalFragment extends BaseFragment {

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
        String htmlAsString = getString(R.string.legal_aid_content);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);

        TextView textView = (TextView) view.findViewById(R.id.legal_aid);
        textView.setText(htmlAsSpanned);

        return view;
    }

    @Override
    protected String getTitle() {
        return getString(R.string.legal_aid);
    }

}
