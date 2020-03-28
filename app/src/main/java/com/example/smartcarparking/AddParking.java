package com.example.smartcarparking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddParking extends AppCompatActivity {
    CardView btnRegister;
    ProgressDialog progressDialog;
    ImageView profileImage;
    private Uri imagePath;
    int count = 0;
    private LocationManager locationManager;
    String provider, lati, loni, addressString;
    Double latitude = 0.0, longitude = 0.0;
    FusedLocationProviderClient mFusedLocationClient;
    EditText length, width, price;
    final FirbaseAuthenticationClass firbaseAuthenticationClass = new FirbaseAuthenticationClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parking);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        length = (EditText) findViewById(R.id.length);
        width = (EditText) findViewById(R.id.width);
        price = (EditText) findViewById(R.id.price);
        btnRegister = (CardView) findViewById(R.id.register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adding parking..... ");
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location1) {
                        // Got last known location. In some rare situations this can be null.
                        if (location1 != null) {
                            // Logic to handle location object

                            longitude = location1.getLongitude();
                            latitude = location1.getLatitude();
                            lati = (String.valueOf(latitude));
                            loni = (String.valueOf(longitude));
                            //Toast.makeText(getApplicationContext() , lati + " " + loni ,  Toast.LENGTH_LONG).show();
                            Geocoder geoCoder = new Geocoder(AddParking.this, Locale.getDefault());
                            StringBuilder builder = new StringBuilder();
                            try {
                                List<Address> address = geoCoder.getFromLocation(latitude, longitude, 1);
                                int maxLines = address.get(0).getMaxAddressLineIndex();
                                for (int i = 0; i < maxLines; i++) {
                                    String addressStr = address.get(0).getAddressLine(i);
                                    builder.append(addressStr);
                                    builder.append(" ");
                                }
                                if (address.size() > 0) {
                                    System.out.println(address.get(0).getLocality());
                                    System.out.println(address.get(0).getCountryName());
                                    //Toast.makeText(getApplicationContext() , address.get(0).getAddressLine(0) , Toast.LENGTH_LONG).show();
                                }
                                String finalAddress = builder.toString(); //This is the complete address.
                                addressString = address.get(0).getAddressLine(0);
                            } catch (IOException e) {
                                // Handle IOException
                            } catch (NullPointerException e) {
                                // Handle NullPointerException
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enable your location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                recreate();
                return;
            }
        }
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Width = width.getText().toString().toUpperCase();
                String Length = length.getText().toString();
                String Price = price.getText().toString();
                if (Width.equals("")) {
                    width.setError("Enter Valid Name");
                    width.setFocusable(true);
                } else if (Length.equals("")) {
                    length.setError("Enter Parking Name");
                    length.setFocusable(true);
                } else if (Price.equals("")) {
                    price.setError("Enter Parking Space");
                    price.setFocusable(true);
                } else if (count == 0) {
                    Snackbar.make(v, "Please Select Image", Snackbar.LENGTH_LONG).show();
                } else {
                    // progressDialog.show();
                  //  firbaseAuthenticationClass.RegisterUser(userGmail, userPassword, Contact, Name, ParkingName, ParkingSpace, userCategory, imagePath, lati, loni, addressString, CompleteProfileActivity.this, progressDialog);

                }
            }
        });
    }

}

