package com.goats.chatbrewery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.modelclass.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;

public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.ViewHolder> {

    List<String> keys;
    Context context;

    public UpdatesAdapter(List<String> keys, Context context) {
        this.keys = keys;
        this.context = context;
    }

    @NonNull
    @Override
    public UpdatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_updates, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdatesAdapter.ViewHolder holder, int position) {
        FirebaseFirestore.getInstance().collection("Users").document(keys.get(position)).get().addOnSuccessListener(documentSnapshot -> {
            UserModel model = documentSnapshot.toObject(UserModel.class);
            holder.name.setText(model.getName());
            holder.phone.setText(model.getPhone());
            holder.email.setText(model.getEmail());
            holder.accept.setOnClickListener(v -> {
                HashMap<String, String> friend = new HashMap<>();
                friend.put(keys.get(position), keys.get(position));
                FirebaseFirestore.getInstance().collection("Friends").document("Users").collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(keys.get(position)).set(friend, SetOptions.merge()).addOnSuccessListener(aVoid -> Toast.makeText(context, "Friend Request Accepted", Toast.LENGTH_SHORT).show());
                friend.clear();
                friend.put(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getUid());
                FirebaseFirestore.getInstance().collection("Friends").document("Users").collection(keys.get(position)).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(friend, SetOptions.merge());
                friend.clear();
                FirebaseFirestore.getInstance().collection("Friend Requests").document("Sent").collection(keys.get(position)).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).delete();
                FirebaseFirestore.getInstance().collection("Friend Requests").document("Requested").collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(keys.get(position)).delete();
            });

            holder.reject.setOnClickListener(v -> {
                FirebaseFirestore.getInstance().collection("Friend Requests").document("Sent").collection(keys.get(position)).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).delete();
                FirebaseFirestore.getInstance().collection("Friend Requests").document("Requested").collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(keys.get(position)).delete().addOnSuccessListener(aVoid -> Toast.makeText(context, "Friend Request Rejected", Toast.LENGTH_SHORT).show());
            });
        });
    }

    @Override
    public int getItemCount() { return keys.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, phone, email;
        ImageButton accept, reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textview_rc_name);
            phone = itemView.findViewById(R.id.textview_rc_phone);
            email = itemView.findViewById(R.id.textview_rc_email);
            accept = itemView.findViewById(R.id.accept_friend_button);
            reject = itemView.findViewById(R.id.reject_friend_button);
        }
    }
}
