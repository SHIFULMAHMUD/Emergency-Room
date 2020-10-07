package com.android.emergencymedicalsystem.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.emergencymedicalsystem.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class DriverSignupActivity extends AppCompatActivity {
    LinearLayout linearLayoutGotoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Account");
        linearLayoutGotoLogin=findViewById(R.id.ll9);
        linearLayoutGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DriverSignupActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}