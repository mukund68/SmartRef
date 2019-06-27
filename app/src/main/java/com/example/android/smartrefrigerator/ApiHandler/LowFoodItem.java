package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

public class LowFoodItem {

    @SerializedName("ItemName")
    private String itemName;
    @SerializedName("Quant")
    private int quantity;
    @SerializedName("Rate")
    private String rate;

    public LowFoodItem(String itemName, int quantity, String rate) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.rate = rate;
    }

    public LowFoodItem() {
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getRate() {
        return rate;
    }
}
