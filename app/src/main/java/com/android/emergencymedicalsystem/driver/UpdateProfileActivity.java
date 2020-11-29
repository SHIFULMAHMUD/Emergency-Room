package com.android.emergencymedicalsystem.driver;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText name_et, password_et,division_et,area_et,regno_et,type_et,fair_et,facility_et;
    Button update_btn;
    private ApiInterface apiInterface;
    String getCell,text,name,password,division,area,regno,type,fair,facility;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amb_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Ambulance Information");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(UpdateProfileActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        name_et=findViewById(R.id.editTextUpdateName);
        password_et=findViewById(R.id.editTextUpdatePassword);
        division_et=findViewById(R.id.editTextUpdateDivision);
        area_et=findViewById(R.id.editTextUpdateArea);
        regno_et=findViewById(R.id.editTextUpdateRegNo);
        type_et=findViewById(R.id.editTextUpdateType);
        fair_et=findViewById(R.id.editTextUpdateFair);
        facility_et=findViewById(R.id.editTextUpdateFacility);

        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("pass");
        division = getIntent().getStringExtra("division");
        area = getIntent().getStringExtra("area");
        regno= getIntent().getStringExtra("reg");
        type= getIntent().getStringExtra("type");
        fair= getIntent().getStringExtra("fair");
        facility= getIntent().getStringExtra("facility");

        name_et.setText(name);
        password_et.setText(password);
        division_et.setText(division);
        area_et.setText(area);
        regno_et.setText(regno);
        type_et.setText(type);
        fair_et.setText(fair);
        facility_et.setText(facility);

        division_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] divisionList = {"Dhaka", "Chittagong","Barishal","Mymensingh","Khulna","Rajshahi","Rangpur","Sylhet"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Choose Division");
                builder.setCancelable(false);
                builder.setItems(divisionList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 1:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
                                break;
                            case 2:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 3:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
                                break;
                            case 4:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 5:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
                                break;
                            case 6:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 7:
                                division_et.setText(divisionList[position]);
                                text = divisionList[position];
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
        type_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] accountList = {"AC", "NON-AC","ICU"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Choose Ambulance Type");
                builder.setCancelable(false);
                builder.setItems(accountList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                type_et.setText(accountList[position]);
                                text = accountList[position];
                                break;

                            case 1:
                                type_et.setText(accountList[position]);
                                text = accountList[position];
                                break;
                            case 2:
                                type_et.setText(accountList[position]);
                                text = accountList[position];
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

        update_btn=findViewById(R.id.updateProfileButton);
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Confirm Submission?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String name = name_et.getText().toString();
                                String cell=getCell;
                                String password = password_et.getText().toString();
                                String reg_no = regno_et.getText().toString();
                                String division = division_et.getText().toString();
                                String area = area_et.getText().toString();
                                String type = type_et.getText().toString();
                                String fair = fair_et.getText().toString();
                                String facility = facility_et.getText().toString();

                                if (name.isEmpty()) {
                                    name_et.setError("Name can't be empty ! ");
                                    name_et.requestFocus();

                                }
                                else if (reg_no.isEmpty()) {
                                    regno_et.setError("Vehicle Reg. No can't be empty ! ");
                                    regno_et.requestFocus();

                                }
                                else if (type.isEmpty()) {
                                    type_et.setError("Ambulance type can't be empty ! ");
                                    type_et.requestFocus();
                                    Toasty.error(UpdateProfileActivity.this,"Please select ambulance type ! ", Toasty.LENGTH_SHORT).show();
                                }
                                else if (password.isEmpty()) {
                                    password_et.setError("Password can't be empty ! ");
                                    password_et.requestFocus();

                                }
                                else if (division.isEmpty()) {
                                    division_et.setError("Division can't be empty ! ");
                                    division_et.requestFocus();
                                    Toasty.error(UpdateProfileActivity.this,"Please select your division ! ", Toasty.LENGTH_SHORT).show();
                                }
                                else if (area.isEmpty()) {
                                    area_et.setError("Area can't be empty ! ");
                                    area_et.requestFocus();
                                }
                                else if (fair.isEmpty()) {
                                    fair_et.setError("Ambulance fair can't be empty ! ");
                                    fair_et.requestFocus();
                                }
                                else if (facility.isEmpty()) {
                                    facility_et.setError("Ambulance facility can't be empty ! ");
                                    facility_et.requestFocus();
                                }
                                else {
                                    //call login method
                                    updateProfile(name,cell,reg_no,type,password,division,area,fair,facility);
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

    }
    /*public void getDhkAreaData() {
        Call<List<User>> call = apiInterface.getDhkArea();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                areaList = response.body();
                areaNames.clear();
                for (int i = 0; i < areaList.size(); i++) {
                    areaNames.add(areaList.get(i).getArea());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
   */

    private void updateProfile(String name,String cell,String regno, String type,String password,String division,String area,String fair, String facility) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Ambulance> call = apiInterface.updateProfile(name,cell,regno,type,password,division,area,fair,facility);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(UpdateProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(UpdateProfileActivity.this, AmbulanceDetailsActivity.class);
                    startActivity(intent);
                    finish();
                }



                else {
                    loading.dismiss();
                    Toasty.error(UpdateProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ambulance> call, Throwable t) {

                loading.dismiss();
                Toasty.error(UpdateProfileActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
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