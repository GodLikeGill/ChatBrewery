package com.goats.chatbrewery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goats.chatbrewery.R;
import com.goats.chatbrewery.adapters.UpdatesAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Updates extends Fragment {

    public Updates() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_updates, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_updates);

        FirebaseFirestore.getInstance().collection("Friend Requests").document("Requested").collection(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener((value, error) -> {
            if(error == null){
                List<String> keys = new ArrayList<>();
                for(DocumentSnapshot snapshot : value)
                    keys.add(snapshot.getId());

                try {
                    UpdatesAdapter adapter = new UpdatesAdapter(keys, getContext());
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                }catch (Exception ignored) {}
            }
        });
    }
}