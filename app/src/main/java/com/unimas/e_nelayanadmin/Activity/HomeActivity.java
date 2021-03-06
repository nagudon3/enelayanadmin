package com.unimas.e_nelayanadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.unimas.e_nelayanadmin.R;

public class HomeActivity extends AppCompatActivity {
    private Button button, fisherman, seller;
    private Toast backToast;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = (Button) findViewById(R.id.logoutBtn);
        fisherman = (Button) findViewById(R.id.fishermanButton);
        seller = (Button) findViewById(R.id.sellerButton);

        fisherman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FishermanList.class));
            }
        });

        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SellerList.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return;
        }else {
            backToast = Toast.makeText(this, "Tap on back button again to exit.", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}