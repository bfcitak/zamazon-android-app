package com.tantuni.zamazon.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.CustomerController;
import com.tantuni.zamazon.controllers.ProductController;
import com.tantuni.zamazon.controllers.adapters.ProductFeatureAdapter;
import com.tantuni.zamazon.models.Cart;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.models.WishList;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView textViewProductHeader, textViewProductDescription, textViewProductCategory;
    RecyclerView recyclerViewProductDetails;
    ProgressBar progressBarProductDetails;
    ProductFeatureAdapter productFeatureAdapter;
    CustomerController customerController;
    ArrayList<String> featureKeys, featureValues;
    Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        progressBarProductDetails = (ProgressBar) findViewById(R.id.progressBarProductDetails);

        recyclerViewProductDetails = (RecyclerView) findViewById(R.id.recyclerViewProductDetails);

        textViewProductHeader = (TextView) findViewById(R.id.textViewProductHeader);
        textViewProductDescription = (TextView) findViewById(R.id.textViewProductDescription);
        textViewProductCategory = (TextView) findViewById(R.id.textViewProductCategory);

        String productId = getIntent().getStringExtra("productId");

        ProductController.getProductById(this, productId, new ProductCallback<Product>() {
            @Override
            public void onSuccess(Product product, String message) {
                progressBarProductDetails.setVisibility(View.GONE);
                currentProduct = product;
                featureKeys = new ArrayList<>(Arrays.asList("Price", "Stock", "Rate"));
                featureValues = new ArrayList<>(Arrays.asList(product.getPrice().toString() + " TL", "17", "3.8"));
                setTitle(product.getHeader());
                textViewProductCategory.setText(getString(R.string.full_category, product.getCategory().getName(), product.getSubCategory().getName()));
                textViewProductDescription.setText(product.getDescription());
                textViewProductHeader.setText(product.getHeader());
                setupRecycler();
            }

            @Override
            public void onError(Exception exception) {
                progressBarProductDetails.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setupRecycler() {
        productFeatureAdapter = new ProductFeatureAdapter(getApplicationContext(), featureKeys, featureValues);
        recyclerViewProductDetails.setAdapter(productFeatureAdapter);
        recyclerViewProductDetails.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewProductDetails.addItemDecoration(new DividerItemDecoration(recyclerViewProductDetails.getContext(), DividerItemDecoration.VERTICAL));
    }

    public void addProductToCart(View view) {
        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            customerController.addProductToCart(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), currentProduct.getId(), new ProductCallback<Cart>() {
                @Override
                public void onSuccess(Cart cart, String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception exception) {
                    Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "You must be logged in!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addProductToWishList(View view) {
        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            Log.d("PRODUCT DETAILS","CLİCK");
            customerController.addProductToWishList(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser().getId(), currentProduct.getId(), new ProductCallback<WishList>() {
                @Override
                public void onSuccess(WishList wishList, String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Exception exception) {
                    Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "You must be logged in!", Toast.LENGTH_SHORT).show();
        }
    }
}
