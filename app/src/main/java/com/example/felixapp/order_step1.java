package com.example.felixapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

public class order_step1 extends AppCompatActivity {

    EditText name;
    Button next;
    PinView receiver_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_step1);
        hook();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()||receiver_no.getText().toString().isEmpty()){
                    name.setHint("FILL ALL THE REQUIRED FIELDS");
                }else{
                    com.example.fleix.LoginActivity.sharedPreferences.edit().putString("RECEIVER_NAME",name.getText().toString());
                    com.example.fleix.LoginActivity.sharedPreferences.edit().putString("RECEIVER_NO",receiver_no.getText().toString());
                }
            }
        });
    }

    private void hook() {
        name=findViewById(R.id.reciever_name);
        next=findViewById(R.id.next_step);
        receiver_no=findViewById(R.id.reciever_no);
    }
}