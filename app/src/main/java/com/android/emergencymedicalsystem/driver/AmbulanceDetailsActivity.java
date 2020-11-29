package com.android.emergencymedicalsystem.driver;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmbulanceDetailsActivity extends AppCompatActivity {
    String name, type,cell, fair,reg_no,facility,division,area;
    ImageView amb_image;
    Button updateBtn;
    String getCell,userName,userCell,userPassword,userArea,userDivision,userType,userFair,userRegNo,userImage,userFacility;
    private ApiInterface apiInterface;
    private ProgressDialog loading;
    TextView name_tv,type_tv,cell_tv,fair_tv,reg_no_tv,facility_tv,div_tv,area_tv,pass_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amb_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ambulance Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        name_tv=findViewById(R.id.ambulance_name_tv);
        amb_image=findViewById(R.id.amb_image);
        amb_image.setScaleType(ImageView.ScaleType.FIT_XY);
        cell_tv=findViewById(R.id.amb_cell_tv);
        pass_tv=findViewById(R.id.amb_pass_tv);
        type_tv=findViewById(R.id.amb_type_tv);
        fair_tv=findViewById(R.id.amb_fair_tv);
        facility_tv=findViewById(R.id.facility_tv);
        reg_no_tv=findViewById(R.id.amb_reg_no_tv);
        div_tv=findViewById(R.id.div_tv);
        area_tv=findViewById(R.id.area_tv);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(AmbulanceDetailsActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
        getProfileData(getCell);
        }
        updateBtn=findViewById(R.id.cirUpdateButton);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AmbulanceDetailsActivity.this,UpdateProfileActivity.class);
                intent.putExtra("name",userName);
                intent.putExtra("pass",userPassword);
                intent.putExtra("type",userType);
                intent.putExtra("fair",userFair);
                intent.putExtra("reg",userRegNo);
                intent.putExtra("facility",userFacility);
                intent.putExtra("division",userDivision);
                intent.putExtra("area",userArea);
                startActivity(intent);
            }
        });
    }
    public void getProfileData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getDriverProfile(cell);

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {


                        userName = profileData.get(0).getName();
                        userCell = profileData.get(0).getCell();
                        userPassword = profileData.get(0).getPassword();
                        userType = profileData.get(0).getType();
                        userFair = profileData.get(0).getFair();
                        userRegNo = profileData.get(0).getV_reg_no();
                        userImage = profileData.get(0).getImage();
                        userFacility = profileData.get(0).getFacility();
                        userDivision = profileData.get(0).getDivision();
                        userArea = profileData.get(0).getArea();

                        name_tv.setText(userName);
                        cell_tv.setText(userCell);
                        type_tv.setText(userType);
                        fair_tv.setText("Tk "+ userFair +" per hour");
                        reg_no_tv.setText(userRegNo);
                        facility_tv.setText(userFacility);
                        div_tv.setText(userDivision);
                        area_tv.setText(userArea);
                        Glide.with(AmbulanceDetailsActivity.this).load(Constant.IMAGE_URL+userImage).into(amb_image);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

                Log.d("Error : ", t.toString());
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