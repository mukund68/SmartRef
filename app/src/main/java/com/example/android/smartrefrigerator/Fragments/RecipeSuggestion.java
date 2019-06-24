package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartrefrigerator.R;

public class RecipeSuggestion extends Fragment {

    private static RecipeSuggestion recipeSuggestionFragment;

    public RecipeSuggestion() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_suggestion, container, false);
    }

    public static RecipeSuggestion getRecipeSuggestionFragment() {
        if (recipeSuggestionFragment == null)
            recipeSuggestionFragment = new RecipeSuggestion();
        return recipeSuggestionFragment;
    }

}
