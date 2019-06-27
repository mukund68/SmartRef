package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.smartrefrigerator.ApiHandler.ApiHandler;
import com.example.android.smartrefrigerator.ApiHandler.ComparePrice;
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.ApiHandler.SuggestRecipe;
import com.example.android.smartrefrigerator.HelperClass.AdapterComparePrice;
import com.example.android.smartrefrigerator.HelperClass.AdapterSuggestRecipe;
import com.example.android.smartrefrigerator.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompareOnlinePrices extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private AdapterComparePrice adapterComparePrice;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private static CompareOnlinePrices compareOnlinePricesFragment;

    public CompareOnlinePrices() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compare_online_prices, container, false);

        Log.e("InCompare","Yepiee");

        jsonPlaceHolderApi = ApiHandler.getJsonPlaceHolderApi();

        linearLayoutManager = new LinearLayoutManager(getContext());
        progressBar = view.findViewById(R.id.compare_online_prices_progress);
        progressBar.setIndeterminate(true);

        recyclerView = view.findViewById(R.id.compare_online_prices_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        adapterComparePrice = new AdapterComparePrice(view.getContext());
        comparePrice();

        progressBar.setVisibility(View.GONE);

        return view;
    }

    public void comparePrice() {

        Call<List<ComparePrice>> callToComparePrices = jsonPlaceHolderApi.comparePrices();

        callToComparePrices.enqueue(new Callback<List<ComparePrice>>() {
            @Override
            public void onResponse(@NonNull Call<List<ComparePrice>> call, @NonNull Response<List<ComparePrice>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(),"Code: " + response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("BeforeGET1", response.body().toString());
                List<ComparePrice> compareOnlinePrices = response.body();
                Log.d("GETResponse1", response.body().toString());

                for (ComparePrice comparePrice : compareOnlinePrices)
                    adapterComparePrice.addComparePrice(comparePrice);

                recyclerView.setAdapter(adapterComparePrice);
            }

            @Override
            public void onFailure(@NonNull Call<List<ComparePrice>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static CompareOnlinePrices getCompareOnlinePricesFragment() {
        if (compareOnlinePricesFragment == null)
            compareOnlinePricesFragment = new CompareOnlinePrices();
        return compareOnlinePricesFragment;
    }

}
