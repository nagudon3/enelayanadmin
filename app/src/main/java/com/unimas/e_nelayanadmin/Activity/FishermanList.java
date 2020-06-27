package com.unimas.e_nelayanadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unimas.e_nelayanadmin.Adapter.FishermanAdapter;
import com.unimas.e_nelayanadmin.Model.Fisherman;
import com.unimas.e_nelayanadmin.R;

import java.util.ArrayList;

public class FishermanList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    FishermanAdapter fishermanAdapter;
    ArrayList<Fisherman> fishermanArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisherman_list);
        progressBar = findViewById(R.id.progressBar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.fishermanRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fishermanArrayList = new ArrayList<>();

        DatabaseReference fishermanDb = database.getReference().child("Fisherman");
        fishermanDb.orderByChild("approvalStatus").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    progressBar.setVisibility(View.INVISIBLE);
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        Fisherman fisherman = dataSnapshot1.getValue(Fisherman.class);
                        fishermanArrayList.add(fisherman);
                    }
                    fishermanAdapter = new FishermanAdapter(FishermanList.this, fishermanArrayList);
                    recyclerView.setAdapter(fishermanAdapter);
                }else {
                    Toast.makeText(FishermanList.this, "No request for the moment.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Nothing here!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
