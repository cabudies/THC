package com.example.asus.thc.AccountActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.thc.Model.User;
import com.example.asus.thc.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInForm extends AppCompatActivity {

    EditText edtPhone;
    EditText edtPassword;
    Button loginBtn;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_form);

        edtPhone=(EditText)findViewById(R.id.edtPhone);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        loginBtn=(Button)findViewById(R.id.loginBtn);
        signupBtn=(Button)findViewById(R.id.signupBtn);

        //INIT FIREBASE

        FirebaseDatabase database;
        database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user;
        table_user = database.getReference("User");


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(SignInForm.this);
                mDialog.setMessage("Please waiting...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener(){


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



    }
}
