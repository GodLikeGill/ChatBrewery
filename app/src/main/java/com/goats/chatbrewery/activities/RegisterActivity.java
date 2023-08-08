package com.goats.chatbrewery.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.goats.chatbrewery.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    String sex, nameText, phoneText, emailText, passText, dobText;
    AppCompatButton male, female, signUp;
    EditText name, phone, email, pass, dob;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.editTextName);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.editTextTextEmailAddress1);
        pass = findViewById(R.id.editTextTextPassword1);
        dob = findViewById(R.id.editTextDOB);
        signUp = findViewById(R.id.sign_up1);

        DatePickerDialog.OnDateSetListener date = (view, year, month, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };

        signUp.setOnClickListener(v -> {

            nameText = name.getText().toString();
            phoneText = phone.getText().toString();
            emailText = email.getText().toString();
            passText = pass.getText().toString();
            dobText = dob.getText().toString();

            if (!nameText.isEmpty() || !phoneText.isEmpty() || !emailText.isEmpty() || !passText.isEmpty() || !sex.isEmpty() || !dobText.isEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText, passText).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Map<String, String> userData = new HashMap<>();
                        userData.put("name", nameText);
                        userData.put("phone", phoneText);
                        userData.put("email", emailText);
                        userData.put("dob", dobText);
                        userData.put("sex", sex);

                        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(userData).addOnSuccessListener(aVoid -> {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        });
                    } else {
                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        dob.setOnClickListener(v -> {
            new DatePickerDialog(RegisterActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void updateLabel() {
        String myFormat = "dd/mm/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        dob.setText(sdf.format(myCalendar.getTime()));
        dobText = sdf.format(myCalendar.getTime());
    }

    public void selectGender(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_male:
                sex = "Male";
                male.setEnabled(false);
                female.setEnabled(true);
                break;
            case R.id.button_female:
                sex = "Female";
                male.setEnabled(true);
                female.setEnabled(false);
                break;
        }
    }
}