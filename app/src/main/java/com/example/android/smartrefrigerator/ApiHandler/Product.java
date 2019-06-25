package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

public class Product
{
    @SerializedName("ItemName")
    private String itemName;
    @SerializedName("Price")
    private float price;
    @SerializedName("Quant")
    private int quantity;
    @SerializedName("Rate")
    private String rate;

    public Product(String itemName, float price, int quantity, String rate) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.rate = rate;
    }

    public Product() {
    }

    public String getItemName() {
        return itemName;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRate() {
        return rate;
    }

}
