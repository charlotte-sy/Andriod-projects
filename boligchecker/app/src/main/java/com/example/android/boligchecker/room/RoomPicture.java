package com.example.android.boligchecker.room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BackButtonSupportFragment;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.fragments.ContractFragment;
import com.example.android.boligchecker.fragments.HomeFragment;
import com.example.android.boligchecker.fragments.LegalFragment;
import com.example.android.boligchecker.fragments.RentFragment;
import com.example.android.boligchecker.myside.FastCheckActivity;

/**
 * Created by Seoyeon on 22/12/2017.
 */

public class RoomPicture extends BaseFragment implements View.OnClickListener{

    private Toast toast;

    public static RoomPicture newInstance() {return new RoomPicture();}


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button roomButton1 = (Button) getView().findViewById(R.id.room_button1);
        Button roomButton2 = (Button) getView().findViewById(R.id.room_button2);
        Button roomButton3 = (Button) getView().findViewById(R.id.room_button3);
        Button roomButton4 = (Button) getView().findViewById(R.id.room_button4);

        roomButton1.setOnClickListener(this);
        roomButton2.setOnClickListener(this);
        roomButton3.setOnClickListener(this);
        roomButton4.setOnClickListener(this);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.room_picture, container, false);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.room_button1:
                Intent intent1 = new Intent(getActivity(), WallOne.class);
                startActivity(intent1);
                break;
            case R.id.room_button2:
                Intent intent2 = new Intent(getActivity(), WallOne.class);
                startActivity(intent2);
                break;
            case R.id.room_button3:
                Intent intent3 = new Intent(getActivity(), WallOne.class);
                startActivity(intent3);
                break;
            case R.id.room_button4:
                Intent intent4 = new Intent(getActivity(), WallOne.class);
                startActivity(intent4);
                break;
        }

    }


    @Override
    protected String getTitle() {
        return getString(R.string.roompicture);
    }

}
