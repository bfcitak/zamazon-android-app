package com.tantuni.zamazon.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.models.Cart;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomerController {

    public static void getUserCartById(final Context context, String userId, final ProductCallback<Cart> productCallback) {
        JsonObjectRequest getUserCartByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_CUSTOMERS + "/" + userId + "/cart", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject cartJson = response.getJSONObject("cart");
                                Cart cart = new Gson().fromJson(cartJson.toString(), new TypeToken<Cart>(){}.getType());
                                productCallback.onSuccess(cart, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getUserCartByIdRequest);
    }

    public static void addProductToCart(final Context context, String userId, String productId, final ProductCallback<Cart> productCallback) {
        Map<String, String> productInfo = new HashMap<>();
        productInfo.put("productId", productId);
        JsonObjectRequest addProductToCartRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_CUSTOMERS + "/" + userId + "/cart", new JSONObject(productInfo),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject cartJson = response.getJSONObject("cart");
                                Cart cart = new Gson().fromJson(cartJson.toString(), new TypeToken<Cart>(){}.getType());
                                productCallback.onSuccess(cart, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(addProductToCartRequest);
    }

    public static void removeProductFromCart(final Context context, String userId, String productId, final ProductCallback<Cart> productCallback) {
        JsonObjectRequest removeProductFromCartRequest = new JsonObjectRequest(Request.Method.DELETE, URL.URL_CUSTOMERS + "/" + userId + "/cart/" + productId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject cartJson = response.getJSONObject("cart");
                                Cart cart = new Gson().fromJson(cartJson.toString(), new TypeToken<Cart>() {}.getType());
                                productCallback.onSuccess(cart, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(removeProductFromCartRequest);
    }
}
