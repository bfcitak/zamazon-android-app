package com.tantuni.zamazon.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.controllers.UserController;
import com.tantuni.zamazon.models.User;
import com.tantuni.zamazon.networks.SharedPrefManager;
import com.tantuni.zamazon.networks.UserCallback;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    ProgressBar progressBarLogin;
    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        Log.d("EARTIK","EARTIK2");
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        //if user presses on login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // first getting the values
                final String username = editTextUsername.getText().toString();
                final String password = editTextPassword.getText().toString();
                userLogin(username, password);
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                Log.d("EARTIK","EARTIK2");
                finish();
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    public void userLogin(final String username, String password) {

        progressBarLogin.setVisibility(View.VISIBLE);
        Log.d("EARTIK","EARTIK");
        if (validateLoginData(username, password)) {
            // if everything is fine
            final Map<String, String> loginData = new HashMap<>();
            loginData.put("email", username);
            loginData.put("password", password);

            userController.login(getApplicationContext(), loginData, new UserCallback<User>() {
                @Override
                public void onSuccess(User user, String message) {
                    Log.d("NEDEN1","NEDEN");
                    Log.d("NEDEN2","NEDEN");
                    if (user.getRoles().iterator().next().getRole().equals("ADMIN")) {
                        // admin activity acilacak.
                        Log.d("EEEE","LALALAL");
                        finish();
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    } else if (user.getRoles().iterator().next().getRole().equals("CUSTOMER")) {
                        Log.d("EEEE1","LALALAL");
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else if (user.getRoles().iterator().next().getRole().equals("SELLER")) {
                        Log.d("EEEE2","LALALAL");
                        finish();
                        startActivity(new Intent(getApplicationContext(), SellerActivity.class));
                    }
                }

                @Override
                public void onError(Exception exception) {
                    Log.d("NEDEN","NEDEN");
                    if (exception.getMessage() != null && exception.getMessage().equals("BAN"))
                        buildDialog();
                    else {
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("zamazonsharedpref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();
                        Toast.makeText(getApplicationContext(), "Invalid email or password!", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        progressBarLogin.setVisibility(View.GONE);
    }

    public void buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder( this);

        builder.setTitle("Error");

        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editTextPassword.setText("");
                editTextUsername.setText("");
            }
        });
        builder.setMessage("You have banned!");
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public Boolean validateLoginData(String username, String password) {
        //validating inputs
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            editTextUsername.setError("Enter a valid email!");
            editTextUsername.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password!");
            editTextPassword.requestFocus();
            return false;
        }
        if (password.length() < 4) {
            editTextPassword.setError("At least 4 characters!");
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
