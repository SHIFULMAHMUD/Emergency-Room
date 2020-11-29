package com.android.emergencymedicalsystem.user.ambulance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.Hospital;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.MainActivity;
import com.android.emergencymedicalsystem.user.nurse.NurseDetailsActivity;
import com.android.emergencymedicalsystem.user.profile.ProfileActivity;
import com.android.emergencymedicalsystem.user.profile.UpdateProfileActivity;
import com.bumptech.glide.Glide;

import java.util.List;

public class AmbulanceDetailsActivity extends AppCompatActivity {
    String name, type,cell, fair,reg_no,facility,division,area,image,token,distance;
    ImageView amb_image;
    private ApiInterface apiInterface;
    Button hireBtn;
    String getCell,id;
    private ProgressDialog loading;
    TextView name_tv,type_tv,cell_tv,fair_tv,reg_no_tv,facility_tv,div_tv,area_tv,distance_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ambulance Details");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        distance_tv=findViewById(R.id.distance_tv);
        id = getIntent().getStringExtra("id");
        String data[]=id.split(",");
        distance=data[1];
        distance_tv.setText(distance+" Km");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(AmbulanceDetailsActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getAmbData(data[0]);
        }
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

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
                                String status = "Pending";
                                String request = "Pending";
                                String time = "Null";
                                String cell = cell_tv.getText().toString();
                                    //call login method
                                hireAmbulance(cell,status, request,time,getCell);

                                String title = "New Ride Request";
                                String message = "You have a new ride request from a user. See details from app.";
                                getAmbData(id);
                                String user_token=token;

                                SendNotification(title,message,user_token);

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
    private void hireAmbulance(String cell,String status, String request,String time,String usercell) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.hireAmbulance(cell,status, request,time,usercell);
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
    private void SendNotification(String title,String message,String token) {

        /*loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
*/

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<User> call = apiInterface.sendNotification(title,message,token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                /*String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(TaskActivity.this, message, Toasty.LENGTH_SHORT).show();
                    *//*Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();*//*
                }



                else {
                    loading.dismiss();
                    Toasty.error(TaskActivity.this, message, Toasty.LENGTH_SHORT).show();
                }*/
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

  /*              loading.dismiss();
                Toasty.error(TaskActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();*/
            }
        });
    }
    public void getAmbData(String id) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getAmbulance(id);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {


                        name = profileData.get(0).getName();
                        cell = profileData.get(0).getCell();
                        type = profileData.get(0).getType();
                        fair = profileData.get(0).getFair();
                        reg_no = profileData.get(0).getV_reg_no();
                        image = profileData.get(0).getImage();
                        facility = profileData.get(0).getFacility();
                        division = profileData.get(0).getDivision();
                        area = profileData.get(0).getArea();
                        token = profileData.get(0).getToken();

                        name_tv.setText(name);
                        cell_tv.setText(cell);
                        type_tv.setText(type);
                        fair_tv.setText("Tk "+ fair +" per hour");
                        reg_no_tv.setText(reg_no);
                        facility_tv.setText(facility);
                        div_tv.setText(division);
                        area_tv.setText(area);
                        distance_tv.setText(distance+" Km");
                        Glide.with(AmbulanceDetailsActivity.this).load(Constant.IMAGE_URL+image).into(amb_image);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

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