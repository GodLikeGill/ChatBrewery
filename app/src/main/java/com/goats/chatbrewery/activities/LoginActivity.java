package com.goats.chatbrewery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.goats.chatbrewery.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText email = findViewById(R.id.editTextTextEmailAddress);
        EditText pass = findViewById(R.id.editTextTextPassword);
        AppCompatButton signIn = findViewById(R.id.sign_in1);

        signIn.setOnClickListener(v -> {

            String emailText = email.getText().toString();
            String passText = pass.getText().toString();

            if (!emailText.isEmpty() || !passText.isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passText).addOnSuccessListener(authResult -> {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }).addOnFailureListener(e -> Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}