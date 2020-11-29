package com.android.emergencymedicalsystem;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.emergencymedicalsystem.driver.AmbulanceDetailsActivity;
import com.android.emergencymedicalsystem.driver.DriverLoginActivity;
import com.android.emergencymedicalsystem.driver.RideActivity;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverMainActivity extends AppCompatActivity {
    private Dialog dialog;
    String getCell,userRequest,userStatus,userCell,userToken;
    Button rideBtn,profileBtn;
    TextView fairTv;
    String fair,time,finalfair;
    double ridefair,ridetime,totalfair;
    private ApiInterface apiInterface;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_driver);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EMS Plus");
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        getRideData(getCell);

        getFairData(getCell);

        rideBtn =findViewById(R.id.cirRideButton);

        getProfileData(getCell);

        fairTv=findViewById(R.id.fair_tv);

        rideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userStatus.equals("Unavailable")){
                Intent intent=new Intent(DriverMainActivity.this, RideActivity.class);
                startActivity(intent);
            }else {
                    Toasty.info(DriverMainActivity.this,"No ride available !", Toasty.LENGTH_SHORT).show();
                }
            }
        });

        profileBtn=findViewById(R.id.profileButton);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DriverMainActivity.this, AmbulanceDetailsActivity.class);
                startActivity(intent);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent=new Intent(DriverMainActivity.this,DriverLoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void getRideData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getRideData(cell);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {

                        userRequest = profileData.get(0).getRequest();
                        userCell = profileData.get(0).getPassenger_cell();

                        getPassengerToken(userCell);
                        if (userRequest.equals("Pending")){
                            Requestdialog();
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });


    }
    private void Requestdialog() {

        dialog = new Dialog(DriverMainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.request_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        Button accept,reject;

        accept = dialog.findViewById(R.id.accept_btn);
        reject = dialog.findViewById(R.id.reject_btn);



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cell=getCell;
                String status = "Unavailable";
                String request = "Confirmed";
                confirmRequest(cell,status,request);

                getPassengerToken(userCell);

                String title = "Ride Request Confirmed";
                String message = "Your ride request has been confirmed by driver.";
                SendNotification(title,message,userToken);

                dialog.cancel();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cell=getCell;
                String status = "Available";
                String request = "Rejected";
                rejectRequest(cell,status,request);
                getPassengerToken(userCell);
                String title = "Ride Request Rejected";
                String message = "Your ride request has been rejected by driver.";
                SendNotification(title,message,userToken);

                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void confirmRequest(String cell,String status,String request) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.confirmReq(cell,status,request);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(DriverMainActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(DriverMainActivity.this, RideActivity.class);
                    startActivity(intent);
                }



                else {
                    loading.dismiss();
                    Toasty.error(DriverMainActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ambulance> call, Throwable t) {

                loading.dismiss();
                Toasty.error(DriverMainActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }
    private void rejectRequest(String cell,String status,String request) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.rejectReq(cell,status,request);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(DriverMainActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
                else {
                    loading.dismiss();
                    Toasty.error(DriverMainActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ambulance> call, Throwable t) {

                loading.dismiss();
                Toasty.error(DriverMainActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }
    public void getProfileData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getStatus(cell);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> profileData;

                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {

                        userStatus = profileData.get(0).getStatus();

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });


    }
    public void getPassengerToken(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getToken(cell);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {

                        userToken = profileData.get(0).getToken();

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });


    }
    private void SendNotification(String title,String message,String token) {

        /*loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
*/

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.sendDriverNotification(title,message,token);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

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
            public void onFailure(Call<Ambulance> call, Throwable t) {

  /*              loading.dismiss();
                Toasty.error(TaskActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();*/
            }
        });
    }
    public void getFairData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getDriverFair(cell);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> fairdata;
                    fairdata = response.body();

                    if (fairdata.isEmpty() || fairdata.get(0).getTime().equals("Null") || fairdata.get(0).getTime().equals("")) {
                        fairTv.setText("");
                    } else {

                        fair = fairdata.get(0).getFair();
                        time = fairdata.get(0).getTime();
                        ridefair= Double.parseDouble(fair);
                        ridetime= Double.parseDouble(time);
                        totalfair=ridefair*ridetime;
                        finalfair= String.valueOf(totalfair);
                        fairTv.setText("Collect Ride Fair : Tk "+finalfair);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });


    }
}