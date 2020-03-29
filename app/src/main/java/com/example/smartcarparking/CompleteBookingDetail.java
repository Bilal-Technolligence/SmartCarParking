package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CompleteBookingDetail extends AppCompatActivity {
    CardView letsPark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_booking_detail);
        letsPark = findViewById(R.id.letspark);

        letsPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteBookingDetail.this,EndCurrentParking.class);
                startActivity(intent);
            }
        });
    }
}
