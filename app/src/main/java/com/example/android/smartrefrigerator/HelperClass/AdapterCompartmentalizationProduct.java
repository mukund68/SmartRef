package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.R;

import java.util.ArrayList;

public class AdapterCompartmentalizationProduct extends RecyclerView.Adapter<AdapterCompartmentalizationProduct.ProductViewHolder>
{
    private Context context;
    private LayoutInflater layoutLInflater;
    private ArrayList<Product> productArrayList;

    AdapterCompartmentalizationProduct(Context context)
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
        return new ProductViewHolder(layoutLInflater.inflate(R.layout.compartmentalization_2, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int i) {
        Product product = productArrayList.get(i);

        productViewHolder.setItemImage(product.getItemName());
        productViewHolder.setItemName(product.getItemName());
        productViewHolder.setQuantity(product.getQuantity(), product.getRate());

    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder
    {
        private TextView itemNameText;
        private TextView quantityText;
        private ImageView imageViewForComp;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameText = itemView.findViewById(R.id.itemNameForComp);
            quantityText = itemView.findViewById(R.id.textViewQuantityComp);
            imageViewForComp = itemView.findViewById(R.id.imageViewForComp);
        }

        public void setItemImage(String itemName)
        {
            itemName = itemName.toLowerCase();

            int res = context.getResources().getIdentifier("img_" + itemName, "drawable", context.getPackageName());
            imageViewForComp.setImageResource(res);
        }

        public void setItemName(String itemName)
        {
            itemNameText.setText(itemName);
        }

        public void setQuantity(int quantity, String rate)
        {
            quantityText.setText(String.valueOf(quantity).concat(rate));
        }
    }
}