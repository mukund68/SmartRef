package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smartrefrigerator.ApiHandler.SuggestRecipe;
import com.example.android.smartrefrigerator.R;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSuggestRecipe extends RecyclerView.Adapter<AdapterSuggestRecipe.ViewHolder> {

    private ArrayList<SuggestRecipe> suggestRecipes;
    private Context context;

    public AdapterSuggestRecipe(ArrayList<SuggestRecipe> suggestRecipes, Context context) {
        this.suggestRecipes = suggestRecipes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_suggestion_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SuggestRecipe suggestRecipe = suggestRecipes.get(i);

        viewHolder.recipeName.setText(suggestRecipe.getDishName());
        viewHolder.dishName.setText(suggestRecipe.getDishName());
        viewHolder.categoryName.setText(suggestRecipe.getCategory());
        viewHolder.calorieNumber.setText(String.valueOf(suggestRecipe.getCalories()));

        Picasso.with(context)
                .load(suggestRecipe.getImage())
                .into(viewHolder.recipeImage);

        ArrayList<String> ingredients = suggestRecipe.getIngredients();
        String apiIngredients= "";
        for (String ingredient : ingredients){
            apiIngredients += ingredient + "\n";
        }
        viewHolder.ingredients.setText(apiIngredients);

        ArrayList<String> presentIngredients = suggestRecipe.getPresentIngredients();
        String apiPresentIngredients= "";
        for (String presentIngredient : presentIngredients){
            apiPresentIngredients += presentIngredient + "\n";
        }
        viewHolder.presentIngredients.setText(apiPresentIngredients);

    }

    @Override
    public int getItemCount() {
        return suggestRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView recipeName;
        public TextView presentIngredients;
        public TextView ingredients;
        public ImageView recipeImage;
        public TextView dishName;
        public TextView calorieNumber;
        public TextView categoryName;
        public FoldingCell fc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipeName);
            presentIngredients = itemView.findViewById(R.id.presentIngredients);
            ingredients = itemView.findViewById(R.id.ingredients);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            dishName = itemView.findViewById(R.id.dishName);
            calorieNumber = itemView.findViewById(R.id.calorieNumber);
            categoryName = itemView.findViewById(R.id.categoryName);
            fc = itemView.findViewById(R.id.folding_cell_rs);
            // attach click listener to folding cell
            fc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        fc.toggle(false);
                    }catch (Exception e){
                        fc.toggle(true);
                    }
                }
            });

        }
    }


}
