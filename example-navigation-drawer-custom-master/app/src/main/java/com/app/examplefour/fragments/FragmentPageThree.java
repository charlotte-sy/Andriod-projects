package com.app.examplefour.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app.examplefour.R;
import com.app.examplefour.adapter.SampleOneItem;
import com.app.examplefour.recyclerview.RecyclerLinearAndGridItemDecoration;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ricardo on 05/05/16.
 */
public class FragmentPageThree extends Fragment {

    @BindView(R.id.item_recycler_view)
    RecyclerView mRecyclerView;

    public static FragmentPageThree newInstance() {
        return new FragmentPageThree();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_page, container, false);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) fragmentView.getLayoutParams();
        int space = (int) getResources().getDimension(R.dimen.spacing_normal);
        params.setMargins(space, 0, space, 0);
        fragmentView.setLayoutParams(params);

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
        getActivity().setTitle(getString(R.string.fragment_title_three));
    }

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0 ? 2 : 1);
            }
        });

        FastItemAdapter fastAdapter = new FastItemAdapter();
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(fastAdapter);
        mRecyclerView.addItemDecoration(new RecyclerLinearAndGridItemDecoration(getActivity(), R.dimen.spacing_normal));

        for (int i = 1; i < 11; i++) {
            fastAdapter.add(new SampleOneItem().withName(getString(R.string.fragment_title_three)).withPosition(String.valueOf(i)).withUrl("http://www.menucool.com/slider/jsImgSlider/images/image-slider-2.jpg"));
        }
    }
}
