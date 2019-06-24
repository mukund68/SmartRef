package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartrefrigerator.R;

public class CompareOnlinePrices extends Fragment {

    private static CompareOnlinePrices compareOnlinePricesFragment;

    public CompareOnlinePrices() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compare_online_prices, container, false);
    }

    public static CompareOnlinePrices getCompareOnlinePricesFragment() {
        if (compareOnlinePricesFragment == null)
            compareOnlinePricesFragment = new CompareOnlinePrices();
        return compareOnlinePricesFragment;
    }

}
