package com.android.emergencymedicalsystem.driver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.DriverMainActivity;
import com.android.emergencymedicalsystem.MapsActivity;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.Passenger;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.tomer.fadingtextview.FadingTextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RideActivity extends AppCompatActivity {
    Button startBtn,endBtn,subminBtn,locationBtn;
    private ApiInterface apiInterface;
    String getCell,userName,userCell,userArea,userDivision,userLatitude,userLongitude,passengerCell,start_time,userToken;
    Date date1,date2;
    TextView nameTv,cellTv,divisionTv,areaTv;
    FadingTextView animation_text;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Manage Ride");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        nameTv=findViewById(R.id.passenger_name_tv);
        cellTv=findViewById(R.id.cell_tv);
        divisionTv=findViewById(R.id.division_tv);
        areaTv=findViewById(R.id.area_tv);
        animation_text=findViewById(R.id.style_text);
        animation_text.setVisibility(View.GONE);

    //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        start_time = sharedPreferences.getString(Constant.START_TIME_SHARED_PREF, "Not Available");

        if (start_time.equals("null"))
        {
            animation_text.setVisibility(View.GONE);
        }else{
            animation_text.setVisibility(View.VISIBLE);
            SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss a");
            try {
                date1 = sdf.parse(start_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
//Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(RideActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getAmbulanceData(getCell);

        }
        startBtn = findViewById(R.id.start_ride_btn);
        endBtn = findViewById(R.id.end_ride_btn);
        subminBtn = findViewById(R.id.submit_btn);
        locationBtn=findViewById(R.id.location_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RideActivity.this);
                builder.setTitle("Want to start ride ?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss a");
                                String dateString = sdf.format(date);
                                try {
                                    date1 = sdf.parse(dateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Toasty.info(RideActivity.this, "Ride Started!", Toasty.LENGTH_LONG).show();
                                //           onClickStart();
                                animation_text.setVisibility(View.VISIBLE);
                                start_time=dateString;
                                SharedPreferences sp = RideActivity.this.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sp.edit();
                                //Adding values to editor
                                editor.putString(Constant.START_TIME_SHARED_PREF, start_time);
                                //Saving values to editor
                                editor.commit();

                                Intent intent=new Intent(RideActivity.this, MapsActivity.class);
                                intent.putExtra("lat",userLatitude);
                                intent.putExtra("lng",userLongitude);
                                startActivity(intent);

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

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RideActivity.this);
                builder.setTitle("Want to stop ride ?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("hh-mm-ss a");
                                String dateString = sdf.format(date);
                                try {
                                    date2 = sdf.parse(dateString);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Toasty.info(RideActivity.this, "Ride Ended!", Toasty.LENGTH_LONG).show();
                                //         onClickStop();
                                animation_text.setVisibility(View.GONE);
                                start_time="null";
                                SharedPreferences sp = RideActivity.this.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                //Creating editor to store values to shared preferences
                                SharedPreferences.Editor editor = sp.edit();
                                //Adding values to editor
                                editor.putString(Constant.START_TIME_SHARED_PREF, start_time);
                                //Saving values to editor
                                editor.commit();

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

subminBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final String[] taskList = {"Yes", "No"};
        AlertDialog.Builder builder = new AlertDialog.Builder(RideActivity.this);
        builder.setTitle("Want to submit ride data ?");
        builder.setCancelable(false);
        builder.setItems(taskList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                switch (position) {
                    case 0:
                        if (date1==null)
                        {
                            Toasty.error(RideActivity.this,"Start your ride", Toasty.LENGTH_SHORT).show();
                        }
                        else if (date2 == null)
                        {
                            Toasty.error(RideActivity.this,"End your ride", Toasty.LENGTH_SHORT).show();
                        }
                        else {
                            long mills = date2.getTime() - date1.getTime();
                            int hours = (int) (mills/(1000 * 60 * 60));
                            int mins = (int) (mills/(1000*60)) % 60;
                            if (mins<10){
                                String diff = hours + ".0" + mins;
                                sendRequest(getCell,diff);
                            }else {
                                String diff = hours + "." + mins;
                                sendRequest(getCell,diff);
                            }
                            start_time="null";
                            SharedPreferences sp = RideActivity.this.getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sp.edit();
                            //Adding values to editor
                            editor.putString(Constant.START_TIME_SHARED_PREF, start_time);
                            //Saving values to editor
                            editor.commit();
                            String status="Available";
                            String request="Null";
                            updateStatus(getCell,status,request);
                        }
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
locationBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toasty.success(RideActivity.this,"Opening Map Location", Toasty.LENGTH_SHORT).show();
        Intent intent=new Intent(RideActivity.this, MapsActivity.class);
        intent.putExtra("lat",userLatitude);
        intent.putExtra("lng",userLongitude);
        startActivity(intent);
    }
});
    }




    private void sendRequest(String cell,String time) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.sendReq(cell,time);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(RideActivity.this, message, Toasty.LENGTH_LONG).show();

                }

                else {
                    loading.dismiss();
                    Toasty.error(RideActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ambulance> call, Throwable t) {

                loading.dismiss();
                Toasty.error(RideActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    public void getPassengerData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Passenger>> call;
        call = apiInterface.getPassenger(cell);

        call.enqueue(new Callback<List<Passenger>>() {
            @Override
            public void onResponse(Call<List<Passenger>> call, Response<List<Passenger>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Passenger> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {

                        userName = profileData.get(0).getName();
                        userCell = profileData.get(0).getCell();
                        userDivision = profileData.get(0).getDivision();
                        userArea = profileData.get(0).getArea();
                        userLatitude = profileData.get(0).getLatitude();
                        userLongitude = profileData.get(0).getLongitude();

                        nameTv.setText(userName);
                        cellTv.setText(userCell);
                        divisionTv.setText(userDivision);
                        areaTv.setText(userArea);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Passenger>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });
    }
    public void getAmbulanceData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getDriverAmbulance(cell);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {


                        passengerCell = profileData.get(0).getPassenger_cell();
                        getPassengerData(passengerCell);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
            }
        });

    }
    private void updateStatus(String cell,String status,String request) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.updateStatus(cell,status,request);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(RideActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(RideActivity.this, DriverMainActivity.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    loading.dismiss();
                    Toasty.error(RideActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ambulance> call, Throwable t) {

                loading.dismiss();
                Toasty.error(RideActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }


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