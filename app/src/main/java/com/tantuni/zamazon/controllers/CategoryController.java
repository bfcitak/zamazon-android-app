package com.tantuni.zamazon.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.models.Category;
import com.tantuni.zamazon.models.SubCategory;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryController {
    public static ArrayList<Category> categories;
    public static ArrayList<SubCategory> subCategories;
    public static Category category;

    public static void getAllCategories(Context context, final ProductCallback<ArrayList<Category>> productCallback) {

        JsonObjectRequest getAllCategoriesRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_CATEGORIES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray categoriesJson = response.getJSONArray("categories");
                            categories = new Gson().fromJson(categoriesJson.toString(), new TypeToken<List<Category>>(){}.getType());
                            productCallback.onSuccess(categories, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getAllCategoriesRequest);
    }

    public static void getCategoryById(final Context context, String categoryId, final ProductCallback<Category> productCallback) {
        JsonObjectRequest getCategoryByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_CATEGORIES + "/" + categoryId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject categoryJson = response.getJSONObject("category");
                                category = new Gson().fromJson(categoryJson.toString(), new TypeToken<Category>(){}.getType());
                                productCallback.onSuccess(category, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getCategoryByIdRequest);
    }

    public static void getSubCategoriesOfCategory(final Context context, String categoryId, final ProductCallback<ArrayList<SubCategory>> productCallback) {
        JsonObjectRequest getSubCategoriesOfCategoryRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_CATEGORIES + "/" + categoryId + "/subCategories", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray subCategoriesJson = response.getJSONArray("subCategories");
                                subCategories = new Gson().fromJson(subCategoriesJson.toString(), new TypeToken<ArrayList<SubCategory>>(){}.getType());
                                productCallback.onSuccess(subCategories, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getSubCategoriesOfCategoryRequest);
    }
}
