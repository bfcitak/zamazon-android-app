package com.tantuni.zamazon.activities;

import android.net.Uri;
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
import com.tantuni.zamazon.fragments.AdminHomeFragment;
import com.tantuni.zamazon.fragments.ManipulateItemsFragment;
import com.tantuni.zamazon.fragments.ProfileFragment;

public class AdminActivity extends AppCompatActivity implements AdminHomeFragment.OnFragmentInteractionListener {

    ActionBar toolbar;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("NOLUYO","NOLUYO");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = getSupportActionBar();
        loadFragment(new AdminHomeFragment());

        BottomNavigationView navigation = findViewById(R.id.navigationViewAdmin);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_users:

                    toolbar.setTitle("Users");
                    fragment = new AdminHomeFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_items:

                    toolbar.setTitle("Items");
                    fragment = new ManipulateItemsFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_admin_profile:

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
        transaction.replace(R.id.containerAdmin, fragment);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    public void onBackPressed(){
        finish();
    }
}
