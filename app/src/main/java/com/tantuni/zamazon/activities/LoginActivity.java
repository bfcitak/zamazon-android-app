package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tantuni.zamazon.R;
import com.tantuni.zamazon.models.SharedPrefManager;
import com.tantuni.zamazon.models.URL;
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.models.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

        //validating inputs
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editTextUsername.setError("Enter a valid email!");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 4) {
            editTextPassword.setError("At least 4 characters!");
            editTextPassword.requestFocus();
            return;
        }

        //if everything is fine
        Map<String, String> data = new HashMap<>();
        data.put("email", username);
        data.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_LOGIN, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //if no error in response
                            if (!false) { //response.getBoolean("error")
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                                Log.d("responseObject", response.toString());
                                //getting the user from the response
                                JSONObject userJson = response.getJSONObject("user");
                                JSONArray userRoles = userJson.getJSONArray("roles");

                                Set<String> roles = new HashSet<>();
                                for (int i = 0; i < userRoles.length(); i++) {
                                    JSONObject roleJson = userRoles.getJSONObject(i);
                                    roles.add(roleJson.getString("role"));
                                }

                                //creating a new user object
                                User user = new User(
                                        userJson.getString("id"),
                                        userJson.getString("email"),
                                        userJson.getString("firstName"),
                                        userJson.getString("lastName"),
                                        userJson.getBoolean("active"),
                                        roles
                                );

                                Log.d("userObject", user.toString());
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("Invalid email or password!"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
