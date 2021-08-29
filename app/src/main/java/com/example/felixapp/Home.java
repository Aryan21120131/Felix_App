package com.example.felixapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    private RecyclerView recyclerView;
    FloatingActionButton add;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        hook();
        setNav();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Home.this, databaseReference.child("ORDERS").getDatabase().toString(), Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(Home.this,order_step1.class));
            }
        });
    }

    private void setNav() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_email:
                    try {
                        Intent email_intent=new Intent(Intent.ACTION_SENDTO);
                        String[] to={"Felix-dh@outlook.com"};
                        email_intent.setData(Uri.parse("mailto:"));
                        email_intent.putExtra(Intent.EXTRA_EMAIL,to);
                        startActivity(Intent.createChooser(email_intent,"Send Email"));
                        break;
                    }catch (Exception e){

                    }
                case R.id.menu_call:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + "9938813445"));
                    startActivity(intent);
                    break;
                case R.id.menu_sms:
                    Intent sms_intent = new Intent(Intent.ACTION_VIEW);
                    sms_intent.setType("vnd.android-dir/mms-sms");
                    sms_intent.setData(Uri.parse("sms:" + "9938813445"));
                    startActivity(sms_intent);
                    break;
                case R.id.menu_logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(Home.this,LoginActivity.class));
                    break;
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        Toolbar toolbar = findViewById(R.id.toolBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setTitle("FELIX");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);
        TextView name_nav = headerView.findViewById(R.id.username_nav);
        TextView phone_nav=headerView.findViewById(R.id.phone_nav);
        name_nav.setText(LoginActivity.sharedPreferences.getString("USERNAME","USERNAME"));
        phone_nav.setText(LoginActivity.sharedPreferences.getString("PHONE",""));
    }

    private void hook() {
        navigationView = findViewById(R.id.navigation_drawer);
        recyclerView=findViewById(R.id.user_recycle);
        add=findViewById(R.id.new_order);
    }
}