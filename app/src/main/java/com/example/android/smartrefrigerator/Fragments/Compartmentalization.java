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
import com.example.android.smartrefrigerator.ApiHandler.CompartmentResult;
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.HelperClass.AdapterCompartmentalization;
import com.example.android.smartrefrigerator.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Compartmentalization extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private AdapterCompartmentalization adapterCompartmentalization;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private static Compartmentalization compartmentalizationFragment;

    public Compartmentalization() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compartmentalization, container, false);

        jsonPlaceHolderApi = ApiHandler.getJsonPlaceHolderApi();

        linearLayoutManager = new LinearLayoutManager(getContext());
        progressBar = view.findViewById(R.id.compartmentalization_progress);
        progressBar.setIndeterminate(true);

        recyclerView = view.findViewById(R.id.compartmentalization_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        adapterCompartmentalization = new AdapterCompartmentalization(view.getContext());
        viewComparments();

        progressBar.setVisibility(View.GONE);

        return view;

    }

    public void viewComparments() {

        Call<List<CompartmentResult>> callToCompartmentalization = jsonPlaceHolderApi.getCompartments();

        callToCompartmentalization.enqueue(new Callback<List<CompartmentResult>>() {
            @Override
            public void onResponse(@NonNull Call<List<CompartmentResult>> call, @NonNull Response<List<CompartmentResult>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(),"Code: " + response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("BeforeGET1", response.body().toString());
                List<CompartmentResult> compartmentResults = response.body();
                Log.d("GETResponse1", response.body().toString());

                for (CompartmentResult compartmentResult : compartmentResults)
                    adapterCompartmentalization.addCompartmentResult(compartmentResult);

                recyclerView.setAdapter(adapterCompartmentalization);
            }

            @Override
            public void onFailure(@NonNull Call<List<CompartmentResult>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Compartmentalization getCompartmentalizationFragment() {
        if (compartmentalizationFragment == null)
            compartmentalizationFragment = new Compartmentalization();
        return compartmentalizationFragment;
    }

}
