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
import com.tantuni.zamazon.models.Admin;
import com.tantuni.zamazon.models.Customer;
import com.tantuni.zamazon.models.Role;
import com.tantuni.zamazon.models.Seller;
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.UserCallback;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserController {

    public static void login(final Context context, final Map<String, String> loginData, final UserCallback<User> userCallback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_LOGIN, new JSONObject(loginData),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //if no error in response
                            if (!response.getBoolean("error")) {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                //getting the user from the response
                                JSONObject userJson = response.getJSONObject("user");
                                JSONArray rolesJson = userJson.getJSONArray("roles");
                                String token = response.getString("token");
                                Set<Role> roles = new Gson().fromJson(rolesJson.toString(), new TypeToken<Set<Role>>(){}.getType());
                                Role role = roles.iterator().next();

                                if (role.getRole().equals("CUSTOMER")) {
                                    Customer user = new Gson().fromJson(userJson.toString(), new TypeToken<Customer>(){}.getType());
                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(context).userLogin(user, token);
                                    Log.d("LOGIN", user.toString());
                                    if (user.getActive())
                                        userCallback.onSuccess(user, response.getString("message"));
                                    else
                                        userCallback.onError(new AuthFailureError("BAN"));
                                } else if (role.getRole().equals("SELLER")) {
                                    Seller user = new Gson().fromJson(userJson.toString(), new TypeToken<Seller>(){}.getType());
                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(context).userLogin(user, token);
                                    if (user.getActive())
                                        userCallback.onSuccess(user, response.getString("message"));
                                    else
                                        userCallback.onError(new AuthFailureError("BAN"));
                                } else if (role.getRole().equals("ADMIN")) {
                                    Admin user = new Gson().fromJson(userJson.toString(), new TypeToken<Admin>(){}.getType());
                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(context).userLogin(user, token);
                                    userCallback.onSuccess(user, response.getString("message"));
                                } else {
                                    Toast.makeText(context, "Authorization Error!", Toast.LENGTH_SHORT).show();
                                }

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
                params.put("username", loginData.get("email"));
                params.put("password", loginData.get("password"));
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void signUp(final Context context, final Map<String, String> signUpData, final UserCallback<User> userCallback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_REGISTER_CUSTOMER, new JSONObject(signUpData),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //if no error in response
                            if (!response.getBoolean("error")) {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                                //getting the user from the response
                                JSONObject userJson = response.getJSONObject("user");
                                //creating a new user object
                                User user = new Gson().fromJson(userJson.toString(), new TypeToken<User>(){}.getType());
                                userCallback.onSuccess(user, response.getString("message"));
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

    public static void getUserById(final Context context, String userId, final UserCallback<User> userCallback) {
        JsonObjectRequest getUserByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_USERS + "/" + userId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User user = new Gson().fromJson(response.toString(), new TypeToken<User>(){}.getType());
                        userCallback.onSuccess(user, "MESSAGE");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        userCallback.onError(error);
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
        VolleySingleton.getInstance(context).addToRequestQueue(getUserByIdRequest);
    }

    public static void changePassword(final Context context, User user, final Map<String, String> passwordData, final UserCallback<User> userCallback) {
        JsonObjectRequest changePasswordRequest = new JsonObjectRequest(Request.Method.PUT, URL.URL_USERS + "/" + user.getId(), new JSONObject(passwordData),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //getting the user from the response
                        try {
                            String message = response.getString("message");
                            JSONObject userJson = response.getJSONObject("user");
                            if (!response.getBoolean("error")) {
                                User user = new Gson().fromJson(userJson.toString(), new TypeToken<User>(){}.getType());
                                userCallback.onSuccess(user, message);
                            } else {
                                User user = new Gson().fromJson(userJson.toString(), new TypeToken<User>(){}.getType());
                                userCallback.onSuccess(user, message);
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "Bearer " + SharedPrefManager.getInstance(context).getToken());
                return params;
            }
            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("oldPassword", passwordData.get("oldPassword"));
                params.put("newPassword", passwordData.get("newPassword"));
                params.put("newRePassword", passwordData.get("newRePassword"));
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(changePasswordRequest);
    }

}
