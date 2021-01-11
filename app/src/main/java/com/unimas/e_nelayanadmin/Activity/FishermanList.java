package com.unimas.e_nelayanadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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
    private TextView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisherman_list);
        progressBar = findViewById(R.id.progressBar);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        empty = findViewById(R.id.empty);
        recyclerView = findViewById(R.id.fishermanRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fishermanArrayList = new ArrayList<>();

        DatabaseReference fishermanDb = database.getReference().child("Fisherman");
        fishermanDb.orderByChild("approvalStatus").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    progressBar.setVisibility(View.INVISIBLE);
                    empty.setVisibility(View.GONE);
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        Fisherman fisherman = dataSnapshot1.getValue(Fisherman.class);
                        fishermanArrayList.add(fisherman);
                    }
                    fishermanAdapter = new FishermanAdapter(FishermanList.this, fishermanArrayList);
                    recyclerView.setAdapter(fishermanAdapter);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Nothing here!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FishermanList.this, MainActivity.class));
    }
}
