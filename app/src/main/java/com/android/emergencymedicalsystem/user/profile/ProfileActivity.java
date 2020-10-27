package com.android.emergencymedicalsystem.user.profile;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    String getCell,userName,userCell,userPassword,userArea,userDivision,userBloodGroup,userBloodStatus;
    Button updateBtn;
    TextView nameTv,cellTv,divisionTv,areaTv,bloodTv;
    RadioGroup radioGroup;
    RadioButton radioButtonAvailable,radioButtonNotAvailable;
    String string_rb;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        nameTv=findViewById(R.id.profile_name_tv);
        cellTv=findViewById(R.id.profile_cell_tv);
        divisionTv=findViewById(R.id.division_tv);
        areaTv=findViewById(R.id.area_tv);
        bloodTv=findViewById(R.id.blood_group_tv);
        updateBtn=findViewById(R.id.updateProfileBtn);
        radioButtonAvailable=findViewById(R.id.rb1);
        radioButtonNotAvailable=findViewById(R.id.rb2);
//Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        radioGroup=findViewById(R.id.radiogroup_blood);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                string_rb = rb.getText().toString().trim();
            }
        });
        radioButtonAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Update Blood Donation Status ?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String cell=getCell;
                                radioButtonAvailable.setChecked(true);
                                string_rb=radioButtonAvailable.getText().toString().trim();
                                String status = string_rb;

                                    updateStatus(cell,status);

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
        radioButtonNotAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Update Blood Donation Status ?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String cell=getCell;
                                radioButtonNotAvailable.setChecked(true);
                                string_rb=radioButtonNotAvailable.getText().toString().trim();
                                String status = string_rb;

                                updateStatus(cell,status);

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
    private void updateStatus(String cell,String status) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<User> call = apiInterface.updateStatus(cell,status);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(ProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                }



                else {
                    loading.dismiss();
                    Toasty.error(ProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                loading.dismiss();
                Toasty.error(ProfileActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
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
                        userBloodStatus = profileData.get(0).getStatus();
                        if (userBloodStatus.equals("Available")){
                            radioButtonAvailable.setChecked(true);
                        }
                        else if (userBloodStatus.equals("Not Available")){
                            radioButtonNotAvailable.setChecked(true);
                        }
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



