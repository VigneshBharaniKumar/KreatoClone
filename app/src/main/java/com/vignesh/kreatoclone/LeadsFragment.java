package com.vignesh.kreatoclone;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class LeadsFragment extends Fragment {

    public LeadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leads, container, false);
    }

    public static LeadsFragment newInstance() {
        return new LeadsFragment();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getContext(), HomeActivity.class);
        startActivity(intent);
    }
}
