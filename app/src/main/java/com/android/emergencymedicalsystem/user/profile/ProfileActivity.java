package com.android.emergencymedicalsystem.user.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
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

public class ProfileActivity extends AppCompatActivity {
    private List<User> contactsList;
    private ApiInterface apiInterface;
    String getCell,userName,userCell,userPassword,userArea,userDivision,userBloodGroup;
    Button updateBtn;
    TextView nameTv,cellTv,divisionTv,areaTv,bloodTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View / Update Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        nameTv=findViewById(R.id.profile_name_tv);
        cellTv=findViewById(R.id.profile_cell_tv);
        divisionTv=findViewById(R.id.division_tv);
        areaTv=findViewById(R.id.area_tv);
        bloodTv=findViewById(R.id.blood_group_tv);
        updateBtn=findViewById(R.id.updateProfileBtn);
//Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(ProfileActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getProfileData(getCell);
        }
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,UpdateProfileActivity.class);
                intent.putExtra("name",userName);
                intent.putExtra("pass",userPassword);
                intent.putExtra("blood",userBloodGroup);
                intent.putExtra("division",userDivision);
                intent.putExtra("area",userArea);
                startActivity(intent);
            }
        });
    }


    public void getProfileData(String cell) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<User>> call;
        call = apiInterface.getProfile(cell);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<User> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {


                        userName = profileData.get(0).getName();
                        userCell = profileData.get(0).getCell();
                        userPassword = profileData.get(0).getPassword();
                        userDivision = profileData.get(0).getDivision();
                        userArea = profileData.get(0).getArea();
                        userBloodGroup = profileData.get(0).getBlood();

                        nameTv.setText(userName);
                        cellTv.setText(userCell);
                        divisionTv.setText(userDivision);
                        areaTv.setText(userArea);
                        bloodTv.setText(userBloodGroup);
                    }

                }
            }

            @Override
        public void onFailure(Call<List<User>> call, Throwable t) {

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



