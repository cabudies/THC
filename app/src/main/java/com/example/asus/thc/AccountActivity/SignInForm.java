package com.example.asus.thc.AccountActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.asus.thc.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class SignInForm extends AppCompatActivity implements DialogInterface.OnClickListener {

    private EditText edtPhone;
    private Button loginBtn;
    private static final String TAG = SignInForm.class.getName();
    private ProgressBar signInProgressBar;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_form);

        edtPhone = findViewById(R.id.edtPhone);
        loginBtn = findViewById(R.id.loginBtn);
        signInProgressBar = findViewById(R.id.sign_in_progress);

        firebaseAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(final PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "Verification completed.");

                signInProgressBar.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(SignInForm.this);
                builder.setTitle("Verification Completed.");
                builder.setMessage("Signing in the user.");
                edtPhone.setText("");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        signInProgressBar.setVisibility(View.VISIBLE);
                        signInUser(phoneAuthCredential);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d(TAG, "Verification failed");

                AlertDialog.Builder builder = new AlertDialog.Builder(SignInForm.this);
                builder.setTitle("Verification Failed.");
                builder.setMessage("Please try again.");
                edtPhone.setText("");
                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        };

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+91" + edtPhone.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        SignInForm.this,
                        mCallbacks);

                signInProgressBar.setVisibility(View.VISIBLE);
            }
        });

    }

    private void signInUser(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        signInProgressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User signed in Successfully.");
                            Intent intent = new Intent(SignInForm.this, HomePage.class);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.e(TAG, "Signing in failed. Please try again");
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignInForm.this);
                                builder.setTitle("No user found.");
                                builder.setMessage("Please click on 'Login' button to create new user account");
                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                    }
                });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        // dismiss the dialog interface.
        dialog.dismiss();
    }
}
