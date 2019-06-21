package com.tantuni.zamazon.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.models.CreditCard;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class AddCreditCardActivity extends AppCompatActivity {
    EditText name;
    EditText cardNumber;
    EditText cvcNumber;
    EditText expiryDate;
    Button confirmCreditCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_card);
        name = (EditText) findViewById(R.id.editTextCreditCardNameOnCard);
        cardNumber = (EditText) findViewById(R.id.editTextCreditCardNumber);
        cvcNumber = (EditText) findViewById(R.id.editTextCreditCardCvc);
        expiryDate = (EditText) findViewById(R.id.editTextCreditCardExpiryDate);
        confirmCreditCart = (Button) findViewById(R.id.buttonAddCreditCardConfirm);
        confirmCreditCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addCreditCart(view);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void addCreditCart(View view) throws ParseException {
        HashMap<String,Object> cardData = new HashMap<>();
        cardData.put("nameOnCard",name.getText().toString());
        cardData.put("cardNumber",cardNumber.getText().toString());
        cardData.put("cvc",cvcNumber.getText().toString());
        cardData.put("expiryDate",expiryDate.getText().toString());
        CustomerController.addUserCreditCard(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), cardData, new ProductCallback<ArrayList<CreditCard>>() {
            @Override
            public void onSuccess(ArrayList<CreditCard> object, String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }
}
