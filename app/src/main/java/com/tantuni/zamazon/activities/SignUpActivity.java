package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.tantuni.zamazon.networks.UserCallback;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextFirstName, editTextLastName;
    ProgressBar progressBarSignUp;
    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressBarSignUp = (ProgressBar) findViewById(R.id.progressBarSignUp);

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

    public void registerUser() {

        progressBarSignUp.setVisibility(View.VISIBLE);

        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();

        if (validateSignUpData(email, password, firstName, lastName)) {
            //if everything is fine
            Map<String, String> signUpData = new HashMap<>();
            signUpData.put("email", email);
            signUpData.put("password", password);
            signUpData.put("firstName", firstName);
            signUpData.put("lastName", lastName);

            userController.signUp(getApplicationContext(), signUpData, new UserCallback<User>() {
                @Override
                public void onSuccess(User user, String message) {
                    progressBarSignUp.setVisibility(View.GONE);
                    // starting the login activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

                @Override
                public void onError(Exception exception) {
                    progressBarSignUp.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public Boolean validateSignUpData(String email, String password, String firstName, String lastName) {
        // first we will do the validations
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email!");
            editTextEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password!");
            editTextPassword.requestFocus();
            return false;
        }

        if (password.length() < 4) {
            editTextPassword.setError("At least 4 characters!");
            editTextPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(firstName)) {
            editTextFirstName.setError("Enter your first name!");
            editTextFirstName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            editTextLastName.setError("Enter your last name!");
            editTextLastName.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentManager fm = getSupportFragmentManager();
        Log.d("GEL","GEEEL");
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

}
