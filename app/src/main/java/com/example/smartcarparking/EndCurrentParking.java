package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EndCurrentParking extends AppCompatActivity {
    Button endParking,cancelParking;
    String Rent,parkingDuration,ParkingTime;
    TextView parkingName,carName,carNumber,duration,parkingTime,parkingDate,perHourRent;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_current_parking);

        endParking =findViewById(R.id.btnEndParking);
        cancelParking =findViewById(R.id.btnEndParking);

        parkingName = findViewById(R.id.txtParkingName);

        carName = findViewById(R.id.txtCarName);
        carNumber = findViewById(R.id.txtCarNumber);
        duration = findViewById(R.id.txtDuration);
        perHourRent = findViewById(R.id.txtperHourRent);
        parkingTime = findViewById(R.id.txtStartTime);
        parkingDate = findViewById(R.id.txtDate);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String  parkTime ="9:02:10";




        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //    final String userId ="1234";

        databaseReference.child("Bookings").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    parkingName.setText(dataSnapshot.child("ParkingName").getValue().toString());
                    carName.setText(dataSnapshot.child("CarName").getValue().toString());
                    carNumber.setText(dataSnapshot.child("CarNumber").getValue().toString());
                    parkingDuration =dataSnapshot.child("ParkingDuration").getValue().toString();
                    Rent =dataSnapshot.child("Rent").getValue().toString();
                    duration.setText(parkingDuration+" Hours");
                    perHourRent.setText("Rs."+Rent+"/h");
                    ParkingTime =dataSnapshot.child("Time").getValue().toString();
                    parkingTime.setText(dataSnapshot.child("Time").getValue().toString());
                    parkingDate.setText(dataSnapshot.child("Date").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        long diff =Integer.valueOf(currentTime)-Integer.valueOf(ParkingTime);
//        long diffSeconds = diff / 1000;
//        Toast.makeText(this, "Time "+diffSeconds, Toast.LENGTH_SHORT).show();

        if(currentTime>parkTime*1000)
        {
            cancelParking.setVisibility(View.INVISIBLE);
            endParking.setVisibility(View.VISIBLE);
        }
        else {
            cancelParking.setVisibility(View.VISIBLE);
            endParking.setVisibility(View.INVISIBLE);

        }
        endParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndCurrentParking.this,PaymentMethod.class);
                intent.putExtra( "duration",parkingDuration);
                intent.putExtra( "rent",Rent);


                startActivity(intent);
            }
        });

        cancelParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
                alertDialogBuilder.setMessage("Are you sure to remove parking?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Bookings").child(userId).setValue(null);
                        dialog.dismiss();
                        Intent intent = new Intent(EndCurrentParking.this,MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }
}
