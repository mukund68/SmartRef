package com.example.android.smartrefrigerator.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartrefrigerator.ApiHandler.ExpiryDetails;
import com.example.android.smartrefrigerator.ApiHandler.ExpiryForFood;
import com.example.android.smartrefrigerator.ApiHandler.Food;
import com.example.android.smartrefrigerator.ApiHandler.JsonPlaceHolderApi;
import com.example.android.smartrefrigerator.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetExpiry extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private static SetExpiry setExpiryFragment;

    private Button buttonToSetExpiry;
    private TextView textViewToSetExpiry;
    private ImageView imageFromApi;
    private TextView editFoodName;
    private ImageButton buton_calendar;

    public SetExpiry() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_expiry, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //Initialize Views
        buttonToSetExpiry = view.findViewById(R.id.buttonToSetExpiry);
        textViewToSetExpiry =  view.findViewById(R.id.textViewToSetExpiry);

        buton_calendar = view.findViewById(R.id.buton_calendar);
        imageFromApi = view.findViewById(R.id.imageFromApi);
        editFoodName = view.findViewById(R.id.editFoodName);

        buttonToSetExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(setExpiryFragment).initiateScan();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if barcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getContext(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if bar contains data
                Toast.makeText(getContext(), result.getContents(), Toast.LENGTH_LONG).show();
                textViewToSetExpiry.setText("");
                String upc = result.getContents();
                showExpiryDetails(upc);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showExpiryDetails(String upc) {

        Call<ExpiryDetails> callToShowExpiryDetails = jsonPlaceHolderApi.getExpiryDetails("https://api.edamam.com/api/food-database/parser?upc="+upc+"&app_id=2a38f2f1&app_key=77779de4ded01d6736250e3ddb89eec5");

        callToShowExpiryDetails.enqueue(new Callback<ExpiryDetails>() {
            @Override
            public void onResponse(@NonNull Call<ExpiryDetails> call, @NonNull Response<ExpiryDetails> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(),"Did not scan barcode properly: " + response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                ExpiryDetails expiryDetails = response.body();
                Log.e("GETResponse", response.body().toString());

                textViewToSetExpiry.setText(expiryDetails.getText());

                ArrayList<Food> foods = expiryDetails.getHints();
                Log.e("Response",foods.toString());

                Food food = foods.get(0);
                ExpiryForFood expiryForFood = food.getFood();

                Picasso.with(getContext())
                        .load(expiryForFood.getImageUrl())
                        .into(imageFromApi);

                editFoodName.setText(expiryForFood.getLabel());

            }

            @Override
            public void onFailure(@NonNull Call<ExpiryDetails> call, @NonNull Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static SetExpiry getSetExpiryFragment() {
        if (setExpiryFragment == null)
            setExpiryFragment = new SetExpiry();
        return setExpiryFragment;
    }

}
