package com.example.asus.thc.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.thc.Model.User;
import com.example.asus.thc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SignInForm extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtPassword;
    private Button loginBtn;
    private Button signupBtn;

    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_form);

        edtPhone=(EditText)findViewById(R.id.edtPhone);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        loginBtn=(Button)findViewById(R.id.loginBtn);
        signupBtn=(Button)findViewById(R.id.signupBtn);

        //INIT FIREBASE

        table_user = FirebaseDatabase.getInstance().getReference("User");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignInForm.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                table_user.child("User").addValueEventListener(new ValueEventListener(){


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        //Check if user does not exist in database
                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()){
                            //Get User Information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                            if (User.getPassword().equals(edtPassword.getText().toString())) {
                                Toast.makeText(SignInForm.this, "Sign in successfully !", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignInForm.this, "Sign in failed !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            mDialog.dismiss();
                            Toast.makeText(SignInForm.this, "User does not exist. Please sign up!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError){

                    }
                });

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        SignInForm.this,
                            SignUpForm.class);
                    startActivity(intent);
                }

        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
        });
    }

}
