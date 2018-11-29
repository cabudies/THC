package com.example.asus.thc.AccountActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.thc.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    Button btnHomeLogin,btnLocation;
    TextView userSignedIn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        userSignedIn = findViewById(R.id.home_screen_text_view_user);
        firebaseAuth = FirebaseAuth.getInstance();
//        btnHomeLogin = findViewById(R.id.btnhomelogin);
//        btnLocation = findViewById(R.id.btnLocation);
//
//        txtSlogan = findViewById(R.id.txtSlogan);
//        firebaseAuth = FirebaseAuth.getInstance();
//        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Fh_Ink.ttf");
//        txtSlogan.setTypeface(face);
//
//        btnHomeLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signIn = new Intent(HomePage.this, SignInForm.class);
//                startActivity(signIn);
//            }
//        });
//
//        btnLocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            // show alert dialog box to user to such that no user found. Please
            // show new user.
            userSignedIn.setText("No user found.");
            AlertDialog.Builder builder = new AlertDialog.Builder(HomePage.this);
            builder.setTitle("No user found.");
            builder.setMessage("Please login to create a new account.");
            builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(HomePage.this, SignInForm.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            // fetch the records from firestore
            userSignedIn.setText("User successfully Signed In");
        }
    }
}
