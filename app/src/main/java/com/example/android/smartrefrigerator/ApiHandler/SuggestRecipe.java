package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SuggestRecipe {

    @SerializedName("Calories")
    private float calories;
    @SerializedName("Category")
    private String category;
    @SerializedName("DishName")
    private String dishName;
    @SerializedName("Image")
    private String image;
    @SerializedName("Ingredients")
    private ArrayList<String> ingredients;
    @SerializedName("PresentIngredients")
    private ArrayList<String> presentIngredients;

    public SuggestRecipe(float calories, String category, String dishName, String image, ArrayList<String> ingredients, ArrayList<String> presentIngredients) {
        this.calories = calories;
        this.category = category;
        this.dishName = dishName;
        this.image = image;
        this.ingredients = ingredients;
        this.presentIngredients = presentIngredients;
    }

    public SuggestRecipe() {
    }

    public float getCalories() {
        return calories;
    }

    public String getCategory() {
        return category;
    }

    public String getDishName() {
        return dishName;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getPresentIngredients() {
        return presentIngredients;
    }
}
