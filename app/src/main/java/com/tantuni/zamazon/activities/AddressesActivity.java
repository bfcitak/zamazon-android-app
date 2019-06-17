package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.controllers.adapters.AddressesAdapter;
import com.tantuni.zamazon.controllers.adapters.CreditCardsAdapter;
import com.tantuni.zamazon.models.Address;
import com.tantuni.zamazon.models.CreditCard;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;

public class AddressesActivity extends AppCompatActivity {

    ProgressBar progressBarAddresses;
    RecyclerView recyclerViewAddresses;
    AddressesAdapter addressesAdapter;
    CustomerController customerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        recyclerViewAddresses = (RecyclerView) findViewById(R.id.recyclerViewAddresses);
        progressBarAddresses = (ProgressBar) findViewById(R.id.progressBarAddresses);

        customerController.getUserAddressesById(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), new ProductCallback<ArrayList<Address>>() {
            @Override
            public void onSuccess(ArrayList<Address> addresses, String message) {
                progressBarAddresses.setVisibility(View.GONE);
                setupRecycler(addresses);
            }

            @Override
            public void onError(Exception exception) {
                progressBarAddresses.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setupRecycler(ArrayList<Address> address) {
        addressesAdapter = new AddressesAdapter(getApplicationContext(), address);
        recyclerViewAddresses.setAdapter(addressesAdapter);
        recyclerViewAddresses.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void openAddAddressActivity(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(), AddAddressActivity.class));
    }
}
