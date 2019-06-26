package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smartrefrigerator.ApiHandler.LowFoodItem;
import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.R;

import java.util.ArrayList;

public class AdapterLowConsumption extends RecyclerView.Adapter<AdapterLowConsumption.ViewHolder> {

    private ArrayList<LowFoodItem> lowFoodItems;
    private Context context;
    private ArrayList<Product> product;

    public AdapterLowConsumption(ArrayList<LowFoodItem> lowFoodItems, Context context) {
        this.lowFoodItems = lowFoodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.low_consumption_card, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        LowFoodItem lowFoodItem = lowFoodItems.get(i);

        product = lowFoodItem.getProducts();


    }

    @Override
    public int getItemCount() {
        return lowFoodItems.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView lowConsumptionImage;
        public TextView itemName;
        public TextView currentQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            lowConsumptionImage = itemView.findViewById(R.id.lowConsumptionImage);
            itemName = itemView.findViewById(R.id.itemName);
            currentQuantity = itemView.findViewById(R.id.currentQuantity);

        }
    }


}

