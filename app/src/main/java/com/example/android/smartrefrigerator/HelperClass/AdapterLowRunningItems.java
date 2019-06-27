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
import com.example.android.smartrefrigerator.R;

import java.util.ArrayList;

public class AdapterLowRunningItems extends RecyclerView.Adapter<AdapterLowRunningItems.LowFoodItemViewHolder> {

    private ArrayList<LowFoodItem> lowFoodItemsArrayList;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterLowRunningItems(Context context)
    {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        lowFoodItemsArrayList = new ArrayList<LowFoodItem>();
    }

    public void addLowFoodItem(LowFoodItem lowFoodItem)
    {
        lowFoodItemsArrayList.add(lowFoodItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LowFoodItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LowFoodItemViewHolder(layoutInflater.inflate(R.layout.low_consumption_card, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull LowFoodItemViewHolder lowFoodItemViewHolder, int i) {
        LowFoodItem lowFoodItem = lowFoodItemsArrayList.get(i);

        lowFoodItemViewHolder.setItemNameLC(lowFoodItem.getItemName());
        lowFoodItemViewHolder.setLowConsumptionImage(lowFoodItem.getItemName());
        lowFoodItemViewHolder.setQuantity(lowFoodItem.getQuantity(), lowFoodItem.getRate());

    }

    @Override
    public int getItemCount() {
        return lowFoodItemsArrayList.size();
    }

    public class LowFoodItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView lowConsumptionImage;
        public TextView itemNameLC;
        public TextView suggestedQuantity;

        public LowFoodItemViewHolder(@NonNull View itemView) {
            super(itemView);

            lowConsumptionImage = itemView.findViewById(R.id.lowConsumptionImage);
            itemNameLC = itemView.findViewById(R.id.itemNameLC);
            suggestedQuantity = itemView.findViewById(R.id.suggestedQuantity);

        }

        public void setItemNameLC(String itemName)
        {
            itemNameLC.setText(itemName);
        }

        public void setLowConsumptionImage(String itemName)
        {
            int res = context.getResources().getIdentifier("img_" + itemName, "drawable", context.getPackageName());
            lowConsumptionImage.setImageResource(res);
        }

        public void setQuantity(int quantity, String rate)
        {
            suggestedQuantity.setText(String.valueOf(quantity).concat(rate));
        }

    }

}

