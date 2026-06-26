package com.example.termoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.termoapp.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = new Database(this);

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.regibtn.setOnClickListener(v -> {
            String user = binding.usernameInput.getText().toString();
            String pass = binding.passwordInput.getText().toString();
            String repass = binding.ripetiPasswordInput.getText().toString();

            if (user.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Per favore compila tutti i campi", Toast.LENGTH_SHORT).show();
            } else {
                if (pass.equals(repass)) {
                    Boolean checkUser = database.checkEmail(user);
                    if (!checkUser) {
                        Boolean insert = database.insertData(user, pass);
                        if (insert) {
                            Toast.makeText(SignupActivity.this, "Registrazione completata!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupActivity.this, "Registrazione fallita", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "L'utente esiste già! Accedi", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Le password non corrispondono", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
