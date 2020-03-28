package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceMain extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    ArrayList<ParkAttr> parkAttrs;
    String User;
    RecyclerView recyclerView;
    FloatingActionButton btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);
        recyclerView=findViewById(R.id.ffList);
        getSupportActionBar().setTitle("Parkings");
        parkAttrs = new ArrayList<ParkAttr>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ServiceMain.this , AddParking.class);
                startActivity(i);
            }
        });
        databaseReference.child("Parkings").orderByChild("admin").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parkAttrs.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ParkAttr p = dataSnapshot1.getValue(ParkAttr.class);
                    parkAttrs.add(p);
                }

                recyclerView.setAdapter(new ParkingAdapter(parkAttrs , getApplicationContext() , ServiceMain.this));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
