package com.example.android.smartrefrigerator.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartrefrigerator.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class SetExpiry extends Fragment {

    private static SetExpiry setExpiryFragment;

    private Button buttonToSetExpiry;
    private TextView textViewToSetExpiry;

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

        //Initialize Views
        buttonToSetExpiry = view.findViewById(R.id.buttonToSetExpiry);
        textViewToSetExpiry =  view.findViewById(R.id.textViewToSetExpiry);

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
                textViewToSetExpiry.setText(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static SetExpiry getSetExpiryFragment() {
        if (setExpiryFragment == null)
            setExpiryFragment = new SetExpiry();
        return setExpiryFragment;
    }

}
