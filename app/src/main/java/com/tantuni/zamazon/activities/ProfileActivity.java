package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.models.SharedPrefManager;
import com.tantuni.zamazon.models.User;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewId, textViewEmail, textViewFirstName, textViewLastName, textViewIsActive, textViewRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
        textViewLastName = (TextView) findViewById(R.id.textViewLastName);
        textViewIsActive = (TextView) findViewById(R.id.textViewIsActive);
        textViewRoles = (TextView) findViewById(R.id.textViewRoles);

        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewEmail.setText(user.getEmail());
        textViewFirstName.setText(user.getFirstName());
        textViewLastName.setText(user.getLastName());
        textViewIsActive.setText(user.getActive().toString());
        textViewRoles.setText("ROLE");

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }

}
