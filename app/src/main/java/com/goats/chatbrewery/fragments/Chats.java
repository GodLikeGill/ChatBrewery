package com.goats.chatbrewery.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.activities.AllUsers;
import com.goats.chatbrewery.activities.ChatActivity;
import com.goats.chatbrewery.activities.ProfileActivity;
import com.goats.chatbrewery.activities.WelcomeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class Chats extends Fragment {

    public Chats() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout testLayout = view.findViewById(R.id.test_relativelayout);
        testLayout.setOnClickListener(v -> startActivity(new Intent(getContext(), ChatActivity.class)));

        ImageButton morePopDown = view.findViewById(R.id.button_more);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingactionbutton);

        floatingActionButton.setOnClickListener(v -> startActivity(new Intent(getContext(), AllUsers.class)));

        morePopDown.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(), morePopDown);
            popupMenu.getMenuInflater().inflate(R.menu.more_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.profile:
                        openProfile();
                        break;
                    case R.id.sign_out:
                        signOut();
                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    private void openProfile(){
        startActivity(new Intent(getContext(),ProfileActivity.class));
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), WelcomeActivity.class));
        getActivity().finish();
    }
}