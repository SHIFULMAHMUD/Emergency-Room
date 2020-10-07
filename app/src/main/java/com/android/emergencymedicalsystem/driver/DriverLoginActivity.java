package com.android.emergencymedicalsystem.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.emergencymedicalsystem.user.MainActivity;
import com.android.emergencymedicalsystem.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class DriverLoginActivity extends AppCompatActivity {
    LinearLayout gotoSignUp;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Emergency Medical Service");
        gotoSignUp = findViewById(R.id.ll5);
        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverLoginActivity.this, DriverSignupActivity.class);
                startActivity(intent);
            }
        });
        loginBtn = findViewById(R.id.cirLoginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverLoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}