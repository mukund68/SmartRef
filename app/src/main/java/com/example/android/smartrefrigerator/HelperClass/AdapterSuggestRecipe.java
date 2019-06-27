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

public class AdapterSuggestRecipe extends RecyclerView.Adapter<AdapterSuggestRecipe.SuggestRecipeViewHolder> {

    private ArrayList<SuggestRecipe> suggestRecipesArrayList;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterSuggestRecipe(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        suggestRecipesArrayList = new ArrayList<SuggestRecipe>();
    }

    public void addSuggestRecipe(SuggestRecipe suggestRecipe)
    {
        suggestRecipesArrayList.add(suggestRecipe);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SuggestRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SuggestRecipeViewHolder(layoutInflater.inflate(R.layout.recipe_suggestion_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestRecipeViewHolder suggestRecipeViewHolder, int i) {
        SuggestRecipe suggestRecipe = suggestRecipesArrayList.get(i);

        suggestRecipeViewHolder.recipeName.setText(suggestRecipe.getDishName());
        suggestRecipeViewHolder.dishName.setText(suggestRecipe.getDishName());
        suggestRecipeViewHolder.categoryName.setText(suggestRecipe.getCategory());
        suggestRecipeViewHolder.calorieNumber.setText(String.valueOf(suggestRecipe.getCalories()));

        Picasso.with(context)
                .load(suggestRecipe.getImage())
                .into(suggestRecipeViewHolder.recipeImage);

        ArrayList<String> ingredients = suggestRecipe.getIngredients();
        String apiIngredients= "";
        for (String ingredient : ingredients){
            apiIngredients += ingredient + "\n";
        }
        suggestRecipeViewHolder.ingredients.setText(apiIngredients);

        ArrayList<String> presentIngredients = suggestRecipe.getPresentIngredients();
        String apiPresentIngredients= "";
        for (String presentIngredient : presentIngredients){
            apiPresentIngredients += presentIngredient + "\n";
        }
        suggestRecipeViewHolder.presentIngredients.setText(apiPresentIngredients);

    }

    @Override
    public int getItemCount() {
        return suggestRecipesArrayList.size();
    }

    public class SuggestRecipeViewHolder extends RecyclerView.ViewHolder{

        public TextView recipeName;
        public TextView presentIngredients;
        public TextView ingredients;
        public ImageView recipeImage;
        public TextView dishName;
        public TextView calorieNumber;
        public TextView categoryName;
        public FoldingCell fc;

        public SuggestRecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeName = itemView.findViewById(R.id.recipeName);
            presentIngredients = itemView.findViewById(R.id.presentIngredients);
            ingredients = itemView.findViewById(R.id.ingredients);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            dishName = itemView.findViewById(R.id.dishName);
            calorieNumber = itemView.findViewById(R.id.calorieNumber);
            categoryName = itemView.findViewById(R.id.categoryName);

            fc = itemView.findViewById(R.id.folding_cell_rs);
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
