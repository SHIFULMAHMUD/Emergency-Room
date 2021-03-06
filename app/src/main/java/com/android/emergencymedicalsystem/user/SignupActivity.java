package com.android.emergencymedicalsystem.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.adapter.RecyclerViewAdapter;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.nurse.NurseActivity;
import com.android.emergencymedicalsystem.user.payment.SamplePaymentActivity;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {
    LinearLayout linearLayoutGotoLogin;
    private List<User> areaList;
    public Calendar myCalendar = Calendar.getInstance();
    public DatePickerDialog.OnDateSetListener appointment_date;
    private int mYear, mMonth, mDay;
    ArrayList<String> areaNames  = new ArrayList<String>();
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    EditText etxtName, etxtCell, etxtPassword, etxtDivision, etxtArea, etxtBloodGroup,etxtBloodDonationDate;
    Button btnSignUp;
    private ApiInterface apiInterface;
    String text="", user_name, user_cell, user_password, user_division, user_area, user_bg, lat, lng,user_token,user_donation_date;
    private ProgressDialog loading;
    private static final String CHANNEL_ID = "emergency_medical_system";
    private static final String CHANNEL_NAME= "Emergency Medical System";
    private static final String CHANNEL_DESC = "Android Push Notification Tutorial";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Account");
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            user_token =task.getResult().getToken();
                        }
                    }
                });
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SignupActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            getLocation();
        }
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        linearLayoutGotoLogin = findViewById(R.id.ll9);
        linearLayoutGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        etxtName = findViewById(R.id.editTextRegisterName);
        etxtName.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
        etxtCell = findViewById(R.id.editTextRegisterPhone);
        etxtPassword = findViewById(R.id.editTextRegisterPassword);
        etxtDivision = findViewById(R.id.editTextRegisterDivision);
        etxtArea = findViewById(R.id.editTextRegisterArea);
        etxtBloodGroup = findViewById(R.id.editTextBloodGroup);
        etxtBloodDonationDate = findViewById(R.id.editTextBloodDonationDate);
        linearLayoutGotoLogin = findViewById(R.id.ll9);
        btnSignUp = findViewById(R.id.cirRegisterButton);
        etxtDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] divisionList = {"Dhaka", "Chittagong","Barishal","Mymensingh","Khulna","Rajshahi","Rangpur","Sylhet"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle("Choose Division");
                builder.setCancelable(false);
                builder.setItems(divisionList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 1:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                break;
                            case 2:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 3:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                break;
                            case 4:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 5:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                break;
                            case 6:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                break;

                            case 7:
                                etxtDivision.setText(divisionList[position]);
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

        etxtBloodGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] bgList = {"O positive", "O negative", "A positive", "A negative", "B positive", "B negative", "AB positive", "AB negative"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle("Choose Blood Group");
                builder.setCancelable(false);
                builder.setItems(bgList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtBloodGroup.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 1:
                                etxtBloodGroup.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 2:
                                etxtBloodGroup.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 3:
                                etxtBloodGroup.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 4:
                                etxtBloodGroup.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 5:
                                etxtBloodGroup.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 6:
                                etxtBloodGroup.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 7:
                                etxtBloodGroup.setText(bgList[position]);
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
        appointment_date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etxtBloodDonationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                etxtBloodDonationDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();


    }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Internet connection checker
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                // Check if Internet present
                if (!cd.isConnectingToInternet()) {
                    // Internet Connection is not present
                    Toasty.error(SignupActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
                }else {
                //taking values
                user_name = etxtName.getText().toString();
                user_cell = etxtCell.getText().toString();
                user_password = etxtPassword.getText().toString();
                user_division = etxtDivision.getText().toString();
                user_area = etxtArea.getText().toString();
                user_bg = etxtBloodGroup.getText().toString();
                user_donation_date = etxtBloodDonationDate.getText().toString();

                //validation
                if (user_name.isEmpty()) {
                    etxtName.setError("Name can not be empty! ");
                    etxtName.requestFocus();

                } else if (user_cell.length() != 11 || !user_cell.startsWith("01")) {
                    etxtCell.setError("Invalid cell!");
                    etxtCell.requestFocus();

                } else if (user_password.isEmpty()) {
                    etxtPassword.setError("Password can not be empty! ");
                    etxtPassword.requestFocus();

                } else if ((user_password.length() < 4)) {
                    etxtPassword.setError("Password should be more than 3 characters!");
                    etxtPassword.requestFocus();

                } else if (user_division.isEmpty()) {
                    etxtDivision.setError("Division can not be empty! ");
                    etxtDivision.requestFocus();
                    Toasty.error(SignupActivity.this, "Please select Division !", Toast.LENGTH_SHORT).show();
                } else if (user_area.isEmpty()) {
                    etxtArea.setError("Area can not be empty! ");
                    etxtArea.requestFocus();
                } else if (user_bg.isEmpty()) {
                    etxtBloodGroup.setError("Blood Group can't be empty! ");
                    etxtBloodGroup.requestFocus();
                    Toasty.error(SignupActivity.this, "Blood Group can't be empty! ", Toast.LENGTH_SHORT).show();
                }
                else if (user_donation_date.isEmpty()) {
                    etxtBloodDonationDate.setError("Blood Donation Date can't be empty! ");
                    etxtBloodDonationDate.requestFocus();
                    Toasty.error(SignupActivity.this, "Blood Donation Date can't be empty! ", Toast.LENGTH_SHORT).show();
                }
                //Runtime permissions
                else {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SignupActivity.this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, REQUEST_CODE_LOCATION_PERMISSION);
                    } else {
                        getLocation();
                    }

                    //call signup method
                    sign_up(user_name, user_cell, user_password, user_division, user_area, user_bg,user_donation_date, lat, lng,user_token);
                }
            }
            }
        });

    }
    //for date input
    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etxtBloodDonationDate.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toasty.error(this, "Permission Denied!", Toasty.LENGTH_SHORT).show();
            }
        }
    }

    private void getLocation() {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(SignupActivity.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(SignupActivity.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    lat = String.valueOf(latitude);
                    lng = String.valueOf(longitude);
                }
            }
        }, Looper.getMainLooper());
    }

    //signup method
    private void sign_up(String name,String cell,String password,String division,String area,String blood_group,String donation_date,String latitude,String longitude,String token) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.signUp(name, cell,password, division, area,blood_group,donation_date,latitude,longitude,token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(SignupActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);

                }

                else if (value.equals("exists"))
                {
                    loading.dismiss();
                    Toasty.error(SignupActivity.this, message, Toasty.LENGTH_SHORT).show();


                }


                else {
                    loading.dismiss();
                    Toasty.error(SignupActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                loading.dismiss();
                Toasty.error(SignupActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

}