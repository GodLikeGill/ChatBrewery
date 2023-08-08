package com.goats.chatbrewery.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.adapters.UserAdapter;
import com.goats.chatbrewery.modelclass.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        FirebaseFirestore.getInstance().collection("Users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<String> key = new ArrayList<>();
                List<UserModel> data = new ArrayList<>();
                for (QueryDocumentSnapshot snapshot1 : task.getResult()) {
                    UserModel model = snapshot1.toObject(UserModel.class);
                    String s = snapshot1.getId();
                    if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(s)) {
                        data.add(model);
                        key.add(s);
                    }
                }

                UserAdapter adapter = new UserAdapter(data, key, getApplicationContext());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);

            } else if (task.isCanceled()) {
                Toast.makeText(AllUsers.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}