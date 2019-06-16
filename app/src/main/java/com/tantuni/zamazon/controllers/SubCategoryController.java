package com.tantuni.zamazon.controllers;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tantuni.zamazon.models.Feature;
import com.tantuni.zamazon.models.SubCategory;
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryController {
    public static ArrayList<SubCategory> subCategories;
    public static ArrayList<Feature> features;
    public static SubCategory subCategory;

    public static void getAllSubCategories(Context context, final ProductCallback<ArrayList<SubCategory>> productCallback) {

        JsonObjectRequest getAllSubCategoriesRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_SUBCATEGORIES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray subCategoriesJson = response.getJSONArray("subCategories");
                            subCategories = new Gson().fromJson(subCategoriesJson.toString(), new TypeToken<List<SubCategory>>(){}.getType());
                            productCallback.onSuccess(subCategories, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getAllSubCategoriesRequest);
    }

    public static void getSubCategoryById(final Context context, String subCategoryId, final ProductCallback<SubCategory> productCallback) {
        JsonObjectRequest getSubCategoryByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_SUBCATEGORIES + "/" + subCategoryId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject subCategoryJson = response.getJSONObject("subCategory");
                                subCategory = new Gson().fromJson(subCategoryJson.toString(), new TypeToken<SubCategory>(){}.getType());
                                productCallback.onSuccess(subCategory, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getSubCategoryByIdRequest);
    }

    public static void getFeaturesOfSubCategory(final Context context, String subCategoryId, final ProductCallback<ArrayList<Feature>> productCallback) {
        JsonObjectRequest getFeaturesOfSubCategoryRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_SUBCATEGORIES + "/" + subCategoryId + "/features", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray featuresJson = response.getJSONArray("features");
                                features = new Gson().fromJson(featuresJson.toString(), new TypeToken<ArrayList<Feature>>(){}.getType());
                                productCallback.onSuccess(features, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getFeaturesOfSubCategoryRequest);
    }
}
