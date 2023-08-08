package com.goats.chatbrewery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.EditText;

import com.goats.chatbrewery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        EditText name = findViewById(R.id.full_name1);
        EditText email = findViewById(R.id.email_address1);
        EditText phone = findViewById(R.id.phone1);
        EditText about = findViewById(R.id.about1);
        AppCompatButton saveButton = findViewById(R.id.save_profile_button);

        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnSuccessListener(documentSnapshot -> {
            name.setText(documentSnapshot.getString("name"));
            email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            phone.setText(documentSnapshot.getString("phone"));
            about.setText(documentSnapshot.getString("about"));
        });

        saveButton.setOnClickListener(v -> {
            String nameText = name.getText().toString();
            String emailText = email.getText().toString();
            String phoneText = phone.getText().toString();
            String aboutText = about.getText().toString();

            if(!nameText.isEmpty() || !emailText.isEmpty() || !phoneText.isEmpty() || !aboutText.isEmpty()){

                Map<String, String> userData = new HashMap<>();
                userData.put("name", nameText);
                userData.put("phone", phoneText);
                userData.put("about", aboutText);
                FirebaseAuth.getInstance().getCurrentUser().updateEmail(emailText);
                FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(userData).addOnSuccessListener(aVoid -> finish());
            }
        });
    }
}