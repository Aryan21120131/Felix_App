package com.example.felixapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

public class MobileNumber extends AppCompatActivity {

    Button next;
    PinView ph_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);
        hook();
        next.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void onClick(View view) {
                if (ph_no.getText().toString().length() != 10) {
                    Toast.makeText(MobileNumber.this, "Enter Valid Phone Number !!!", Toast.LENGTH_SHORT).show();
                } else {
                    LoginActivity.sharedPreferences.edit().putString("PHONE", ph_no.getText().toString()).commit();
                    startActivity(new Intent(MobileNumber.this, VerifyActivity.class));
                }
            }
        });
    }

    private void hook() {
        next=findViewById(R.id.next);
        ph_no=findViewById(R.id.reciever_no);
    }
}