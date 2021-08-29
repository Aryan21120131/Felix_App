package com.example.felixapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {

    PinView pin;
    Button Login;
    TextView Text;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String VerificationCode;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        hook();
        databaseReference= FirebaseDatabase.getInstance().getReference("USERS");
        Text.setText("OTP SEND TO PHONE NUMBER \n"+LoginActivity.sharedPreferences.getString("PHONE",""));
        Login.setOnClickListener(view -> {
            Toast.makeText(VerifyActivity.this, databaseReference.getKey().toString(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(VerifyActivity.this,Home.class));
            databaseReference.child("ROLE").setValue(LoginActivity.sharedPreferences.getString("ROLE",""));
            databaseReference.child("PHONE").setValue(LoginActivity.sharedPreferences.getString("PHONE",""));
                PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+"8287335701")       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(VerifyActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
        });
    }

    private void hook() {
        pin=findViewById(R.id.otp_code);
        Login=findViewById(R.id.login_btn);
        Text=findViewById(R.id.OTP_NUMBER_TEXT);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            VerificationCode=s;
            Toast.makeText(VerifyActivity.this, "Code Sent", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code!=null){
                Toast.makeText(VerifyActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }
    };

    private void verifyCode(String verification_code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationCode,verification_code);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyActivity.this, "Hii New User", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}