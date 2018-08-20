package com.example.android.boligchecker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;

import com.example.android.boligchecker.R;
import com.example.android.boligchecker.base.BackButtonSupportFragment;
import com.example.android.boligchecker.base.BaseFragment;

public class HomeFragment extends BaseFragment implements BackButtonSupportFragment, View.OnClickListener {

    private boolean consumingBackPress = true;
    private Toast toast;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView rentImage = (ImageView) getView().findViewById(R.id.rent_image);
        ImageView contractImage = (ImageView) getView().findViewById(R.id.contract_image);
        ImageView legalImage = (ImageView) getView().findViewById(R.id.legal_image);
        ImageView moveImage = (ImageView) getView().findViewById(R.id.move_out_image);

        rentImage.setOnClickListener(this);
        contractImage.setOnClickListener(this);
        legalImage.setOnClickListener(this);
        moveImage.setOnClickListener(this);

     // clearBackStack();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home, container, false);

    }


    private void clearBackStack() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
          //  manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
           manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        switch (v.getId()) {
            case R.id.rent_image:
                fragment = new RentFragment();
                break;
            case R.id.contract_image:
                fragment = new ContractFragment();
                break;
            case R.id.move_out_image:
                fragment = new MoveFragment();
                break;
            case R.id.legal_image:
                fragment = new LegalFragment();
                break;

        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }



    @Override
    public boolean onBackPressed() {
        //return true when handled by yourself
        if (consumingBackPress) {
            //This is actually a terrible thing to do and totally against the guidelines
            // Ideally you shouldn't handle the backpress ever, so really think twice about what
            // you are doing and whether you are getting hacky
            toast = Toast.makeText(getActivity(), "Press back once more to quit the application", Toast.LENGTH_LONG);
            toast.show();
            consumingBackPress = false;
            return true; //consumed
        }
        toast.cancel();
        return false; //delegated
    }


    @Override
    protected String getTitle() {
        return "";
    }
}