package com.tantuni.zamazon.controllers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.VolleySingleton;
import com.tantuni.zamazon.networks.ProductCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductController {
    public static ArrayList<Product> products = new ArrayList<>();
    public static Product product;

    public static void getAllProducts(Context context, final ProductCallback<ArrayList<Product>> productCallback) {

        JsonArrayRequest getAllProductsRequest = new JsonArrayRequest(Request.Method.GET, URL.URL_PRODUCTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        products = new Gson().fromJson(response.toString(), new TypeToken<List<Product>>(){}.getType());
                        productCallback.onSuccess(products);
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

    public static void getProductById(Context context, String productId, final ProductCallback<Product> productCallback) {
        JsonObjectRequest getProductByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_PRODUCTS + "/" + productId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        product = new Gson().fromJson(response.toString(), new TypeToken<Product>(){}.getType());
                        productCallback.onSuccess(product);
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
