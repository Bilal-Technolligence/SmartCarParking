package com.example.smartcarparking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends BaseActivity implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION = 0;
    LocationManager locationManager;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    private String provider;
    DrawerLayout drawerLayout;
    TextView name, email;
    ImageView imageView;
    String lati, loni;
    Double latitude = 0.0, longitude = 0.0;
    FloatingActionButton floatingActionButton;
    FusedLocationProviderClient mFusedLocationClient;
    String usr = null;
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            recreate();
            return;
        }
//        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        floatingActionButton = (FloatingActionButton) findViewById( R.id.floatButton );
        //opening map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById( R.id.fragmentMap );
        supportMapFragment.getMapAsync( MainActivity.this );
        floatingActionButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent( MainActivity.this, ViewParkingSlots.class );

                    startActivity( i );
                }
        } );
       // btnReserveParking = findViewById(R.id.btnReserve);

//        btnReserveParking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this,CompleteBookingDetail.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
       // imageView.setVisibility( View.GONE );
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient( this );
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener( this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location1) {
                        // Got last known location. In some rare situations this can be null.
                        if (location1 != null) {
                            // Logic to handle location object

                            longitude = location1.getLongitude();
                            latitude = location1.getLatitude();
                            lati = (String.valueOf( latitude ));
                            loni = (String.valueOf( longitude ));

                            //showing on map
                            LatLng latLng1 = new LatLng( latitude, longitude );
                            googleMap.addMarker( new MarkerOptions().position( latLng1 ).title( "Your location" ) );
                            googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( latLng1, 18 ), 4000, null );
                        } else {

                            Toast.makeText(getApplicationContext(), "Please allow location to this app", Toast.LENGTH_LONG).show();

                        }


                    }
                } );

    }
}
