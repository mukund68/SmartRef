package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.smartrefrigerator.ApiHandler.ComparePrice;
import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class AdapterComparePrice extends RecyclerView.Adapter<AdapterComparePrice.ComparePriceViewHolder>
{
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ComparePrice> comparePriceArrayList;

    public AdapterComparePrice(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        comparePriceArrayList = new ArrayList<ComparePrice>();
    }

    public void addComparePrice(ComparePrice comparePrice)
    {
        comparePriceArrayList.add(comparePrice);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComparePriceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ComparePriceViewHolder(layoutInflater.inflate(R.layout.compare_price_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComparePriceViewHolder comparePriceViewHolder, int i) {
        ComparePrice comparePrice = comparePriceArrayList.get(i);
        comparePriceViewHolder.setStore(comparePrice.getStore());
        comparePriceViewHolder.setTotalBill(comparePrice.getTotalBill());
        comparePriceViewHolder.setProducts(comparePrice.getProducts());
    }

    @Override
    public int getItemCount() {
        return comparePriceArrayList.size();
    }

    class ComparePriceViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView recyclerView;
        public FoldingCell foldingCell;
        private ImageView storeImage;
        private TextView totalBillText;

        public ComparePriceViewHolder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell_cp);
            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try
                    {
                        foldingCell.toggle(false);
                    }
                    catch (Exception e)
                    {
                        foldingCell.toggle(true);
                    }

                }
            });

            recyclerView = itemView.findViewById(R.id.compare_price_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(false);
            recyclerView.setNestedScrollingEnabled(false);
            totalBillText = itemView.findViewById(R.id.totalPrice);
            storeImage = itemView.findViewById(R.id.storeImage);
        }

        void setProducts(ArrayList<Product> productArrayList)
        {
            AdapterProduct productAdapter = new AdapterProduct(context);
            productAdapter.addAllProduct(productArrayList);
            recyclerView.setAdapter(productAdapter);
        }

        public void setStore(String store)
        {
            store = store.replace(" ", "_");
            store = store.replace("'", "");
            store = store.toLowerCase();

            int res = context.getResources().getIdentifier("img_" + store, "drawable", context.getPackageName());
            storeImage.setImageResource(res);
        }

        public void setTotalBill(float totalBill)
        {
            totalBillText.setText(String.valueOf(totalBill));
        }
    }
}