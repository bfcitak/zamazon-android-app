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
import com.tantuni.zamazon.controllers.adapters.CreditCardsAdapter;
import com.tantuni.zamazon.models.CreditCard;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;

public class CreditCardsActivity extends AppCompatActivity {

    ProgressBar progressBarCreditCards;
    RecyclerView recyclerViewCreditCards;
    CreditCardsAdapter creditCardsAdapter;
    CustomerController customerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_cards);

        recyclerViewCreditCards = (RecyclerView) findViewById(R.id.recyclerViewCreditCards);
        progressBarCreditCards = (ProgressBar) findViewById(R.id.progressBarCreditCards);

        customerController.getUserCreditCardsById(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), new ProductCallback<ArrayList<CreditCard>>() {
            @Override
            public void onSuccess(ArrayList<CreditCard> creditCards, String message) {
                progressBarCreditCards.setVisibility(View.GONE);
                setupRecycler(creditCards);
            }

            @Override
            public void onError(Exception exception) {
                progressBarCreditCards.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setupRecycler(ArrayList<CreditCard> creditCards) {
        creditCardsAdapter = new CreditCardsAdapter(getApplicationContext(), creditCards);
        recyclerViewCreditCards.setAdapter(creditCardsAdapter);
        recyclerViewCreditCards.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void openAddCreditCardActivity(View view) {
        finish();
        startActivity(new Intent(getApplicationContext(), AddCreditCardActivity.class));
    }
}
