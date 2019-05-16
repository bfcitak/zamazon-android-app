package com.tantuni.zamazon.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.models.Cart;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.VolleySingleton;
import com.tantuni.zamazon.networks.ProductCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController {
    public static ArrayList<Product> products = new ArrayList<>();
    public static Product product;

    public static void getAllProducts(Context context, final ProductCallback<ArrayList<Product>> productCallback) {

        JsonObjectRequest getAllProductsRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_PRODUCTS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray productsJson = response.getJSONArray("products");
                            products = new Gson().fromJson(productsJson.toString(), new TypeToken<List<Product>>(){}.getType());
                            productCallback.onSuccess(products, response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        productCallback.onError(error);
                    }
                }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(getAllProductsRequest);
    }

    public static void getProductById(final Context context, String productId, final ProductCallback<Product> productCallback) {
        JsonObjectRequest getProductByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_PRODUCTS + "/" + productId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject productJson = response.getJSONObject("product");
                                product = new Gson().fromJson(productJson.toString(), new TypeToken<Product>(){}.getType());
                                productCallback.onSuccess(product, response.getString("message"));
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        productCallback.onError(error);
                    }
                }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(getProductByIdRequest);
    }

}
