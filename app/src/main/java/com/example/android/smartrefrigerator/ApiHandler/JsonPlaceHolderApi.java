package com.example.android.smartrefrigerator.ApiHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    /*@GET("getstockdetails")
    Call<List<MarketPrice>> getMarketPrices();*/

    /*@GET("compare")
    Call<List<ComparePrice>> comparePrices();*/

    @GET("suggestrecipes")
    Call<List<SuggestRecipe>> suggestRecipes();

    /*@GET("lowfooditems")
    Call<List<LowFoodItem>> getLowFoodItem();

    @POST("imageName/")
    Call<List<ImageResponse>> postImage(@Body ImageResponse imageName);*/

}