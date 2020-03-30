package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PaymentMethod extends AppCompatActivity {
    CardView payByHand;
    TextView totalParkingRent,totalDurationHours;
    String Rent,parkingDuration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        payByHand = findViewById(R.id.cardHandPayment);
        totalParkingRent = findViewById(R.id.txtTotalRent);

        totalDurationHours = findViewById(R.id.txtTotalDuration);
        Intent intent = getIntent();
        Rent = intent.getStringExtra("rent");
        parkingDuration = intent.getStringExtra("duration");


        payByHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethod.this,PayAndRateUser.class);
                startActivity(intent);
            }
        });
    }
}
