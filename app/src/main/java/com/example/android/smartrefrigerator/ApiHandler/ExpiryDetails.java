package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ExpiryDetails {

    @SerializedName("text")
    private String text;
    @SerializedName("hints")
    private ArrayList<Food> hints;

    public ExpiryDetails(String text, ArrayList<Food> hints) {
        this.text = text;
        this.hints = hints;
    }

    public String getText() {
        return text;
    }

    public ArrayList<Food> getHints() {
        return hints;
    }

}
