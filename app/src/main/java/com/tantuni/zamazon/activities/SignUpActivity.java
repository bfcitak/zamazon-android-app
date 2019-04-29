package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

public class SignUpActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //if the user is already logged in we will directly start the profile activity
        /* if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
            return;
        } */

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();

        // first we will do the validations
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 4) {
            editTextPassword.setError("At least 4 characters!");
            editTextPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(firstName)) {
            editTextFirstName.setError("Enter your first name!");
            editTextFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            editTextLastName.setError("Enter your last name!");
            editTextLastName.requestFocus();
            return;
        }

        //if everything is fine
        Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", password);
        data.put("firstName", firstName);
        data.put("lastName", lastName);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL.URL_REGISTER, new JSONObject(data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);

                        try {

                            //if no error in response
                            if (!false) {  //response.getBoolean("error")
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

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

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
}
