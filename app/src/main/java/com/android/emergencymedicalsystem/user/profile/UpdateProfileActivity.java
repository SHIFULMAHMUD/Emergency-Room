package com.android.emergencymedicalsystem.user.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.user.SignupActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText name_et, password_et, blood_group_et,division_et,area_et,donation_date_et;
    Button update_btn;
    public Calendar myCalendar = Calendar.getInstance();
    public DatePickerDialog.OnDateSetListener appointment_date;
    private int mYear, mMonth, mDay;
    private ApiInterface apiInterface;
    private List<User> areaList;
    ArrayList<String> areaNames  = new ArrayList<String>();
    String getCell,text,name,password,blood_group,division,area,date;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
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
        blood_group_et=findViewById(R.id.editTextUpdateBloodGroup);
        division_et=findViewById(R.id.editTextUpdateDivision);
        area_et=findViewById(R.id.editTextUpdateArea);
        donation_date_et=findViewById(R.id.editTextUpdateDonationDate);
        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("pass");
        blood_group = getIntent().getStringExtra("blood");
        division = getIntent().getStringExtra("division");
        area = getIntent().getStringExtra("area");
        date = getIntent().getStringExtra("date");
        name_et.setText(name);
        password_et.setText(password);
        blood_group_et.setText(blood_group);
        division_et.setText(division);
        area_et.setText(area);
        donation_date_et.setText(date);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
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

        blood_group_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] bgList = {"O positive", "O negative", "A positive", "A negative", "B positive", "B negative", "AB positive", "AB negative"};

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);
                builder.setTitle("Choose Blood Group");
                builder.setCancelable(false);
                builder.setItems(bgList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 1:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 2:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 3:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 4:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 5:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 6:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 7:
                                blood_group_et.setText(bgList[position]);
                                text = bgList[position];
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
        donation_date_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateProfileActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                donation_date_et.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


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
                                String blood = blood_group_et.getText().toString();
                                String division = division_et.getText().toString();
                                String area = area_et.getText().toString();
                                String date = donation_date_et.getText().toString();

                                //validation

                                if (name.isEmpty()) {
                                    name_et.setError("Name can't be empty! ");
                                    name_et.requestFocus();

                                }
                                else if (password.isEmpty()) {
                                    password_et.setError("Password can't be empty! ");
                                    password_et.requestFocus();

                                }

                                else if (blood.isEmpty()) {
                                    blood_group_et.setError("Please select your blood group ! ");
                                    blood_group_et.requestFocus();
                                    Toasty.error(UpdateProfileActivity.this,"Please select your blood group ! ",Toasty.LENGTH_SHORT).show();
                                }
                                else if (division.isEmpty()) {
                                    division_et.setError("Division can't be empty! ");
                                    division_et.requestFocus();
                                    Toasty.error(UpdateProfileActivity.this,"Please select your division ! ",Toasty.LENGTH_SHORT).show();
                                }
                                else if (area.isEmpty()) {
                                    area_et.setError("Area can't be empty! ");
                                    area_et.requestFocus();
                                }
                                else if (date.isEmpty()) {
                                    donation_date_et.setError("Blood Donation Date can't be empty! ");
                                    donation_date_et.requestFocus();
                                    Toasty.error(UpdateProfileActivity.this,"Please select last blood donation date ! ",Toasty.LENGTH_SHORT).show();
                                }
                                else {
                                    //call login method
                                    updateProfile(name,cell,password, blood,division,area,date);
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
    public void getCtgAreaData() {
        Call<List<User>> call = apiInterface.getCtgArea();
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
    }*/
    private void updateProfile(String name,String cell,String password,String blood,String division,String area, String date) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<User> call = apiInterface.updateProfile(name,cell,password,blood,division,area,date);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(UpdateProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }



                else {
                    loading.dismiss();
                    Toasty.error(UpdateProfileActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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