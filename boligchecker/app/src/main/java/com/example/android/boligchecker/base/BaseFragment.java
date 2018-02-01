package com.example.android.boligchecker.base;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Seoyeon on 16/12/2017.
 */

public abstract class BaseFragment extends Fragment {

    private AddFragmentHandler fragmentHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        fragmentHandler = new AddFragmentHandler(getActivity().getSupportFragmentManager());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getTitle());
    }

    protected abstract String getTitle();

    protected void add(BaseFragment fragment) {
        fragmentHandler.add(fragment);
    }

}
