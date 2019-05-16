package com.tantuni.zamazon.activities;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.tantuni.zamazon.R;
import com.tantuni.zamazon.fragments.ProfileFragment;
import com.tantuni.zamazon.fragments.SellerHomeFragment;
import com.tantuni.zamazon.fragments.SellerPendingFragment;

public class SellerActivity extends AppCompatActivity implements SellerHomeFragment.OnFragmentInteractionListener{

    ActionBar toolbar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);
        toolbar = getSupportActionBar();
        loadFragment(new SellerHomeFragment());

        BottomNavigationView bottomNavigationViewSeller = findViewById(R.id.navigationViewSeller);
        bottomNavigationViewSeller.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_seller_home:

                    toolbar.setTitle("Home");
                    fragment = new SellerHomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_seller_pending:

                    toolbar.setTitle("Pending Sales");
                    fragment = new SellerPendingFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_seller_profile:

                    toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_seller, fragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
