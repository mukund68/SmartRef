package com.example.android.smartrefrigerator.Activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.android.smartrefrigerator.Fragments.CompareOnlinePrices;
import com.example.android.smartrefrigerator.Fragments.Compartmentalization;
import com.example.android.smartrefrigerator.Fragments.FridgeContents;
import com.example.android.smartrefrigerator.Fragments.LowRunningProducts;
import com.example.android.smartrefrigerator.Fragments.ManageUser;
import com.example.android.smartrefrigerator.Fragments.RecipeSuggestion;
import com.example.android.smartrefrigerator.Fragments.ScanCompartment;
import com.example.android.smartrefrigerator.Fragments.ScanInvoice;
import com.example.android.smartrefrigerator.Fragments.SetExpiry;
import com.example.android.smartrefrigerator.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    public static Fragment fragment;

    private final CompareOnlinePrices compareOnlinePricesFragment = CompareOnlinePrices.getCompareOnlinePricesFragment();
    private final Compartmentalization compartmentalizationFragment = Compartmentalization.getCompartmentalizationFragment();
    private final FridgeContents fridgeContentFragment = FridgeContents.getFridgeContentFragment();
    private final LowRunningProducts lowRunningProductsFragment = LowRunningProducts.getLowRunningProductsFragment();
    private final ManageUser manageUserFragment = ManageUser.getManageUserFragment();
    private final RecipeSuggestion recipeSuggestionFragment = RecipeSuggestion.getRecipeSuggestionFragment();
    private final ScanCompartment scanCompartmentFragment = ScanCompartment.getScanCompartmentFragment();
    private final ScanInvoice scanInvoiceFragment = ScanInvoice.getScanInvoiceFragment();
    private final SetExpiry setExpiryFragment = SetExpiry.getSetExpiryFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_alert));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragment = scanCompartmentFragment;
        replaceFragment(fragment);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alert) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_scanCompartments:
                if (fragment != scanCompartmentFragment)
                    replaceFragment(scanCompartmentFragment);
                break;
            case R.id.nav_scanInvoice:
                if (fragment != scanInvoiceFragment)
                    replaceFragment(scanInvoiceFragment);
                break;
            case R.id.nav_compartmentalization:
                if (fragment != compartmentalizationFragment)
                    replaceFragment(compartmentalizationFragment);
                break;
            case R.id.nav_setExpiry:
                if (fragment != setExpiryFragment)
                    replaceFragment(setExpiryFragment);
                break;
            case R.id.nav_fridgeContents:
                if (fragment != fridgeContentFragment)
                    replaceFragment(fridgeContentFragment);
                break;
            case R.id.nav_recipeSuggestion:
                if (fragment != recipeSuggestionFragment)
                    replaceFragment(recipeSuggestionFragment);
                break;
            case R.id.nav_lowRunningItems:
                if (fragment != lowRunningProductsFragment)
                    replaceFragment(lowRunningProductsFragment);
                break;
            case R.id.nav_comparePrices:
                if (fragment != compareOnlinePricesFragment)
                    replaceFragment(compareOnlinePricesFragment);
                break;
            case R.id.nav_manageUser:
                if (fragment != manageUserFragment)
                    replaceFragment(manageUserFragment);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment fragment)
    {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commitAllowingStateLoss();
        MainActivity.fragment = fragment;
    }

}
