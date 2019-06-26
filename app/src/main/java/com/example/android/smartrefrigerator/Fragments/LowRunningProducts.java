package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.smartrefrigerator.ApiHandler.ApiHandler;
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.ApiHandler.LowFoodItem;
import com.example.android.smartrefrigerator.R;

import java.util.ArrayList;


public class LowRunningProducts extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private ArrayList<LowFoodItem> lowFoodItems;

    private static LowRunningProducts lowRunningProductsFragment;

    public LowRunningProducts() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_low_running_products, container, false);


        jsonPlaceHolderApi = ApiHandler.getJsonPlaceHolderApi();

        linearLayoutManager = new LinearLayoutManager(getContext());
        progressBar = view.findViewById(R.id.low_running_products_progress);
        progressBar.setIndeterminate(true);

        recyclerView = view.findViewById(R.id.low_running_products_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lowFoodItems = new ArrayList<>();

        //suggestRecipe();

        progressBar.setVisibility(View.GONE);

        return view;
    }



    public static LowRunningProducts getLowRunningProductsFragment() {
        if (lowRunningProductsFragment == null)
            lowRunningProductsFragment = new LowRunningProducts();
        return lowRunningProductsFragment;
    }

}
