package com.goats.chatbrewery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.modelclass.ChatModel;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    String uid;
    List<ChatModel> chatModels;

    public ChatsAdapter(String uid, List<ChatModel> chatModels) {
        this.uid = uid;
        this.chatModels = chatModels;
    }

    @NonNull
    @Override
    public ChatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsAdapter.ViewHolder holder, int position) {
        if (chatModels.get(position).getUid().equals(uid)) {
            holder.user_other.setVisibility(View.GONE);
            holder.user_me.setVisibility(View.VISIBLE);
            holder.user_me.setText(chatModels.get(position).getMsg());
        } else {
            holder.user_other.setVisibility(View.VISIBLE);
            holder.user_me.setVisibility(View.GONE);
            holder.user_other.setText(chatModels.get(position).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return chatModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_me;
        TextView user_other;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_me = itemView.findViewById(R.id.chat_user_me);
            user_other = itemView.findViewById(R.id.chat_user_other);
        }
    }
}