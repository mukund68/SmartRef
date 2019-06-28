package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.smartrefrigerator.ApiHandler.ApiHandler;
import com.example.android.smartrefrigerator.ApiHandler.FridgeContent;
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.HelperClass.AdapterFridgeContent;
import com.example.android.smartrefrigerator.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FridgeContents extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private AdapterFridgeContent adapterFridgeContent;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private static FridgeContents fridgeContentFragment;

    public FridgeContents() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fridge_content, container, false);

        jsonPlaceHolderApi = ApiHandler.getJsonPlaceHolderApi();

        linearLayoutManager = new LinearLayoutManager(getContext());
        progressBar = view.findViewById(R.id.fridge_content_progress);
        progressBar.setIndeterminate(true);

        recyclerView = view.findViewById(R.id.fridge_content_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        adapterFridgeContent = new AdapterFridgeContent(view.getContext());
        viewFridgeContent();

        progressBar.setVisibility(View.GONE);

        return view;
    }

    public void viewFridgeContent() {

        Call<List<FridgeContent>> callToFridgeContent = jsonPlaceHolderApi.getFridgeContents();

        callToFridgeContent.enqueue(new Callback<List<FridgeContent>>() {
            @Override
            public void onResponse(@NonNull Call<List<FridgeContent>> call, @NonNull Response<List<FridgeContent>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(),"Code: " + response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("BeforeGET1", response.body().toString());
                List<FridgeContent> fridgeContents = response.body();
                Log.d("GETResponse1", response.body().toString());

                for (FridgeContent fridgeContent : fridgeContents)
                    adapterFridgeContent.addFridgeContent(fridgeContent);

                recyclerView.setAdapter(adapterFridgeContent);
            }

            @Override
            public void onFailure(@NonNull Call<List<FridgeContent>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static FridgeContents getFridgeContentFragment() {
        if (fridgeContentFragment == null)
            fridgeContentFragment = new FridgeContents();
        return fridgeContentFragment;
    }

}
