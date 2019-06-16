package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.ProductController;
import com.tantuni.zamazon.controllers.SubCategoryController;
import com.tantuni.zamazon.fragments.SellerHomeFragment;
import com.tantuni.zamazon.models.Category;
import com.tantuni.zamazon.models.Feature;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.models.Seller;
import com.tantuni.zamazon.models.SubCategory;
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    private EditText editTextAddHeader, editTextAddDescription, editTextAddPrice;
    private TextView textViewFullCategory;
    private LinearLayout linearLayoutAddProduct;

    private ProductController productController;
    private SubCategoryController subCategoryController;

    private Map<String, Spinner> spinnerMap = new HashMap<>();

    SubCategory subCategory;
    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        linearLayoutAddProduct = findViewById(R.id.linearLayoutAddProduct);

        textViewFullCategory = (TextView) findViewById(R.id.textViewFullCategory);

        editTextAddHeader = (EditText) findViewById(R.id.editTextAddHeader);
        editTextAddDescription = (EditText) findViewById(R.id.editTextAddDescription);
        editTextAddPrice = (EditText) findViewById(R.id.editTextAddPrice);

        String categoryString = getIntent().getStringExtra("category");
        String subCategoryString = getIntent().getStringExtra("subCategory");

        subCategory = new Gson().fromJson(subCategoryString, new TypeToken<SubCategory>(){}.getType());
        category = new Gson().fromJson(categoryString, new TypeToken<Category>(){}.getType());

        textViewFullCategory.setText(getString(R.string.full_category, category.getName(), subCategory.getName()));

        subCategoryController.getFeaturesOfSubCategory(getApplicationContext(), subCategory.getId(), new ProductCallback<ArrayList<Feature>>() {
            @Override
            public void onSuccess(ArrayList<Feature> features, String message) {
                for (Feature feature : features) {
                    addSpinnerForFeature(feature.getKey(), feature.getOptions());
                }
            }
            @Override
            public void onError(Exception exception) {
                Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addSpinnerForFeature(String featureKey, List<String> options) {
        final EditText editText = new EditText(this);
        editText.setHint(featureKey);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        String[] optionsArray = options.toArray(new String[options.size()]);
        Spinner spinner = new Spinner(this);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, optionsArray));

        spinner.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Add spinner to LinearLayout
        if (linearLayoutAddProduct != null) {
            linearLayoutAddProduct.addView(spinner);
            spinnerMap.put(featureKey, spinner);
        }
    }

    public Boolean validateProductData(String header, String description, String price) {
        if (TextUtils.isEmpty(header)) {
            editTextAddHeader.setError("Enter a header!");
            editTextAddHeader.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(description)) {
            editTextAddDescription.setError("Enter a description!");
            editTextAddDescription.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(price)) {
            editTextAddPrice.setError("Enter a price!");
            editTextAddPrice.requestFocus();
            return false;
        }
        return true;
    }

    public void addProduct(View view) {
        String header = editTextAddHeader.getText().toString().trim();
        String description = editTextAddDescription.getText().toString().trim();
        String price = editTextAddPrice.getText().toString().trim();
        if (validateProductData(header, description, price)) {
            //if everything is fine
            User user = SharedPrefManager.getInstance(this).getUser();
            Seller seller = new Seller(user.getId(), user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getActive(), user.getRoles());
            Map<String, Object> productData = new HashMap<>();
            Map<String, String> productFeatures = new HashMap<>();
            for (Map.Entry<String, Spinner> entry : spinnerMap.entrySet()) {
                productFeatures.put(entry.getKey(), entry.getValue().getSelectedItem().toString());
            }

            HashMap<String, String> categoryRef = new HashMap<>();
            categoryRef.put("$ref", "categories");
            categoryRef.put("id", category.getId());

            HashMap<String, String> subCategoryRef = new HashMap<>();
            subCategoryRef.put("$ref", "subCategories");
            subCategoryRef.put("id", subCategory.getId());

            HashMap<String, String> sellerRef = new HashMap<>();
            sellerRef.put("$ref", "users");
            sellerRef.put("id", seller.getId());

            productData.put("header", header);
            productData.put("description", description);
            productData.put("price", Double.parseDouble(price));
            productData.put("seller", sellerRef);
            productData.put("features", productFeatures);
            productData.put("category", categoryRef);
            productData.put("subCategory", subCategoryRef);
            productData.put("active", true);


            productController.addProduct(this, productData, new ProductCallback<Product>() {
                @Override
                public void onSuccess(Product product, String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent sellerActivity = new Intent(getApplicationContext(), SellerActivity.class);
                    startActivity(sellerActivity);
                }

                @Override
                public void onError(Exception exception) {
                    Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
