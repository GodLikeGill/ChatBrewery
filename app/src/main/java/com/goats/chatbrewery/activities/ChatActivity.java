package com.goats.chatbrewery.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.adapters.ChatsAdapter;
import com.goats.chatbrewery.modelclass.ChatModel;
import com.goats.chatbrewery.modelclass.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    String key;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        key = getIntent().getStringExtra("key");
        recyclerView = findViewById(R.id.rc_item_chats);
        TextView userNameOther = findViewById(R.id.user_name_chatting);
        ImageButton sendMessage = findViewById(R.id.chat_send_message);
        AppCompatEditText typedMessage = findViewById(R.id.chat_typed_message);

        FirebaseFirestore.getInstance().collection("Users").document(key).get().addOnSuccessListener(documentSnapshot -> userNameOther.setText(documentSnapshot.toObject(UserModel.class).getName()));

        sendMessage.setOnClickListener(v -> {
            String string = typedMessage.getText().toString();
            if (!string.isEmpty()) {
                Long time = System.currentTimeMillis() / 1000;

                ChatModel chatModel = new ChatModel(string, FirebaseAuth.getInstance().getCurrentUser().getUid(), time);
                String id = FirebaseFirestore.getInstance().collection("Chats").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(key).document().getId();
                FirebaseFirestore.getInstance().collection("Chats").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(key).document(id).set(chatModel);
                FirebaseFirestore.getInstance().collection("Chats").document(key).collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(id).set(chatModel);
                typedMessage.setText("");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore.getInstance().collection("Chats").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection(key).orderBy("time", Query.Direction.DESCENDING).addSnapshotListener((value, error) -> {
            if (!value.getDocuments().isEmpty()) {
                List<ChatModel> chatModel = new ArrayList<>();
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    ChatModel chatModel1 = snapshot.toObject(ChatModel.class);
                    chatModel.add(chatModel1);
                }

                ChatsAdapter adapter = new ChatsAdapter(FirebaseAuth.getInstance().getCurrentUser().getUid(), chatModel);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                manager.setReverseLayout(true);
                recyclerView.setLayoutManager(manager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(ChatActivity.this, "No Chat Available!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}