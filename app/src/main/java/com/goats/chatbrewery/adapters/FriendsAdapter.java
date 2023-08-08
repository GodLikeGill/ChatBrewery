package com.goats.chatbrewery.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.activities.ChatActivity;
import com.goats.chatbrewery.modelclass.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {

    List<String> keys;

    public FriendsAdapter(List<String> keys) {
        this.keys = keys;
    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FirebaseFirestore.getInstance().collection("Users").document(keys.get(position)).get().addOnSuccessListener(documentSnapshot -> {
            UserModel model = documentSnapshot.toObject(UserModel.class);
            holder.name.setText(model.getName());
            holder.phone.setText(model.getPhone());
            holder.email.setText(model.getEmail());
        });

        holder.message.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
            intent.putExtra("key", keys.get(position));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return keys.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView phone;
        TextView email;
        ImageButton message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textview_rc_name);
            phone = itemView.findViewById(R.id.textview_rc_phone);
            email = itemView.findViewById(R.id.textview_rc_email);
            message = itemView.findViewById(R.id.send_friend_message_button);
        }
    }
}
