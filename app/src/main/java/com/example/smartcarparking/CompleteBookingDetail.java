package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CompleteBookingDetail extends AppCompatActivity {
    CardView letsPark;
    EditText carName,carNumber,parkingDuration;
    TextView parkingName;
    String ParkingSlot,ParkingRent;
    ProgressDialog progressDialog;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String currentTime;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_booking_detail);
        letsPark = findViewById(R.id.letspark);
        parkingName =findViewById(R.id.txtParkingName);
        carName =findViewById(R.id.txtCarName);
        carNumber =findViewById(R.id.txtCarNumber);
        parkingDuration =findViewById(R.id.txtParkingDuration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Booking Submitting..... ");
        Intent i = getIntent();
        ParkingSlot = i.getStringExtra("parkingSlotId");






        letsPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String CarName =carName.getText().toString();
                String CarNumber = carNumber.getText().toString();
                String ParkingDuration = parkingDuration.getText().toString();
////                String ParkingName = parkingName.getText().toString();
//                String ParkingName ="Veeran Parking";
//                String ParkingPrice = "12";

//                dateFormat = new SimpleDateFormat("kk:mm");
//                currentTime = dateFormat.format(calendar.getTime());
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


                if (CarName == null || CarName.equals("")) {
                    carName.setError("Please Fill Name");
                }
                else if (CarNumber.equals("")) {
                    carNumber.setError("Please Fill Car Number");
                }
               else if (ParkingDuration == null || ParkingDuration.equals("")) {
                    parkingDuration.setError("Please Fill Parking Duration");
                }
                else{
                    progressDialog.show();

                     String currentUser =FirebaseAuth.getInstance().getCurrentUser().getUid();

                    //final String push = FirebaseDatabase.getInstance().getReference().child("booking").push().getKey();
                   // databaseReference.child("Bookings").child(currentUser).child("ParkingName").setValue(ParkingName);
                    databaseReference.child("Bookings").child(currentUser).child("userId").setValue(currentUser);
                    databaseReference.child("Bookings").child(currentUser).child("CarName").setValue(CarName);
                    databaseReference.child("Bookings").child(currentUser).child("CarNumber").setValue(CarNumber);
                   // databaseReference.child("Bookings").child(currentUser).child("Rent").setValue(ParkingPrice);
                    databaseReference.child("Bookings").child(currentUser).child("ParkingDuration").setValue(ParkingDuration);
                    databaseReference.child("Bookings").child(currentUser).child("Date").setValue(currentDate);
                    databaseReference.child("Bookings").child(currentUser).child("Time").setValue(currentTime);
                    databaseReference.child("Bookings").child(currentUser).child("ParkingSlotId").setValue(ParkingSlot);
                    progressDialog.dismiss();
                    databaseReference.child("Parkings").child(ParkingSlot).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                int availableSlots=Integer.parseInt(dataSnapshot.child("available").getValue().toString());
                                availableSlots=availableSlots-1;
                                databaseReference.child("Parkings").child(ParkingSlot).child("available").setValue(String.valueOf(availableSlots));
//
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Intent intent = new Intent(CompleteBookingDetail.this,EndCurrentParking.class);
                    startActivity(intent);
                }



            }
        });
    }
}
