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
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.UserCallback;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    public static void login(final Context context, final Map<String, String> loginData, final UserCallback<User> userCallback) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_LOGIN, new JSONObject(loginData),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //if no error in response
                            if (!false) { //response.getBoolean("error")
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = response.getJSONObject("user");

                                User user = new Gson().fromJson(userJson.toString(), new TypeToken<User>(){}.getType());
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(context).userLogin(user);
                                userCallback.onSuccess(user);
                            } else {
                                Toast.makeText(context, "Invalid email or password!", Toast.LENGTH_SHORT).show();
                            }
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", loginData.get("email"));
                params.put("password", loginData.get("password"));
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void signUp(final Context context, final Map<String, String> signUpData, final UserCallback<User> userCallback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_REGISTER, new JSONObject(signUpData),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //if no error in response
                            if (!false) {  //response.getBoolean("error")
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = response.getJSONObject("user");

                                //creating a new user object
                                User user = new Gson().fromJson(userJson.toString(), new TypeToken<User>(){}.getType());

                                userCallback.onSuccess(user);
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
                        userCallback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", signUpData.get("email"));
                params.put("password", signUpData.get("password"));
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getUserById(Context context, String userId, final UserCallback<User> userCallback) {
        JsonObjectRequest getUserByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_USERS + "/" + userId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User user = new Gson().fromJson(response.toString(), new TypeToken<User>(){}.getType());
                        userCallback.onSuccess(user);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userCallback.onError(error);
                    }
                }
        );
        VolleySingleton.getInstance(context).addToRequestQueue(getUserByIdRequest);
    }

}
