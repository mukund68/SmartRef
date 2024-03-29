package com.example.android.smartrefrigerator.ApiHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    /*@GET("getstockdetails")
    Call<List<MarketPrice>> getMarketPrices();*/

    @GET("compare")
    Call<List<ComparePrice>> comparePrices();

    @GET("suggestrecipes")
    Call<List<SuggestRecipe>> suggestRecipes();

    @GET("lowfooditems")
    Call<List<LowFoodItem>> getLowFoodItem();

    @GET("compartment")
    Call<List<CompartmentResult>> getCompartments();

    @GET("fridgecontent")
    Call<List<FridgeContent>> getFridgeContents();

    @POST("imageName/")
    Call<List<ImageResponse>> postImage(@Body ImageResponse imageName);

    @GET
    Call<ExpiryDetails> getExpiryDetails(@Url String url);

}