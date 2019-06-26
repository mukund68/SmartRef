package com.example.android.smartrefrigerator.ApiHandler;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageResponse {

    @SerializedName("imageName")
    private String imageName;
    @SerializedName("result")
    private String result;

    public ImageResponse(String imageName) {
        this.imageName = imageName;
    }

    public ImageResponse() {
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
