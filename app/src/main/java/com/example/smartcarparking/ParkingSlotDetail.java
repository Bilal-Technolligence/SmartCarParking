package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ParkingSlotDetail extends AppCompatActivity {

    TextView service, company, location, contact, end, start, txtNum, txtRating;
    ImageView imgService;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    String longitude, latitude, phone;
    ImageButton callBtn, directionBtn, reviewBtn;
    RatingBar ratingBar;
    RecyclerView recyclerView;
    ArrayList<Rating_Attr> rating_attrs;
    String  adminId;
    Button btnReserve;
    //FloatingActionButton editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_slot_detail);
        service = (TextView) findViewById(R.id.txtService);
        company = (TextView) findViewById(R.id.txtCompanyName);
        btnReserve=(Button) findViewById(R.id.reserve);

       // editButton = (FloatingActionButton) findViewById(R.id.edit);
       // editButton.setVisibility(View.GONE);
        location = (TextView) findViewById(R.id.txtLocation);
       // contact = (TextView) findViewById(R.id.txtCall);
        //end = (TextView) findViewById(R.id.txtClose);
        start = (TextView) findViewById(R.id.txtOpen);
        imgService = (ImageView) findViewById(R.id.imgService);
        //callBtn = (ImageButton) findViewById(R.id.btnCall);
        directionBtn = (ImageButton) findViewById(R.id.btnDirection);
        reviewBtn = (ImageButton) findViewById(R.id.btnReview);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRating = (TextView) findViewById(R.id.txtRating);
        txtNum = (TextView) findViewById(R.id.txtTotalRating);
        recyclerView = (RecyclerView) findViewById(R.id.reviewList);
        rating_attrs = new ArrayList<Rating_Attr>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String parkingSlotId = getIntent().getStringExtra("Id");
        adminId = getIntent().getStringExtra("adminId");
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( ParkingSlotDetail.this, CompleteBookingDetail.class );
                i.putExtra( "parkingSlotId",parkingSlotId);
                startActivity( i );
            }
        });
        databaseReference.child("Users").child(adminId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    company.setText(dataSnapshot.child("name").getValue().toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Parkings").child(parkingSlotId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ParkAttr parkAttr = dataSnapshot.getValue( ParkAttr.class );
                    if (parkAttr != null) {
                        service.setText(parkAttr.getTitle());
                       // company.setText(addService.getCompanyName());
                        location.setText(parkAttr.getAddress());
                       // contact.setText(addService.getPhone());
                       // phone = addService.getPhone();
                       // end.setText(addService.getCloseTime());
                        start.setText(parkAttr.getPrice());
                        Picasso.get().load(parkAttr.getPic()).into(imgService);
                        longitude = parkAttr.getLongitude();
                        latitude = parkAttr.getLatitude();
                       // adminId = parkAttr.getAdmin();


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Rating").child(adminId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    long count = dataSnapshot.getChildrenCount();
                    Double total = 0.0;
                    for (DataSnapshot ratingsnapshot : dataSnapshot.getChildren()) {
                        Rating_Attr rating_attr = ratingsnapshot.getValue(Rating_Attr.class);
                        total += rating_attr.getTotal();
                    }
                    String star = String.valueOf(new DecimalFormat("#.#").format(total / count));
                    String str = String.valueOf(total / count);
                    ratingBar.setRating(Float.parseFloat(str));
                    //ratingBar.setNumStars((int) (total/count));
                    txtRating.setText(star);
                    txtNum.setText(String.valueOf(count));
//                if (total != 0.0) {
//                    databaseReference.child("Services").child(check).child("rating").setValue(Float.valueOf(star));
//                    databaseReference.child("Services").child(check).child("total").setValue(Integer.valueOf((int) count));
//                }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        directionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ParkingSlotDetail.this, DirectionOnMap.class);
                intent.putExtra("Latitude", latitude);
                intent.putExtra("Longitude", longitude);
                startActivity(intent);
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();

            }
        });
        databaseReference.child("Rating").child(adminId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rating_attrs.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Rating_Attr p = dataSnapshot1.getValue(Rating_Attr.class);
                    rating_attrs.add(p);
                }

                recyclerView.setAdapter(new ReviewListAdapter(rating_attrs, getApplicationContext()));


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

                        String currentUser =FirebaseAuth.getInstance().getCurrentUser().getUid();
                        final String push = FirebaseDatabase.getInstance().getReference().child("Rating").push().getKey();
                        Rating_Attr rating_attr = new Rating_Attr();
                        rating_attr.setId(push);
                        rating_attr.setComment(comment.getText().toString());
                        rating_attr.setTotal(Float.valueOf(ratingBar.getRating()));
                        rating_attr.setUserId(currentUser);

                        databaseReference.child("Rating").child(adminId).child(currentUser)
                                .setValue(rating_attr);

                        dialog.dismiss();
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
