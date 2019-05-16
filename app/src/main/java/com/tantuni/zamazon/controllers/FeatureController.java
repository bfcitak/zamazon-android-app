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
import com.tantuni.zamazon.networks.ProductCallback;
import com.tantuni.zamazon.networks.URL;
import com.tantuni.zamazon.networks.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeatureController {
    public static ArrayList<Feature> features;
    public static Feature feature;

    public static void getAllFeatures(Context context, final ProductCallback<ArrayList<Feature>> productCallback) {

        JsonObjectRequest getAllFeaturesRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_FEATURES, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray featuresJson = response.getJSONArray("features");
                            features = new Gson().fromJson(featuresJson.toString(), new TypeToken<List<Feature>>(){}.getType());
                            productCallback.onSuccess(features, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getAllFeaturesRequest);
    }

    public static void getFeatureById(final Context context, String featureId, final ProductCallback<Feature> productCallback) {
        JsonObjectRequest getFeatureByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_FEATURES + "/" + featureId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject featureJson = response.getJSONObject("feature");
                                feature = new Gson().fromJson(featureJson.toString(), new TypeToken<Feature>(){}.getType());
                                productCallback.onSuccess(feature, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getFeatureByIdRequest);
    }

}
