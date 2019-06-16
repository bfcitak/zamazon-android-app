package com.tantuni.zamazon.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.models.Product;
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.UserCallback;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {
    public static List<User> users = new ArrayList<>();

    public static void getAllUsers(final Context context, final UserCallback<List<User>> userCallback){
        JsonObjectRequest getUsersRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_USERS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray usersJson = response.getJSONArray("users");
                            users = new Gson().fromJson(usersJson.toString(),new TypeToken<List<User>>(){}.getType());
                            userCallback.onSuccess(users, response.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userCallback.onError(error);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + SharedPrefManager.getInstance(context).getToken());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(getUsersRequest);
    }
    public static void banUser(final Context context, final UserCallback<User> userCallback,String userID){
        JsonObjectRequest banUserRequest = new JsonObjectRequest(Request.Method.DELETE, URL.URL_USERS + "/" + userID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.getBoolean("error")){
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                JSONObject userJson = response.getJSONObject("user");
                                User user = new Gson().fromJson(userJson.toString(),new TypeToken<User>(){}.getType());
                                userCallback.onSuccess(user,response.getString("message"));
                            }
                            else {
                                Toast.makeText(context,response.getString("message"),Toast.LENGTH_LONG);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userCallback.onError(error);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + SharedPrefManager.getInstance(context).getToken());
                return params;
            }

        };
        VolleySingleton.getInstance(context).addToRequestQueue(banUserRequest);
    }
    public static void banItem(final Context context, final ProductCallback<Product> productCallback,String productID){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, URL.URL_PRODUCTS + "/" + productID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(context,response.getString("message"),Toast.LENGTH_LONG).show();
                            JSONObject productJson = response.getJSONObject("product");
                            Product product = new Gson().fromJson(productJson.toString(),new TypeToken<Product>(){}.getType());
                            productCallback.onSuccess(product,response.getString("message"));
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        productCallback.onError(error);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + SharedPrefManager.getInstance(context).getToken());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
