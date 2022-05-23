package com.example.laborconecta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.laborconecta.contracts.SessionContract;
import com.example.laborconecta.contracts.UserContract;
import com.example.laborconecta.dbhelpers.SessionHelper;
import com.example.laborconecta.repositories.UserRepository;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Objects.requireNonNull(getSupportActionBar()).hide();

        SessionHelper session = new SessionHelper(getBaseContext());
        UserContract.UserModel user = session.getSession();

        UserRepository userRepository = UserRepository.getInstance(getBaseContext());
        // seta o user no repository (mesmo sendo null)
        userRepository.setUser(user);

        Intent intent = userRepository.isLogged()
                ? new Intent(SplashActivity.this, MainActivity.class)
                : new Intent(SplashActivity.this, LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}