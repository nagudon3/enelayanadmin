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
import com.unimas.e_nelayanadmin.R;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class FishermanDetailsActivity extends AppCompatActivity {
    private TextView fishermanName, fishermanPhone, fishermanLicenseNumber, fishingArea, address, years, approvalStatus;
    private CircleImageView fishermanImage;
    private Button approveButton, cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fisherman_details);

        fishermanLicenseNumber = findViewById(R.id.fishermanLicense);
        fishingArea = findViewById(R.id.fishingArea);
        address = findViewById(R.id.address);
        years = findViewById(R.id.years);
        approvalStatus = findViewById(R.id.approval);
        fishermanName = findViewById(R.id.fishermanName);
        fishermanPhone = findViewById(R.id.fishermanPhone);
        fishermanImage = findViewById(R.id.fishermanImage);
        approveButton = findViewById(R.id.approveBtn);
        cancelButton = findViewById(R.id.cancelBtn);

        String fName = getIntent().getExtras().getString("fishermanName");
        String fPhone = getIntent().getExtras().getString("phoneNumber");
        final String fId = getIntent().getExtras().getString("fishermanID");
        String fImage = getIntent().getExtras().getString("fishermanImage");
        String fLicense =getIntent().getExtras().getString("fishermanLicenseNumber");
        String fAddress = getIntent().getExtras().getString("address");
        String fApproval = getIntent().getExtras().getString("approvalStatus");
        String fYears = getIntent().getExtras().getString("years");
        String fArea = getIntent().getExtras().getString("fishingArea");

        Glide.with(this).load(fImage).into(fishermanImage);

        fishermanName.setText("Name: "+fName);
        fishermanPhone.setText("Phone Number: " +fPhone);
        approvalStatus.setText("Approval Status: " +fApproval);
        years.setText("Year(s) as fisherman: " +fYears);
        fishermanLicenseNumber.setText("Fisherman License Number: " +fLicense);
        fishingArea.setText("Fishing area: "+fArea);
        address.setText("Address: "+fAddress);


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FishermanDetailsActivity.this, FishermanList.class));
            }
        });

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference reference = database.getReference().child("Fisherman").child(fId);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Fisherman fisherman = dataSnapshot.getValue(Fisherman.class);
                        fisherman.setApprovalStatus(true);


                        reference.setValue(fisherman).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(FishermanDetailsActivity.this, "Request approved!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(FishermanDetailsActivity.this, FishermanList.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(FishermanDetailsActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
