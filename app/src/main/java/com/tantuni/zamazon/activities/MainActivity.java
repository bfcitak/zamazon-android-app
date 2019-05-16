package com.tantuni.zamazon.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.fragments.CartFragment;
import com.tantuni.zamazon.fragments.CategoriesFragment;
import com.tantuni.zamazon.fragments.HomeFragment;
import com.tantuni.zamazon.fragments.LoginOrSignUpFragment;
import com.tantuni.zamazon.fragments.ProfileFragment;
import com.tantuni.zamazon.fragments.WishListFragment;
import com.tantuni.zamazon.networks.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private ActionBar toolbar;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
            if (SharedPrefManager.getInstance(getApplicationContext()).getUser().getRoles().iterator().next().getRole().equals("ADMIN")) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                finish();
                startActivity(intent);
            } else if (SharedPrefManager.getInstance(getApplicationContext()).getUser().getRoles().iterator().next().getRole().equals("SELLER")) {
                Intent intent = new Intent(getApplicationContext(), SellerActivity.class);
                finish();
                startActivity(intent);
            }
        }
        setContentView(R.layout.activity_main);
        Log.d("MAIN","HADIII");

        toolbar = getSupportActionBar();
        loadFragment(new HomeFragment());


        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    toolbar.setTitle("Home");
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_categories:

                    toolbar.setTitle("Categories");
                    fragment = new CategoriesFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_wishlist:

                    toolbar.setTitle("Wish List");
                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                            fragment = new WishListFragment();
                            loadFragment(fragment);
                    } else {
                        fragment = new LoginOrSignUpFragment();
                        loadFragment(fragment);
                    }
                    return true;

                case R.id.navigation_cart:

                    toolbar.setTitle("Shopping Cart");
                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                        fragment = new CartFragment();
                        loadFragment(fragment);
                    } else {
                        fragment = new LoginOrSignUpFragment();
                        loadFragment(fragment);
                    }
                    return true;

                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    if (SharedPrefManager.getInstance(getApplicationContext()).isLoggedIn()) {
                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                    } else {
                        fragment = new LoginOrSignUpFragment();
                        loadFragment(fragment);
                    }
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void onBackPressed(){
        finish();
    }

}
