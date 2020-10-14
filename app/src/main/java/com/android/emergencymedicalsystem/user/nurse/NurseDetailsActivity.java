package com.android.emergencymedicalsystem.user.nurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.user.profile.UpdateProfileActivity;

public class NurseDetailsActivity extends AppCompatActivity {
    String name, type,cell, address,day,time,hospital;
    TextView name_tv,type_tv,cell_tv,address_tv,day_tv,time_tv,hospital_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nurse Details");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(NurseDetailsActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        cell = getIntent().getStringExtra("cell");
        hospital = getIntent().getStringExtra("hospital");
        address = getIntent().getStringExtra("address");
        day = getIntent().getStringExtra("day");
        time = getIntent().getStringExtra("time");
        name_tv=findViewById(R.id.nurse_name_tv);
        cell_tv=findViewById(R.id.nurse_cell_tv);
        type_tv=findViewById(R.id.nurse_type_tv);
        hospital_tv=findViewById(R.id.nurse_hospital_tv);
        address_tv=findViewById(R.id.nurse_area_tv);
        day_tv=findViewById(R.id.nurse_day_tv);
        time_tv=findViewById(R.id.nurse_time_tv);

        name_tv.setText(name);
        cell_tv.setText(cell);
        type_tv.setText(type);
        hospital_tv.setText(hospital);
        address_tv.setText(address);
        time_tv.setText(time);
        day_tv.setText(day);
    }
    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}