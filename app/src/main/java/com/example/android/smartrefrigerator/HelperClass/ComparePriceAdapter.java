package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartrefrigerator.ApiHandler.ComparePrice;
import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class ComparePriceAdapter extends RecyclerView.Adapter<ComparePriceAdapter.ComparePriceViewHolder>
{
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ComparePrice> comparePriceArrayList;

    public ComparePriceAdapter(Context context)
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

        public ComparePriceViewHolder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell_cp);
            foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (foldingCell.isUnfolded())
                    {
                        try
                        {
                            foldingCell.toggle(false);
                        }
                        catch (Exception e)
                        {
                            foldingCell.toggle(true);
                        }
                    }
                }
            });
            recyclerView = itemView.findViewById(R.id.compare_price_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(false);
            recyclerView.setNestedScrollingEnabled(false);
        }

        void setProducts(ArrayList<Product> productArrayList)
        {
            ProductAdapter productAdapter = new ProductAdapter(context);
            productAdapter.addAllProduct(productArrayList);
            recyclerView.setAdapter(productAdapter);
        }
    }
}
