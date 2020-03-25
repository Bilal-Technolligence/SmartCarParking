package com.example.smartcarparking;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirbaseAuthenticationClass extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    final DatabaseReference reference = database.getReference("Users");

    public void LoginUser(String EMAIL, String PASSWORD, final Activity activity, final ProgressDialog progressDialog) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String uid = mAuth.getCurrentUser().getUid();
                            reference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String cat = dataSnapshot.child("category").getValue().toString();
                                    if(cat.equals("User")){
                                        activity.startActivity(new Intent(activity, MainActivity.class));
                                        activity.finish();
                                        progressDialog.dismiss();
                                    }
                                    else
                                    {
                                        activity.startActivity(new Intent(activity, MainActivity.class));
                                        activity.finish();
                                        progressDialog.dismiss();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    public void RegisterUser(final String userPassword, final String contact, final String name, final String parkingName, final String parkingSpace, final String address, final String userCategory, final String ImagePath, final String userGmail, final CompleteProfileActivity activity, final ProgressDialog progressDialog) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(userGmail, userPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                            storageReference.putFile(Uri.parse(ImagePath)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!uriTask.isSuccessful()) ;
                                    Uri downloadUri = uriTask.getResult();

                                    UserAttr userAttr = new UserAttr();
                                    userAttr.setName(name);

                                    userAttr.setCategory(userCategory);
//                                    if (userCategory.equals("User"))
//                                        userAttr.setAge(age);
//                                    if (userCategory.equals("Service")){
                                        userAttr.setParkingName(parkingName);
                                        userAttr.setParkingSpace(parkingSpace);
                                        userAttr.setContact(contact);
                                    userAttr.setEmail(userGmail);
                                    userAttr.setId(uid);
                                    userAttr.setAddress(address);
                                    userAttr.setImageUrl(downloadUri.toString());
                                    userAttr.setStatus(0);
                                    reference.child(uid).setValue(userAttr);
                                    if (userCategory.equals("User"))
                                        activity.startActivity(new Intent(activity, MainActivity.class));
                                    else
                                        activity.startActivity(new Intent(activity, MainActivity.class));
                                    Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT).show();
                                    activity.finish();
                                    progressDialog.dismiss();

                                }
                            });


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}
