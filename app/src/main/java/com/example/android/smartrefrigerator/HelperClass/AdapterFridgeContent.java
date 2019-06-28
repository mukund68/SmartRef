package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.smartrefrigerator.ApiHandler.FridgeContent;
import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class AdapterFridgeContent extends RecyclerView.Adapter<AdapterFridgeContent.FridgeContentViewHolder>
{
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<FridgeContent> fridgeContentArrayList;

    public AdapterFridgeContent(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        fridgeContentArrayList = new ArrayList<FridgeContent>();
    }

    public void addFridgeContent(FridgeContent fridgeContent)
    {
        fridgeContentArrayList.add(fridgeContent);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FridgeContentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FridgeContentViewHolder(layoutInflater.inflate(R.layout.fridge_contents_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FridgeContentViewHolder fridgeContentViewHolder, int i) {
        FridgeContent fridgeContent = fridgeContentArrayList.get(i);

        fridgeContentViewHolder.setRackName(fridgeContent.getRack());
        fridgeContentViewHolder.setProducts(fridgeContent.getProducts());
    }

    @Override
    public int getItemCount() {
        return fridgeContentArrayList.size();
    }

    class FridgeContentViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView recyclerView;
        public FoldingCell foldingCell;
        private TextView rackComp;

        public FridgeContentViewHolder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell_FC);
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

            recyclerView = itemView.findViewById(R.id.fridge_cont_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(false);
            recyclerView.setNestedScrollingEnabled(false);

            rackComp = itemView.findViewById(R.id.rackCompFC);
        }

        void setProducts(ArrayList<Product> productArrayList)
        {
            AdapterFridgeContentProduct productAdapter = new AdapterFridgeContentProduct(context);
            productAdapter.addAllProduct(productArrayList);
            recyclerView.setAdapter(productAdapter);
        }

        public void setRackName(String rack)
        {
            rack.replace("r","Rack ");
            rackComp.setText(rack);
        }

    }

}