package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndCurrentParking extends AppCompatActivity {
    Button endParking;
    TextView parkingName,carName,carNumber,duration,parkingTime,parkingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_current_parking);

        endParking =findViewById(R.id.btnEndParking);
        parkingName = findViewById(R.id.txtParkingName);

        carName = findViewById(R.id.txtCarName);
        carNumber = findViewById(R.id.txtCarNumber);
        duration = findViewById(R.id.txtDuration);
        parkingTime = findViewById(R.id.txtStartTime);
        parkingDate = findViewById(R.id.txtDate);


        endParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndCurrentParking.this,PaymentMethod.class);
                startActivity(intent);
            }
        });
    }
}
