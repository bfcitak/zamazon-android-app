package com.tantuni.zamazon.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.ProductController;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.networks.ProductCallback;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView textViewId, textViewHeader, textViewDescription, textViewIsActive;
    ProgressBar progressBarProductDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        progressBarProductDetails = (ProgressBar) findViewById(R.id.progressBarProductDetails);
        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewHeader = (TextView) findViewById(R.id.textViewHeader);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        textViewIsActive = (TextView) findViewById(R.id.textViewIsActive);

        String productId = getIntent().getStringExtra("productId");
        textViewId.setText(productId);

        ProductController.getProductById(this, productId, new ProductCallback<Product>() {
            @Override
            public void onSuccess(Product product) {
                progressBarProductDetails.setVisibility(View.GONE);
                textViewHeader.setText(product.getHeader());
                textViewDescription.setText(product.getDescription());
                textViewIsActive.setText(product.getActive().toString());
                Toast.makeText(getApplicationContext(), product.toString(), Toast.LENGTH_LONG);
            }

            @Override
            public void onError(Exception exception) {
                progressBarProductDetails.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG);
            }
        });
    }
}
