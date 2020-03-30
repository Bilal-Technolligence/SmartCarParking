package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CompleteBookingDetail extends AppCompatActivity {
    CardView letsPark;
    EditText carName,carNumber,parkingDuration;
    TextView parkingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_booking_detail);
        letsPark = findViewById(R.id.letspark);
        parkingName =findViewById(R.id.txtParkingName);
        carName =findViewById(R.id.txtCarName);

        carNumber =findViewById(R.id.txtCarNumber);
        parkingDuration =findViewById(R.id.txtParkingDuration);




        letsPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompleteBookingDetail.this,EndCurrentParking.class);
                startActivity(intent);
            }
        });
    }
}
