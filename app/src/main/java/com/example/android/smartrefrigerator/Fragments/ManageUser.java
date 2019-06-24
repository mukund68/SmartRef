package com.example.android.smartrefrigerator.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.smartrefrigerator.R;

public class ManageUser extends Fragment {

    private static ManageUser manageUserFragment;

    public ManageUser() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_user, container, false);
    }

    public static ManageUser getManageUserFragment() {
        if (manageUserFragment == null)
            manageUserFragment = new ManageUser();
        return manageUserFragment;
    }

}
