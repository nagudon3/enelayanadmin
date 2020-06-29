package com.unimas.e_nelayanadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unimas.e_nelayanadmin.Adapter.FishermanAdapter;
import com.unimas.e_nelayanadmin.Adapter.SellerAdapter;
import com.unimas.e_nelayanadmin.Model.Fisherman;
import com.unimas.e_nelayanadmin.Model.Seller;
import com.unimas.e_nelayanadmin.R;

import java.util.ArrayList;

public class SellerList extends AppCompatActivity {
    private RecyclerView recyclerView;
    SellerAdapter sellerAdapter;
    ArrayList<Seller> sellerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_list);


        FirebaseDatabase database = FirebaseDatabase.getInstance();

        recyclerView = findViewById(R.id.sellerRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sellerArrayList= new ArrayList<>();

        DatabaseReference fishermanDb = database.getReference().child("Seller");
        fishermanDb.orderByChild("approvalStatus").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Seller seller = dataSnapshot1.getValue(Seller.class);
                    sellerArrayList.add(seller);
                }
                sellerAdapter = new SellerAdapter(SellerList.this, sellerArrayList);
                recyclerView.setAdapter(sellerAdapter);
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
        startActivity(new Intent(SellerList.this, HomeActivity.class));
    }
}
