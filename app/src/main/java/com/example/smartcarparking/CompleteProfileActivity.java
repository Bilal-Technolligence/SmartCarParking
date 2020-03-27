package com.example.smartcarparking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Locale;

public class CompleteProfileActivity extends AppCompatActivity {
CardView btnRegister;
    ProgressDialog progressDialog;
    ImageView profileImage;
    private Uri imagePath;
    int count=0;
    private String selection ="User";
    String userName,userGmail,userCategory,userPassword;

    EditText name,contact,age,address,parkingSpace,parkingName;
    final FirbaseAuthenticationClass firbaseAuthenticationClass=new FirbaseAuthenticationClass();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        userCategory=getIntent().getStringExtra("Category");
//        if(userCategory.equals("Service"))
//        {
//            parkingSpace.setVisibility( View.VISIBLE );
//            parkingName.setVisibility(View.VISIBLE);
//            age.setVisibility( View.INVISIBLE );
//
//        }
//        if(userCategory.equals("User")) {
//            parkingSpace.setVisibility(View.INVISIBLE);
//            parkingName.setVisibility(View.INVISIBLE);
//            age.setVisibility(View.VISIBLE);
//
//        }
         userName=getIntent().getStringExtra("Name");
        profileImage = (ImageView) findViewById(R.id.profileImage);
        name=(EditText) findViewById(R.id.name);
        //Setting UserName
        name.setText(String.valueOf(userName));
        contact=(EditText) findViewById(R.id.contact);
        age=(EditText) findViewById(R.id.age);
        address=(EditText) findViewById(R.id.address);
        parkingName=(EditText) findViewById(R.id.parkingName);
        parkingSpace=(EditText) findViewById(R.id.totalParkingSpace);
        btnRegister=(CardView) findViewById(R.id.register);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering..... ");
        userGmail=getIntent().getStringExtra("Email");
        userPassword=getIntent().getStringExtra("Password");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().toUpperCase();
               // String Age = age.getText().toString();
                String Contact = contact.getText().toString();
                String Address = address.getText().toString();
                String ParkingSpace = parkingSpace.getText().toString();
                String ParkingName = parkingName.getText().toString();
                if (Name.equals("")){
                    name.setError("Enter Valid Name");
                    name.setFocusable(true);
//                } else if (userCategory.equals("User")&&Age.equals("")) {
//                    age.setError("Enter Valid Age");
//                    age.setFocusable(true);
                }
                    else if (ParkingName.equals("")) {
                    age.setError("Enter Parking Name");
                    age.setFocusable(true);
                }
                    else if (ParkingSpace.equals("")){
                            age.setError("Enter Parking Space");
                            age.setFocusable(true);
                } else if (Contact.equals("")){
                    contact.setError("Enter Valid Contact Number");
                    contact.setFocusable(true);
                } else if (Address.equals("")){
                    address.setError("Enter Valid Address");
                    address.setFocusable(true);
                } else if (count==0){
                    Snackbar.make(v, "Please Select Image", Snackbar.LENGTH_LONG).show();
                } else {
                            progressDialog.show();
                            firbaseAuthenticationClass.RegisterUser(userGmail, userPassword, Contact, Name,ParkingName,ParkingSpace, Address,userCategory, imagePath, CompleteProfileActivity.this, progressDialog);

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(CompleteProfileActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requestCode&&resultCode==resultCode
                &&data!=null && data.getData()!=null){

            imagePath= data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),imagePath);
                profileImage.setImageBitmap(bitmap);
                count=1;

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
