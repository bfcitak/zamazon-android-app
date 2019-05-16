package com.tantuni.zamazon.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class ChangePasswordActivity extends AppCompatActivity {

    EditText editTextOldPassword, editTextNewPassword, editTextNewRePassword;
    ProgressBar progressBarChangePassword;

    UserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        progressBarChangePassword = (ProgressBar) findViewById(R.id.progressBarChangePassword);

        editTextOldPassword = (EditText) findViewById(R.id.editTextOldPassword);
        editTextNewPassword = (EditText) findViewById(R.id.editTextNewPassword);
        editTextNewRePassword = (EditText) findViewById(R.id.editTextNewRePassword);

        //if user presses on change password button
        findViewById(R.id.buttonChangePassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first getting the values
                final String oldPassword = editTextOldPassword.getText().toString();
                final String newPassword = editTextNewPassword.getText().toString();
                final String newRePassword = editTextNewRePassword.getText().toString();
                changePassword(oldPassword, newPassword, newRePassword);
            }
        });
    }

    public void changePassword(String oldPassword, String newPassword, String newRePassword) {
        if (validateChangePasswordData(oldPassword, newPassword, newRePassword)) {
            // if everything is fine
            progressBarChangePassword.setVisibility(View.VISIBLE);

            final Map<String, String> passwordData = new HashMap<>();
            passwordData.put("oldPassword", oldPassword);
            passwordData.put("newPassword", newPassword);
            passwordData.put("newRePassword", newRePassword);

            userController.changePassword(getApplicationContext(), SharedPrefManager.getInstance(getApplicationContext()).getUser(), passwordData, new UserCallback<User>() {
                @Override
                public void onSuccess(User user, String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onError(Exception exception) {
                    Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                }
            });
            progressBarChangePassword.setVisibility(View.GONE);
        }
    }

    public Boolean validateChangePasswordData(String oldPassword, String newPassword, String newRePassword) {

        // first we will do the validations
        if (TextUtils.isEmpty(oldPassword)) {
            editTextOldPassword.setError("Enter your password!");
            editTextOldPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(newPassword)) {
            editTextNewPassword.setError("Enter your new password!");
            editTextNewPassword.requestFocus();
            return false;
        }

        if (newPassword.length() < 4) {
            editTextNewPassword.setError("At least 4 characters!");
            editTextNewPassword.requestFocus();
            return false;
        }

        if (!newPassword.equals(newRePassword)) {
            editTextNewRePassword.setError("Passwords do not match!");
            editTextNewRePassword.requestFocus();
            return false;
        }
        return true;
    }
}
