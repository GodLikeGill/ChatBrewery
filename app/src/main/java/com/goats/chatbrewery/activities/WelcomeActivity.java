package com.goats.chatbrewery.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.goats.chatbrewery.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        AppCompatButton signIn = findViewById(R.id.sign_in);
        AppCompatButton signUp = findViewById(R.id.sign_up);

        signIn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), LoginActivity.class)));
        signUp.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), RegisterActivity.class)));
    }
}