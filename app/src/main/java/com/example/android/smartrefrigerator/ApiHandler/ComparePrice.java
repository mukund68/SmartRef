package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ComparePrice {

    @SerializedName("Store")
    private String store;
    @SerializedName("Totalbill")
    private float totalBill;
    @SerializedName("products")
    private ArrayList<Product> products;

    public ComparePrice(String store, float totalBill, ArrayList<Product> products) {
        this.store = store;
        this.totalBill = totalBill;
        this.products = products;
    }

    public ComparePrice() {
    }

    public String getStore() {
        return store;
    }

    public float getTotalBill() {
        return totalBill;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
