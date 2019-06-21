package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.models.Address;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;

public class AddAddressActivity extends AppCompatActivity {
    EditText adressHeader;
    EditText adress;
    EditText receiverName;
    Button confirmButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        adressHeader = (EditText) findViewById(R.id.editTextAddressTitle);
        adress = (EditText) findViewById(R.id.editTextAddress);
        receiverName = (EditText) findViewById(R.id.editTextReceiverName);
        confirmButton = (Button) findViewById(R.id.buttonAddAddressConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAdress(view);
            }
        });
    }

    private void addAdress(View view){
        HashMap<String,Object> adressMap = new HashMap<>();
        adressMap.put("receiverName",receiverName.getText().toString());
        adressMap.put("address",adress.getText().toString());
        adressMap.put("title",adressHeader.getText().toString());

        CustomerController.addUserAddress(this, SharedPrefManager.getInstance(this).getUser().getId(),adressMap, new ProductCallback<ArrayList<Address>>() {

            @Override
            public void onSuccess(ArrayList<Address> object, String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception exception) {
                Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_LONG);
            }
        });
    }


}
