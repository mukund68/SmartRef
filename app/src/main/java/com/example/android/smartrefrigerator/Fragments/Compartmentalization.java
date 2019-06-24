package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartrefrigerator.R;

public class Compartmentalization extends Fragment {

    private static Compartmentalization compartmentalizationFragment;

    public Compartmentalization() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compartmentalization, container, false);
    }

    public static Compartmentalization getCompartmentalizationFragment() {
        if (compartmentalizationFragment == null)
            compartmentalizationFragment = new Compartmentalization();
        return compartmentalizationFragment;
    }

}
