package com.android.emergencymedicalsystem.user.blooddonor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.user.nurse.NurseDetailsActivity;

public class DonorDetailsActivity extends AppCompatActivity {
    String name, group,cell, division,area,status;
    TextView name_tv,group_tv,cell_tv,div_tv,area_tv,status_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Donor Details");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(DonorDetailsActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        name = getIntent().getStringExtra("name");
        group = getIntent().getStringExtra("group");
        cell = getIntent().getStringExtra("cell");
        division = getIntent().getStringExtra("div");
        area = getIntent().getStringExtra("area");
        status = getIntent().getStringExtra("status");

        name_tv=findViewById(R.id.donor_name_tv);
        cell_tv=findViewById(R.id.donor_cell_tv);
        group_tv=findViewById(R.id.blood_group_tv);
        div_tv=findViewById(R.id.donor_div_tv);
        area_tv=findViewById(R.id.donor_area_tv);
        status_tv=findViewById(R.id.status_tv);

        name_tv.setText(name);
        cell_tv.setText(cell);
        group_tv.setText(group);
        div_tv.setText(division);
        area_tv.setText(area);
        status_tv.setText(status);
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