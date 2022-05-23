package com.example.laborconecta;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.example.laborconecta.contracts.UserContract;
import com.example.laborconecta.dbhelpers.UserHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    protected UserHelper userHelper;
    protected boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // actionBar.setTitle(R.string.login);
            // remove action bar
            actionBar.hide();
        }

        userHelper = new UserHelper(getApplicationContext());

        TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);
        passwordEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                doLogin(textView);
                return true;
            }
            return false;
        });

        Button loginButton = findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(this::doLogin);

        Button registerButton = findViewById(R.id.registrarBtn);
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    protected void doLogin(View view) {
        if (isLoading) {
            return;
        }

        try {

            isLoading = true;
            TextInputEditText emailEditText = findViewById(R.id.email_edit_text);
            TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);

            Snackbar failed = Snackbar.make(view, "Preencha todos os campos", 5000);

            if (emailEditText.getText() == null || passwordEditText.getText() == null) {
                failed.show();
                return;
            }

            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                failed.show();
                return;
            }

            boolean exists = userHelper.doLogin(email, password);

            if (!exists) {
                Snackbar.make(view, "Usuário não encontrado", 5000).show();
                return;
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } finally {
            isLoading = false;
        }

    }

}