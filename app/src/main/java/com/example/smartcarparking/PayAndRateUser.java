package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PayAndRateUser extends AppCompatActivity {

    Button Submit,rate;
    TextView totalRent,parkingName;
   // RatingBar rating;
    String adminId;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_rate_user);

        Submit = findViewById(R.id.btnSubmit);
        rate = findViewById(R.id.btnRate);
        totalRent = findViewById(R.id.txtTotalRent);
        parkingName = findViewById(R.id.txtParkingName);
        //rating = findViewById(R.id.txttotalRating);

        Intent intent = getIntent();
      String  Rent = intent.getStringExtra("rent");
     final String ParkingSlot = intent.getStringExtra("parkingId");
      totalRent.setText(Rent);
        final String currentUser =FirebaseAuth.getInstance().getCurrentUser().getUid();
      rate.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ShowDialog();
          }
      });
      Submit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                              databaseReference.child("Parkings").child(ParkingSlot).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            int availableSlots=Integer.parseInt(dataSnapshot.child("available").getValue().toString());
                            availableSlots=availableSlots+1;
                            databaseReference.child("Parkings").child(ParkingSlot).child("available").setValue(String.valueOf(availableSlots));
                            databaseReference.child("Bookings").child(currentUser).removeValue();
                            Toast.makeText(getApplicationContext(), "Thanks for coming. See you soon!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
          }
      });



        databaseReference.child("Parkings").child(ParkingSlot).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    parkingName.setText(dataSnapshot.child("name").getValue().toString());
                    adminId=dataSnapshot.child("admin").getValue().toString();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void ShowDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.ratecomment, null);
        final RatingBar ratingBar = (RatingBar) mView.findViewById(R.id.ratingBar);
        final EditText comment = (EditText) mView.findViewById(R.id.edtComment);
        builder.setView(mView);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final String push = FirebaseDatabase.getInstance().getReference().child("Rating").push().getKey();
                        Rating_Attr rating_attr = new Rating_Attr();
                        rating_attr.setId(push);
                        rating_attr.setComment(comment.getText().toString());
                        rating_attr.setTotal(Float.valueOf(ratingBar.getRating()));
                        rating_attr.setUserId(currentUser);

                        databaseReference.child("Rating").child(adminId).child(currentUser)
                                .setValue(rating_attr);

                        dialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }

                });

        builder.create();
        builder.show();
    }
}
