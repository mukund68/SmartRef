package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LowFoodItem {

    @SerializedName("products")
    private ArrayList<Product> products;

    public LowFoodItem(ArrayList<Product> products) {
        this.products = products;
    }

    public LowFoodItem() {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
