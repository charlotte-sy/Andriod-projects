package com.example.android.boligchecker.myside;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.boligchecker.BoligActivity;
import com.example.android.boligchecker.BoligFragment;
import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BackButtonSupportFragment;
import com.example.android.boligchecker.base.BaseFragment;
import com.example.android.boligchecker.fragments.ContractFragment;
import com.example.android.boligchecker.fragments.HomeFragment;
import com.example.android.boligchecker.fragments.LegalFragment;
import com.example.android.boligchecker.fragments.MysideFragment;
import com.example.android.boligchecker.fragments.RentFragment;
import com.example.android.boligchecker.room.RoomPictureMain;

import static android.R.attr.fragment;
import static com.example.android.boligchecker.R.string.fast_safety_check;
import static com.mikepenz.iconics.Iconics.TAG;


/**
 * Created by Seoyeon on 15/12/2017.
 */

public class MainMypage extends BaseFragment implements  View.OnClickListener {



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: B");

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i(TAG, "onResume: B");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: B");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: B");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView: B");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: B");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach: B");
    }









    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.myside, container, false);

        Button button1 = (Button) rootView.findViewById(R.id.fast_check_button);
        Button button2 = (Button) rootView.findViewById(R.id.room_picture_button);
        Button button3 = (Button) rootView.findViewById(R.id.contract_button);

        //  String code ="<b>" + getString(R.string.fast_safety_check) + "</b>" +  "<br />" +
        //          "<small>" + getString(R.string.fast_check_button) + "</small>";

        //  Spanned spanned = Html.fromHtml(code);
        //  button1.setText(spanned);

       // button1.setText(Html.fromHtml("<b>" + getString(R.string.fast_safety_check) + "</b>" +  "<br />" +
             //   "<small>" + getString(R.string.fast_check_button) + "</small>"));


        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

        return rootView;


    }


    @Override
    public void onClick(View v) {

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = null;

            switch (v.getId()) {
                case R.id.fast_check_button:
                    Intent intent = new Intent(getActivity(), FastCheckActivity.class);
                    startActivity(intent);

                case R.id.room_picture_button:
                    fragment = new RoomPictureMain();
                    ft.remove(fm.findFragmentById(R.id.content_frame))
                            .add(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();
                    break;
                case R.id.contract_button:
                    fragment = new MysideFragment();
                    ft.remove(fm.findFragmentById(R.id.content_frame))
                            .add(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();
                    break;

            }




    }

    @Override
    protected String getTitle() {
        return getString(R.string.my_side);
    }



}
