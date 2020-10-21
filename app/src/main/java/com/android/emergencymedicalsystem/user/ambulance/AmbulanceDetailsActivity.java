package com.android.emergencymedicalsystem.user.ambulance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.MainActivity;
import com.android.emergencymedicalsystem.user.nurse.NurseDetailsActivity;
import com.android.emergencymedicalsystem.user.profile.ProfileActivity;
import com.android.emergencymedicalsystem.user.profile.UpdateProfileActivity;
import com.bumptech.glide.Glide;

public class AmbulanceDetailsActivity extends AppCompatActivity {
    String name, type,cell, fair,reg_no,facility,division,area,image;
    ImageView amb_image;
    Button hireBtn;
    private ProgressDialog loading;
    TextView name_tv,type_tv,cell_tv,fair_tv,reg_no_tv,facility_tv,div_tv,area_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ambulance Details");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(AmbulanceDetailsActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        name = getIntent().getStringExtra("name");
        type = getIntent().getStringExtra("type");
        cell = getIntent().getStringExtra("cell");
        fair = getIntent().getStringExtra("fair");
        facility = getIntent().getStringExtra("facility");
        reg_no = getIntent().getStringExtra("reg");
        division = getIntent().getStringExtra("division");
        area = getIntent().getStringExtra("area");
        image = getIntent().getStringExtra("img");

        name_tv=findViewById(R.id.ambulance_name_tv);
        amb_image=findViewById(R.id.amb_image);
        amb_image.setScaleType(ImageView.ScaleType.FIT_XY);
        cell_tv=findViewById(R.id.amb_cell_tv);
        type_tv=findViewById(R.id.amb_type_tv);
        fair_tv=findViewById(R.id.amb_fair_tv);
        facility_tv=findViewById(R.id.facility_tv);
        reg_no_tv=findViewById(R.id.amb_reg_no_tv);
        div_tv=findViewById(R.id.div_tv);
        area_tv=findViewById(R.id.area_tv);
        hireBtn=findViewById(R.id.hireAmbBtn);
        name_tv.setText(name);
        cell_tv.setText(cell);
        type_tv.setText(type);
        fair_tv.setText("Tk "+ fair +" per hour");
        facility_tv.setText(facility);
        reg_no_tv.setText(reg_no);
        div_tv.setText(division);
        area_tv.setText(area);
        Glide.with(AmbulanceDetailsActivity.this).load(Constant.IMAGE_URL+image).into(amb_image);
        hireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AmbulanceDetailsActivity.this);
                builder.setTitle("Confirm Submission?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String request = "Pending";
                                String cell = cell_tv.getText().toString();
                                    //call login method
                                    hireAmbulance(cell, request);


                                break;
                            case 1:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        dialog.dismiss();
                    }
                });


                AlertDialog accountTypeDialog = builder.create();

                accountTypeDialog.show();
            }
        });
    }
    private void hireAmbulance(String cell, String request) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.hireAmbulance(cell, request);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(AmbulanceDetailsActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(AmbulanceDetailsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }



                else {
                    loading.dismiss();
                    Toasty.error(AmbulanceDetailsActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ambulance> call, Throwable t) {

                loading.dismiss();
                Toasty.error(AmbulanceDetailsActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
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