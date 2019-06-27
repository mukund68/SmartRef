package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CompartmentResult {

    @SerializedName("Rack")
    private String rack;
    @SerializedName("products")
    private ArrayList<Product> products;

    public CompartmentResult(String rack, ArrayList<Product> products) {
        this.rack = rack;
        this.products = products;
    }

    public CompartmentResult() {
    }

    public String getRack() {
        return rack;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
