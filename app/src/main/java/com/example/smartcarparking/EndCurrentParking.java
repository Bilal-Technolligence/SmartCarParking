package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndCurrentParking extends AppCompatActivity {
    Button endParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_current_parking);
        endParking =findViewById(R.id.btnEndParking);

        endParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndCurrentParking.this,PaymentMethod.class);
                startActivity(intent);
            }
        });
    }
}
