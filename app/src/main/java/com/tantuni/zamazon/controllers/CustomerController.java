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
import com.tantuni.zamazon.models.Address;
import com.tantuni.zamazon.models.Cart;
import com.tantuni.zamazon.models.CreditCard;
import com.tantuni.zamazon.models.Order;
import com.tantuni.zamazon.models.WishList;
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

    public static void getUserWishListById(final Context context, String userId, final ProductCallback<WishList> productCallback) {
        JsonObjectRequest getUserWishListByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_CUSTOMERS + "/" + userId + "/wishList", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject wishListJson = response.getJSONObject("wishList");
                                WishList wishList = new Gson().fromJson(wishListJson.toString(), new TypeToken<WishList>(){}.getType());
                                productCallback.onSuccess(wishList, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getUserWishListByIdRequest);
    }

    public static void addProductToWishList(final Context context, String userId, String productId, final ProductCallback<WishList> productCallback) {
        Log.d("CUSTOMERCON","FUNC");
        Map<String, String> productInfo = new HashMap<>();
        productInfo.put("productId", productId);
        JsonObjectRequest addProductToWishListRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_CUSTOMERS + "/" + userId + "/wishList", new JSONObject(productInfo),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject wishListJson = response.getJSONObject("wishList");
                                WishList wishList = new Gson().fromJson(wishListJson.toString(), new TypeToken<WishList>(){}.getType());
                                productCallback.onSuccess(wishList, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(addProductToWishListRequest);
    }

    public static void removeProductFromWishList(final Context context, String userId, String productId, final ProductCallback<WishList> productCallback) {
        JsonObjectRequest removeProductFromCartRequest = new JsonObjectRequest(Request.Method.DELETE, URL.URL_CUSTOMERS + "/" + userId + "/wishList/" + productId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONObject wishListJson = response.getJSONObject("wishList");
                                WishList wishList = new Gson().fromJson(wishListJson.toString(), new TypeToken<WishList>() {}.getType());
                                productCallback.onSuccess(wishList, response.getString("message"));
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

    public static void getUserCreditCardsById(final Context context, String userId, final ProductCallback<ArrayList<CreditCard>> productCallback) {
        JsonObjectRequest getUserCreditCardsByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_CUSTOMERS + "/" + userId + "/creditCards", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray creditCardsJson = response.getJSONArray("creditCards");
                                ArrayList<CreditCard> creditCards = new Gson().fromJson(creditCardsJson.toString(), new TypeToken<ArrayList<CreditCard>>(){}.getType());
                                productCallback.onSuccess(creditCards, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getUserCreditCardsByIdRequest);
    }

    public static void getUserAddressesById(final Context context, String userId, final ProductCallback<ArrayList<Address>> productCallback) {
        JsonObjectRequest getUserAddressesByIdRequest = new JsonObjectRequest(Request.Method.GET, URL.URL_CUSTOMERS + "/" + userId + "/addresses", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray addressesJson = response.getJSONArray("addresses");
                                ArrayList<Address> addresses = new Gson().fromJson(addressesJson.toString(), new TypeToken<ArrayList<Address>>(){}.getType());
                                productCallback.onSuccess(addresses, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(getUserAddressesByIdRequest);
    }

    public static void addUserCreditCard(final Context context, String userId, Map<String, Object> creditCardData, final ProductCallback<ArrayList<CreditCard>> productCallback) {
        JsonObjectRequest addUserCreditCardRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_CUSTOMERS + "/" + userId + "/creditCards", new JSONObject(creditCardData),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray creditCardsJson = response.getJSONArray("creditCards");
                                ArrayList<CreditCard> creditCards = new Gson().fromJson(creditCardsJson.toString(), new TypeToken<ArrayList<CreditCard>>(){}.getType());
                                productCallback.onSuccess(creditCards, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(addUserCreditCardRequest);
    }

    public static void addUserAddress(final Context context, String userId, Map<String, Object> addressData, final ProductCallback<ArrayList<Address>> productCallback) {
        JsonObjectRequest addUserAddressRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_CUSTOMERS + "/" + userId + "/addresses", new JSONObject(addressData),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray addressesJson = response.getJSONArray("addresses");
                                ArrayList<Address> addresses = new Gson().fromJson(addressesJson.toString(), new TypeToken<ArrayList<Address>>(){}.getType());
                                productCallback.onSuccess(addresses, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(addUserAddressRequest);
    }

    public static void removeUserCreditCard(final Context context, String userId, String creditCardId, final ProductCallback<ArrayList<CreditCard>> productCallback) {
        JsonObjectRequest removeUserCreditCardRequest = new JsonObjectRequest(Request.Method.DELETE, URL.URL_CUSTOMERS + "/" + userId + "/creditCards/" + creditCardId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray creditCardsJson = response.getJSONArray("creditCards");
                                ArrayList<CreditCard> creditCards = new Gson().fromJson(creditCardsJson.toString(), new TypeToken<ArrayList<CreditCard>>() {}.getType());
                                productCallback.onSuccess(creditCards, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(removeUserCreditCardRequest);
    }

    public static void removeUserAddress(final Context context, String userId, String addressId, final ProductCallback<ArrayList<Address>> productCallback) {
        JsonObjectRequest removeUserAddressRequest = new JsonObjectRequest(Request.Method.DELETE, URL.URL_CUSTOMERS + "/" + userId + "/addresses/" + addressId, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!response.getBoolean("error")) {
                                JSONArray addressesJson = response.getJSONArray("addresses");
                                ArrayList<Address> addresses = new Gson().fromJson(addressesJson.toString(), new TypeToken<ArrayList<Address>>() {}.getType());
                                productCallback.onSuccess(addresses, response.getString("message"));
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
        VolleySingleton.getInstance(context).addToRequestQueue(removeUserAddressRequest);
    }

}
