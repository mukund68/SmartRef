package com.example.android.smartrefrigerator.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.smartrefrigerator.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;

public class SetExpiry extends Fragment {

    private static SetExpiry setExpiryFragment;

    private Button buttonToSetExpiry;
    private TextView textViewToSetExpiry;
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

        //Initialize Views
        buttonToSetExpiry = view.findViewById(R.id.buttonToSetExpiry);
        textViewToSetExpiry =  view.findViewById(R.id.textViewToSetExpiry);

        buton_calendar = view.findViewById(R.id.buton_calendar);

        buttonToSetExpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator.forSupportFragment(setExpiryFragment).initiateScan();
            }
        });

        /*buton_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize a new date picker dialog fragment
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });*/
    }

   /* public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

            // Create a TextView programmatically.
            TextView tv = new TextView(getActivity());

            // Create a TextView programmatically
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                    RelativeLayout.LayoutParams.WRAP_CONTENT); // Height of TextView
            tv.setLayoutParams(lp);
            tv.setPadding(10, 10, 10, 10);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
            //tv.setText("This is a custom title.");
            //tv.setText(calendar.getTime().toString());
            StringBuffer sb = new StringBuffer();
            sb.append(day+" "+month+" "+year);
            tv.setText(sb.toString());
            tv.setTextColor(Color.parseColor("#ff0000"));
            tv.setBackgroundColor(Color.parseColor("#FFD2DAA7"));

            // Set the newly created TextView as a custom tile of DatePickerDialog
            dpd.setCustomTitle(tv);

            // Or you can simply set a tile for DatePickerDialog
            *//*
                setTitle(CharSequence title)
                    Set the title text for this dialog's window.
            *//*
            // dpd.setTitle("This is a simple title."); // Uncomment this line to activate it

            // Return the DatePickerDialog
            return  dpd;
        }*/

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
