package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("food")
    private ExpiryForFood food ;

    public Food(ExpiryForFood food) {
        this.food = food;
    }

    public ExpiryForFood getFood() {
        return food;
    }

}
