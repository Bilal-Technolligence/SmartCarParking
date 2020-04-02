package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PayAndRateUser extends AppCompatActivity {

    Button Submit;
    TextView totalRent,parkingName;
    RatingBar rating;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_rate_user);

        Submit = findViewById(R.id.btnSubmit);
        totalRent = findViewById(R.id.txtTotalRent);
        parkingName = findViewById(R.id.txtParkingName);
        rating = findViewById(R.id.txttotalRating);

        Intent intent = getIntent();
      String  Rent = intent.getStringExtra("rent");
     String ParkingSlot = intent.getStringExtra("parkingId");
      totalRent.setText(Rent);

        databaseReference.child("Parkings").child(ParkingSlot).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    parkingName.setText(dataSnapshot.child("name").getValue().toString());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
