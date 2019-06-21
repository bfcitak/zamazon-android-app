package com.tantuni.zamazon.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.models.Order;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderController {

    public static void addOrder(final Context context, Order order, final ProductCallback<ArrayList<Order>> productCallback) throws JSONException {
        JsonObjectRequest addOrderRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_ORDERS, new JSONObject(new Gson().toJson(order, new TypeToken<Order>(){}.getType())),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray ordersJson = response.getJSONArray("orders");
                                ArrayList<Order> orders = new Gson().fromJson(ordersJson.toString(), new TypeToken<ArrayList<Order>>(){}.getType());
                                productCallback.onSuccess(orders, response.getString("message"));
                            } else {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
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
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + SharedPrefManager.getInstance(context).getToken());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(addOrderRequest);
    }
}
