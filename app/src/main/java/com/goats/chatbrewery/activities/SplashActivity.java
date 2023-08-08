package com.goats.chatbrewery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.goats.chatbrewery.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            if(FirebaseAuth.getInstance().getCurrentUser() == null){
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
            }else{
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            finish();
        }, 2000);
    }
}