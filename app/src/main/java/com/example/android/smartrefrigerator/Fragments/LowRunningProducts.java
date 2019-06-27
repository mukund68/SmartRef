package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.smartrefrigerator.ApiHandler.ApiHandler;
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.ApiHandler.LowFoodItem;
import com.example.android.smartrefrigerator.HelperClass.AdapterLowRunningItems;
import com.example.android.smartrefrigerator.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LowRunningProducts extends Fragment {

    private final CompareOnlinePrices compareOnlinePricesFragment = CompareOnlinePrices.getCompareOnlinePricesFragment();

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private AdapterLowRunningItems adapterLowRunningItems;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private Button buttonLowRunningToCompare;

    private static LowRunningProducts lowRunningProductsFragment;

    public LowRunningProducts() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_low_running_products, container, false);

        jsonPlaceHolderApi = ApiHandler.getJsonPlaceHolderApi();
        buttonLowRunningToCompare = view.findViewById(R.id.buttonLowRunningToCompare);

        linearLayoutManager = new LinearLayoutManager(getContext());
        progressBar = view.findViewById(R.id.low_running_products_progress);
        progressBar.setIndeterminate(true);

        recyclerView = view.findViewById(R.id.low_running_products_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        adapterLowRunningItems = new AdapterLowRunningItems(view.getContext());
        getLowFoodItem();

        buttonLowRunningToCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Change fragment to Compare Price
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, compareOnlinePricesFragment, "NewFragmentTag");
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        progressBar.setVisibility(View.GONE);

        return view;
    }

    public void getLowFoodItem() {

        Call<List<LowFoodItem>> callToGetLowFoodItems = jsonPlaceHolderApi.getLowFoodItem();

        callToGetLowFoodItems.enqueue(new Callback<List<LowFoodItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<LowFoodItem>> call, @NonNull Response<List<LowFoodItem>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(),"Code: " + response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("BeforeGET3", response.body().toString());
                List<LowFoodItem> lowFoodItems = response.body();
                Log.d("GETResponse3", response.body().toString());

                for (LowFoodItem lowFoodItem : lowFoodItems)
                    adapterLowRunningItems.addLowFoodItem(lowFoodItem);

                recyclerView.setAdapter(adapterLowRunningItems);
            }

            @Override
            public void onFailure(@NonNull Call<List<LowFoodItem>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static LowRunningProducts getLowRunningProductsFragment() {
        if (lowRunningProductsFragment == null)
            lowRunningProductsFragment = new LowRunningProducts();
        return lowRunningProductsFragment;
    }

}
