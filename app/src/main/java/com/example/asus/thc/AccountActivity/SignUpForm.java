package com.example.asus.thc.AccountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.asus.thc.R;

public class SignUpForm extends AppCompatActivity{

    Button submitForm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_form);

        submitForm=(Button)findViewById(R.id.signupsubmit);

        submitForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        SignUpForm.this,
                        SignUpVerification.class);
                startActivity(intent);

            }
        });
    }


}
