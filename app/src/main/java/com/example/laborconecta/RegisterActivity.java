package com.example.laborconecta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.laborconecta.dbhelpers.UserHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    protected UserHelper userHelper;
    protected boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userHelper = new UserHelper(getApplicationContext());

        Button registerButton = findViewById(R.id.register_btn);
        registerButton.setOnClickListener(this::doRegister);
    }

    protected void doRegister(View view) {
        if (isLoading) {
            return;
        }

        try {

            isLoading = true;
            TextInputEditText nameEditText = findViewById(R.id.name_edit_text);
            TextInputEditText emailEditText = findViewById(R.id.email_edit_text);
            TextInputEditText passwordEditText = findViewById(R.id.password_edit_text);

            Snackbar failed = Snackbar.make(view, "Preencha todos os campos", 5000);

            if (emailEditText.getText() == null || passwordEditText.getText() == null ||
                    nameEditText.getText() == null) {
                failed.show();
                return;
            }

            final String name = nameEditText.getText().toString();
            final String email = emailEditText.getText().toString();
            final String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                failed.show();
                return;
            }

            int uid = userHelper.exists(email, null);

            if (uid > 0) {
                Snackbar.make(view, "Este Usuário já existe", 5000).show();
                return;
            }

            boolean created = userHelper.doRegister(name, email, password);

            if (!created) {
                Snackbar.make(view, "Não foi possível criar usuário", 10000).show();
                return;
            }

            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } finally {
            isLoading = false;
        }
    }
}