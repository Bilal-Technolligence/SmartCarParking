package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PaymentMethod extends AppCompatActivity {
    CardView payByHand;
    TextView totalParkingRent,totalDurationHours;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    int totalAmount;
    String Rent,parkingDuration,parkingSlot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        payByHand = findViewById(R.id.cardHandPayment);
        totalParkingRent = findViewById(R.id.txtTotalRent);

        totalDurationHours = findViewById(R.id.txtTotalDuration);
        Intent intent = getIntent();
       Rent = intent.getStringExtra("rent");
        parkingSlot = intent.getStringExtra("parkingId");

        parkingDuration = intent.getStringExtra("duration");
         totalAmount = Integer.valueOf( Rent) * Integer.valueOf(parkingDuration);
        totalParkingRent.setText(String.valueOf(totalAmount));
        totalDurationHours.setText(parkingDuration+" h");
        payByHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentMethod.this,PayAndRateUser.class);
                intent.putExtra( "total",totalAmount);
                intent.putExtra( "parkingId",parkingSlot);


                startActivity(intent);
            }
        });
    }
}
