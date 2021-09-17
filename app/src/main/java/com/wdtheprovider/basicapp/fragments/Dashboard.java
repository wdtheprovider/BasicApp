package com.wdtheprovider.basicapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdtheprovider.basicapp.R;
import com.wdtheprovider.basicapp.jobAdapter;
import com.wdtheprovider.basicapp.models.DBHelper;
import com.wdtheprovider.basicapp.models.Jobs;

import java.util.ArrayList;

public class Dashboard extends Fragment {
    DBHelper dbHelper;

    jobAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Jobs> list = new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        recyclerView = view.findViewById(R.id.rv);

        adapter = new jobAdapter(list, getContext(), getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper();

        databaseReference = FirebaseDatabase.getInstance().getReference(Jobs.class.getSimpleName());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("courseFirebase", snapshot.getChildrenCount() + " key");
                int val = (int) snapshot.getChildrenCount()+1;
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Jobs model = dataSnapshot.getValue(Jobs.class);
                    assert model != null;
                    model.setKey(dataSnapshot.getKey());
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}