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

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserModel> data;
    private List<String> key;
    private Context context;

    public UserAdapter(List<UserModel> data, List<String> key, Context context) {
        this.data = data;
        this.key = key;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getName());
        holder.phone.setText(data.get(position).getPhone());
        holder.email.setText(data.get(position).getEmail());
        holder.add.setOnClickListener(v -> {
            HashMap<String, Object> time = new HashMap<>();
            time.put("Time", Calendar.getInstance().getTime());
            FirebaseFirestore.getInstance().collection("Friend Requests").document("Sent").collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).document(key.get(position)).set(time).addOnSuccessListener(aVoid -> Toast.makeText(context, "Friend Request Sent", Toast.LENGTH_SHORT).show());
            FirebaseFirestore.getInstance().collection("Friend Requests").document("Requested").collection(key.get(position)).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(time);
        });
    }

    @Override
    public int getItemCount() {
        return key.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phone;
        TextView email;
        ImageButton add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textview_rc_name);
            phone = itemView.findViewById(R.id.textview_rc_phone);
            email = itemView.findViewById(R.id.textview_rc_email);
            add = itemView.findViewById(R.id.add_friend_button);
        }
    }
}
