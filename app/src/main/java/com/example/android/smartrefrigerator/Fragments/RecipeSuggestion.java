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
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.ApiHandler.SuggestRecipe;
import com.example.android.smartrefrigerator.HelperClass.AdapterSuggestRecipe;
import com.example.android.smartrefrigerator.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeSuggestion extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;

    private ArrayList<SuggestRecipe> recipeSuggestion;

    private static RecipeSuggestion recipeSuggestionFragment;

    public RecipeSuggestion() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_suggestion, container, false);

        jsonPlaceHolderApi = ApiHandler.getJsonPlaceHolderApi();

        linearLayoutManager = new LinearLayoutManager(getContext());
        progressBar = view.findViewById(R.id.recipe_suggestion_progress);
        progressBar.setIndeterminate(true);

        recyclerView = view.findViewById(R.id.recipe_suggestion_recycler_view);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(false);

        recipeSuggestion = new ArrayList<>();

        suggestRecipe();

        progressBar.setVisibility(View.GONE);

        return view;
    }

    public void suggestRecipe() {

        Call<List<SuggestRecipe>> callForSuggestRecipes = jsonPlaceHolderApi.suggestRecipes();

        callForSuggestRecipes.enqueue(new Callback<List<SuggestRecipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<SuggestRecipe>> call, @NonNull Response<List<SuggestRecipe>> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(),"Code: " + response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("BeforeGET2", response.body().toString());
                List<SuggestRecipe> suggestRecipes = response.body();
                Log.d("GETResponse2", response.body().toString());

                for (SuggestRecipe suggestRecipe : suggestRecipes) {
                    recipeSuggestion.add(suggestRecipe);
                }

                adapter = new AdapterSuggestRecipe(recipeSuggestion, getContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(@NonNull Call<List<SuggestRecipe>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static RecipeSuggestion getRecipeSuggestionFragment() {
        if (recipeSuggestionFragment == null)
            recipeSuggestionFragment = new RecipeSuggestion();
        return recipeSuggestionFragment;
    }

}
