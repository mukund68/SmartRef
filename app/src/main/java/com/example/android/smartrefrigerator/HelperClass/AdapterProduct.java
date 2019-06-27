package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.R;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHolder>
{
    private Context context;
    private LayoutInflater layoutLInflater;
    private ArrayList<Product> productArrayList;

    AdapterProduct(Context context)
    {
        this.context = context;
        layoutLInflater = LayoutInflater.from(context);
        productArrayList = new ArrayList<Product>();
    }

    public void addAllProduct(ArrayList<Product> productArrayList)
    {
        this.productArrayList.clear();
        this.productArrayList.addAll(productArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductViewHolder(layoutLInflater.inflate(R.layout.compare_price_2, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        Product product = productArrayList.get(i);
        productViewHolder.setItemName(product.getItemName());
        productViewHolder.setPrice(product.getPrice());
        productViewHolder.setQuantity(product.getQuantity(), product.getRate());
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder
    {
        private TextView itemNameText;
        private TextView priceText;
        private TextView quantityText;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameText = itemView.findViewById(R.id.itemNameForCP);
            priceText = itemView.findViewById(R.id.textViewPrice);
            quantityText = itemView.findViewById(R.id.textViewQuantity);
        }

        public void setItemName(String itemName)
        {
            itemNameText.setText(itemName);
        }

        public void setPrice(float price)
        {
            priceText.setText(String.valueOf(price));
        }

        public void setQuantity(int quantity, String rate)
        {
            quantityText.setText(quantity + rate);
        }
    }
}