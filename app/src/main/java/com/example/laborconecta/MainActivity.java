package com.example.laborconecta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laborconecta.fragments.MyServicesFragment;
import com.example.laborconecta.fragments.ProfileFragment;
import com.example.laborconecta.fragments.ServicesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_view);
        navigationView.setOnItemSelectedListener(this::onMenuItemSelected);

        openFirstFragment();
    }

    protected boolean onMenuItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_services: {
                openFirstFragment();
                break;
            }
            case R.id.nav_my_services: {
                Objects.requireNonNull(getSupportActionBar()).setTitle(item.getTitle());
                Fragment profile = MyServicesFragment.newInstance();
                openFragment(profile);
                break;
            }
            case R.id.nav_profile: {
                Objects.requireNonNull(getSupportActionBar()).setTitle(item.getTitle());
                Fragment profile = ProfileFragment.newInstance();
                openFragment(profile);
                break;
            }
        }
        return true;
    }

    private void openFirstFragment() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.nav_services);
        Fragment profile = ServicesFragment.newInstance();
        openFragment(profile);
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}