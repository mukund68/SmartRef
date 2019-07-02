package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

public class ExpiryForFood {

    @SerializedName("label")
    private String label;
    @SerializedName("brand")
    private String brand;
    @SerializedName("category")
    private String category;
    @SerializedName("image")
    private String imageUrl;

    public ExpiryForFood(String label, String brand, String category, String imageUrl) {
        this.label = label;
        this.brand = brand;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public String getLabel() {
        return label;
    }

    public String getBrand() {
        return brand;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
