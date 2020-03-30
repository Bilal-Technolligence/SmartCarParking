package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewParkingSlots extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    RecyclerView recyclerView;
    ArrayList<ParkAttr> parkAttrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking_slots);
        recyclerView = (RecyclerView) findViewById( R.id.recyclerView );
        parkAttrs = new ArrayList<ParkAttr>();
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        databaseReference.child( "Services" ).orderByChild( "service" ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parkAttrs.clear();
                //profiledata.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ParkAttr p = dataSnapshot1.getValue( ParkAttr.class );
                    parkAttrs.add( p );
                }
                    recyclerView.setAdapter( new ViewParkingSlotsAdapter( parkAttrs, ViewParkingSlots.this ) );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }
}
