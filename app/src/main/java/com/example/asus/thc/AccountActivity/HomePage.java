package com.example.asus.thc.AccountActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.thc.R;

public class HomePage extends AppCompatActivity {

    Button btnHomeLogin,btnLocation;
    TextView txtSlogan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        btnHomeLogin=(Button)findViewById(R.id.btnhomelogin);
        btnLocation=(Button)findViewById(R.id.btnLocation);

        txtSlogan=(TextView)findViewById(R.id.txtSlogan);
        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/Fh_Ink.ttf");
        txtSlogan.setTypeface(face);

        btnHomeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(HomePage.this, SignInForm.class);
                startActivity(signIn);
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
