package com.app.examplefour.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.examplefour.R;
import com.app.examplefour.adapter.SampleOneItem;
import com.app.examplefour.recyclerview.RecyclerItemDecorationOne;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPageOne extends Fragment {

    @BindView(R.id.item_recycler_view)
    RecyclerView mRecyclerView;

    public static FragmentPageOne newInstance() {
        return new FragmentPageOne();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(getString(R.string.fragment_title_one));
    }

    private void setupRecyclerView() {
        FastItemAdapter fastAdapter = new FastItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(fastAdapter);
        mRecyclerView.addItemDecoration(new RecyclerItemDecorationOne(getActivity(), R.dimen.spacing_small));

        for (int i = 0; i < 20; i++) {
            fastAdapter.add(new SampleOneItem().withName(getString(R.string.fragment_title_one)).withPosition(String.valueOf(i)).withUrl("http://www.menucool.com/slider/jsImgSlider/images/image-slider-2.jpg"));
        }
    }
}
