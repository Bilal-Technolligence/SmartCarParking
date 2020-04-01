package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class PayAndRateUser extends AppCompatActivity {

    Button Submit;
    TextView totalRent,parkingName;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_rate_user);

        Submit = findViewById(R.id.btnSubmit);
        totalRent = findViewById(R.id.txtTotalRent);
        parkingName = findViewById(R.id.txtParkingName);
        rating = findViewById(R.id.txttotalRating);

        Intent intent = getIntent();
      String  Rent = intent.getStringExtra("rent");
      totalRent.setText(Rent);


    }
}
