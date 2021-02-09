package com.arish1999.enrollapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class users_fragment extends Fragment {

    FirebaseDatabase database;
    RecyclerView recyclerView;
    UsersAdapter usersAdapter;
    ArrayList<UsersModel> usersModelArrayList;
    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.users_layout,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        usersModelArrayList = new ArrayList<>();
        database=FirebaseDatabase.getInstance();
        usersAdapter = new UsersAdapter(context,usersModelArrayList);
        recyclerView.setAdapter(usersAdapter);
        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                usersModelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    UsersModel usersModel = dataSnapshot.getValue(UsersModel.class);
                    usersModelArrayList.add(0,usersModel);
                    usersAdapter.notifyItemInserted(0);

                }
                usersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;

    }



}
