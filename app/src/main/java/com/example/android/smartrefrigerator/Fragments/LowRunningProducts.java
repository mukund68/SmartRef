package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartrefrigerator.R;


public class LowRunningProducts extends Fragment {

    private static LowRunningProducts lowRunningProductsFragment;

    public LowRunningProducts() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_low_running_products, container, false);
    }

    public static LowRunningProducts getLowRunningProductsFragment() {
        if (lowRunningProductsFragment == null)
            lowRunningProductsFragment = new LowRunningProducts();
        return lowRunningProductsFragment;
    }

}
