package com.example.android.smartrefrigerator.HelperClass;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.smartrefrigerator.ApiHandler.CompartmentResult;
import com.example.android.smartrefrigerator.ApiHandler.Product;
import com.example.android.smartrefrigerator.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class AdapterCompartmentalization extends RecyclerView.Adapter<AdapterCompartmentalization.CompartmentResultViewHolder>
{
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<CompartmentResult> compartmentResultArrayList;

    public AdapterCompartmentalization(Context context)
    {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        compartmentResultArrayList = new ArrayList<CompartmentResult>();
    }

    public void addCompartmentResult(CompartmentResult compartmentResult)
    {
        compartmentResultArrayList.add(compartmentResult);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompartmentResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CompartmentResultViewHolder(layoutInflater.inflate(R.layout.compartmentalization_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CompartmentResultViewHolder compartmentResultViewHolder, int i) {
        CompartmentResult compartmentResult = compartmentResultArrayList.get(i);

        compartmentResultViewHolder.setRackName(compartmentResult.getRack());
        compartmentResultViewHolder.setProducts(compartmentResult.getProducts());
    }

    @Override
    public int getItemCount() {
        return compartmentResultArrayList.size();
    }

    class CompartmentResultViewHolder extends RecyclerView.ViewHolder
    {
        private RecyclerView recyclerView;
        public FoldingCell foldingCell;
        private TextView rackComp;

        public CompartmentResultViewHolder(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell_comp);
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

            recyclerView = itemView.findViewById(R.id.compart_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setHasFixedSize(false);
            recyclerView.setNestedScrollingEnabled(false);

            rackComp = itemView.findViewById(R.id.rackComp);
        }

        void setProducts(ArrayList<Product> productArrayList)
        {
            AdapterCompartmentalizationProduct productAdapter = new AdapterCompartmentalizationProduct(context);
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