package com.goats.chatbrewery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.goats.chatbrewery.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView name = findViewById(R.id.full_name);
        TextView email = findViewById(R.id.email_address);
        TextView phone = findViewById(R.id.phone);
        TextView about = findViewById(R.id.about);

        AppCompatButton editProfileButton = findViewById(R.id.edit_profile_button);

        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(documentSnapshot -> {
            name.setText(documentSnapshot.getString("name"));
            email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            phone.setText(documentSnapshot.getString("phone"));
            about.setText(documentSnapshot.getString("about"));
        });
        editProfileButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), EditProfileActivity.class)));
    }
}