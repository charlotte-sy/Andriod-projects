package com.example.android.boligchecker.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.fragments.ContractFragment;

/**
 * Created by Seoyeon on 22/12/2017.
 */

public class RoomPictureMain extends BaseFragment implements  View.OnClickListener {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button roomManagerButton = (Button) getView().findViewById(R.id.room_manage_button);
        Button checklistButton = (Button) getView().findViewById(R.id.check_list_button);


        roomManagerButton.setOnClickListener(this);
        checklistButton.setOnClickListener(this);


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.room_picture_main, container, false);

    }


    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.room_manage_button:
                Intent intent1 = new Intent(getActivity(), WallOne.class);
                startActivity(intent1);
                break;
            case R.id.check_list_button:
                Intent intent2 = new Intent(getActivity(), CheckList.class);
                startActivity(intent2);
                //  fragment = new CheckList();
                //ft.remove(fm.findFragmentById(R.id.content_frame))
                //  .add(R.id.content_frame, fragment)
                //   .addToBackStack(null)
                //   .commit();
                break;
        }



    }


    @Override
    protected String getTitle() {
        return getString(R.string.room_manage);
    }

}
