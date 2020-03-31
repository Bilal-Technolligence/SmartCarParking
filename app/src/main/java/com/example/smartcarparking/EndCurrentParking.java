package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    String Rent,parkingDuration;
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

        if (currentTime.equals(String.valueOf(parkTime)+1*60*1000)){
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    //    final String userId ="1234";

        databaseReference.child("Bookings").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String carname=dataSnapshot.child("CarName").getValue().toString();
                    String carnumber=dataSnapshot.child("CarNumber").getValue().toString();
                    carName.setText(carname);
                    carNumber.setText(carnumber);
                    parkingDuration =dataSnapshot.child("ParkingDuration").getValue().toString();
                   // Rent =dataSnapshot.child("Rent").getValue().toString();
//                    duration.setText(parkingDuration+" Hours");
//                    perHourRent.setText("Rs."+Rent+"/h");


                    parkingTime.setText(dataSnapshot.child("Time").getValue().toString());
                    parkingDate.setText(dataSnapshot.child("Date").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        endParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndCurrentParking.this,PaymentMethod.class);
                intent.putExtra( "duration",parkingDuration);
                intent.putExtra( "rent",Rent);


                startActivity(intent);
            }
        });
    }
}
