package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartrefrigerator.R;

public class FridgeContent extends Fragment {

    private static FridgeContent fridgeContentFragment;

    public FridgeContent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fridge_content, container, false);
    }

    public static FridgeContent getFridgeContentFragment() {
        if (fridgeContentFragment == null)
            fridgeContentFragment = new FridgeContent();
        return fridgeContentFragment;
    }

}
