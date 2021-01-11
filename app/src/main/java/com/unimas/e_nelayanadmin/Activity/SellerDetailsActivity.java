package com.unimas.e_nelayanadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unimas.e_nelayanadmin.Model.Fisherman;
import com.unimas.e_nelayanadmin.Model.Seller;
import com.unimas.e_nelayanadmin.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerDetailsActivity extends AppCompatActivity {
    private TextView fishermanName, fishermanPhone, fishermanLicenseNumber, fishingArea, address, approvalStatus, source;
    private CircleImageView fishermanImage;
    private Button approveButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);

        fishermanLicenseNumber = findViewById(R.id.fishermanLicense);
        fishingArea = findViewById(R.id.fishingArea);
        address = findViewById(R.id.address);
        approvalStatus = findViewById(R.id.approval);
        fishermanName = findViewById(R.id.fishermanName);
        fishermanPhone = findViewById(R.id.fishermanPhone);
        fishermanImage = findViewById(R.id.fishermanImage);
        approveButton = findViewById(R.id.approveBtn);
        cancelButton = findViewById(R.id.cancelBtn);
        source = findViewById(R.id.sellerSouce);

        String fName = getIntent().getExtras().getString("sellerName");
        String fPhone = getIntent().getExtras().getString("phoneNumber");
        final String fId = getIntent().getExtras().getString("sellerId");
        String fImage = getIntent().getExtras().getString("sellerImage");
        String fLicense =getIntent().getExtras().getString("sellingReason");
        String fAddress = getIntent().getExtras().getString("sellerAddress");
        String fApproval = getIntent().getExtras().getString("approvalStatus");
        String fArea = getIntent().getExtras().getString("sellingArea");
        String fSource = getIntent().getExtras().getString("sellerSource");

        Glide.with(this).load(fImage).into(fishermanImage);

        fishermanName.setText(fName);
        fishermanPhone.setText(fPhone);
        approvalStatus.setText(fApproval);
        fishermanLicenseNumber.setText(fLicense);
        fishingArea.setText(fArea);
        address.setText(fAddress);
        source.setText(fSource);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerDetailsActivity.this, SellerList.class));
            }
        });

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference reference = database.getReference().child("Seller").child(fId);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Seller seller = dataSnapshot.getValue(Seller.class);
                        seller.setApprovalStatus(true);


                        reference.setValue(seller).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SellerDetailsActivity.this, "Request approved!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SellerDetailsActivity.this, SellerList.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SellerDetailsActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }


}