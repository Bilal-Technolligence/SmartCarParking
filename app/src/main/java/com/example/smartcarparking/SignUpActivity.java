package com.example.smartcarparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    Button btnLogin,btnSignup;
    EditText txtName,txtEmail,txtPassword,rePass;
    ProgressDialog progressDialog;
    String category;
    private String selection;
    TextView mHaveAccountTv;
    ProgressDialog pd;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnSignup=(Button)findViewById(R.id.txtSignUp);
        txtEmail =(EditText)findViewById( R.id.editTextEmail ) ;
        txtPassword = (EditText)findViewById( R.id.editTextPassword ) ;
        txtName= (EditText)findViewById( R.id.editTextName) ;
        rePass = (EditText)findViewById( R.id.editTextRePassword ) ;
        mHaveAccountTv=findViewById(R.id.have_accounttv);
        progressDialog=new ProgressDialog(this);
        pd=new ProgressDialog(this);
        pd.setMessage("Logging In..... ");
        Intent in = getIntent();
        category = in.getStringExtra( "name" );
        mHaveAccountTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( SignUpActivity.this,LoginActivity.class );
                startActivity( intent );
                finish();
            }
        } );
        mAuth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=txtEmail.getText().toString().trim();
                String password=txtPassword.getText().toString().trim();
                String name = txtName.getText().toString();
                String Phone = rePass.getText().toString();

                if (name==null || name.equals( "" ))
                {
                    txtName.setError( "Please Fill Name" );
                }
                if (Phone.equals( "" ))
                {
                    rePass.setError( "Please Fill Password" );
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    txtEmail.setError("Invalid Email");
                    txtEmail.setFocusable(true);

                }else if (password.length()<6){
                    txtPassword.setError("Pasword Length Must Be grater than 6 characters");
                    txtPassword.setFocusable(true);

                }else {

                        Intent intent=new Intent(SignUpActivity.this,CompleteProfileActivity.class);
                        intent.putExtra("Email",email);
                        intent.putExtra("Password",password);
                         intent.putExtra("Name",name);
                        intent.putExtra("Category",category);
                        startActivity(intent);
                        finish();

                }

            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
