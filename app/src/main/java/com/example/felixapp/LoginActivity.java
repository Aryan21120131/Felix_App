package com.example.felixapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Spinner role;
    String[] roles={"USER","DELIVERY BOY","WAREHOUSE MANAGER"};
    ImageView image_role;
    Button next;
    public static SharedPreferences sharedPreferences;
    String Role;
    EditText user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences=getSharedPreferences("SP",MODE_PRIVATE);
        hook();
        Role="USER";
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,roles);
        role.setAdapter(adapter);
        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:image_role.setImageResource(R.drawable.user_logo);
                        Role="USER";
                        break;
                    case 1:image_role.setImageResource(R.drawable.delivery_boy_logo);
                        Role="DELIVERY_BOY";
                        break;
                    case 2:image_role.setImageResource(R.drawable.manager_logo);
                        Role="MANAGER";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("ROLE",Role).commit();
                sharedPreferences.edit().putString("USERNAME",user.getText().toString());
                startActivity(new Intent(LoginActivity.this, com.example.fleix.MobileNumber.class));
            }
        });
    }

    private void hook() {
        role=findViewById(R.id.role);
        image_role=findViewById(R.id.image_role);
        next=findViewById(R.id.next);
        user=findViewById(R.id.UserName);
    }
}