package com.android.emergencymedicalsystem.driver;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverSignupActivity extends AppCompatActivity {
    EditText etxtName, etxtCell, etxtPassword,etxtType,etxtDivision,etxtArea,etxtFair,etxtVRegistrationNo,etxtFacility;
    Button btnSignUp;
    private List<User> areaList;
    ArrayList<String> areaNames  = new ArrayList<String>();
    private ApiInterface apiInterface;
    ImageView viewImage;
    String text,mediaPath,user_name,user_cell,user_password,user_type,user_division,user_area,user_fair,user_facility,user_registration,user_token,user_status;
    LinearLayout linearLayoutGotoLogin,linearLayoutAmbulancePic;
    private ProgressDialog loading;
    private static final String CHANNEL_ID = "ems_driver";
    private static final String CHANNEL_NAME= "Emergency Room Driver";
    private static final String CHANNEL_DESC = "Android Push Notification Tutorial";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_signup);
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

        linearLayoutGotoLogin=findViewById(R.id.ll11);
        linearLayoutGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DriverSignupActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(DriverSignupActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        etxtName = findViewById(R.id.editTextRegisterName);
        etxtCell = findViewById(R.id.editTextRegisterPhone);
        etxtPassword = findViewById(R.id.editTextRegisterPassword);
        etxtDivision = findViewById(R.id.editTextDivision);
        etxtArea = findViewById(R.id.editTextArea);
        etxtType = findViewById(R.id.editTextAmbulanceType);
        etxtFair = findViewById(R.id.editTextAmbulanceFair);
        etxtVRegistrationNo = findViewById(R.id.editTextVehicleRegistrationNo);
        etxtFacility = findViewById(R.id.editTextFacility);
        linearLayoutGotoLogin=findViewById(R.id.ll9);
        linearLayoutAmbulancePic=findViewById(R.id.ll12);
        viewImage=findViewById(R.id.viewImage);
        btnSignUp = findViewById(R.id.cirRegisterButton);
        etxtDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] divisionList = {"Dhaka", "Chittagong","Barishal","Mymensingh","Khulna","Rajshahi","Rangpur","Sylhet"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DriverSignupActivity.this);
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

        etxtType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] accountList = {"AC", "NON-AC","ICU"};

                AlertDialog.Builder builder = new AlertDialog.Builder(DriverSignupActivity.this);
                builder.setTitle("Choose Ambulance Type");
                builder.setCancelable(false);
                builder.setItems(accountList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtType.setText(accountList[position]);
                                text = accountList[position];
                                break;

                            case 1:
                                etxtType.setText(accountList[position]);
                                text = accountList[position];
                                break;
                            case 2:
                                etxtType.setText(accountList[position]);
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
        linearLayoutGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DriverSignupActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        linearLayoutAmbulancePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverSignupActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, 1213);

            }
        });
        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DriverSignupActivity.this, ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true); //default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true); //default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true); //default is true
                startActivityForResult(intent, 1213);
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
                    Toasty.error(DriverSignupActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
                }else {
                    //taking values
                    user_name = etxtName.getText().toString();
                    user_cell = etxtCell.getText().toString();
                    user_password = etxtPassword.getText().toString();
                    user_division = etxtDivision.getText().toString();
                    user_area = etxtArea.getText().toString();
                    user_type = etxtType.getText().toString();
                    user_fair = etxtFair.getText().toString();
                    user_facility = etxtFacility.getText().toString();
                    user_registration = etxtVRegistrationNo.getText().toString();
                    user_status = "Available";

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
                        Toasty.error(DriverSignupActivity.this, "Division can not be empty!", Toast.LENGTH_SHORT).show();
                    } else if (user_area.isEmpty()) {
                        etxtArea.setError("Area can not be empty! ");
                        etxtArea.requestFocus();
                    } else if (user_type.isEmpty()) {
                        etxtType.setError("Select Ambulance Type ! ");
                        etxtType.requestFocus();
                        Toasty.error(DriverSignupActivity.this, "Select Ambulance Type !", Toast.LENGTH_SHORT).show();
                    }
                    else if (user_fair.isEmpty()) {
                        etxtFair.setError("Please input ambulance fair ! ");
                        etxtFair.requestFocus();
                    }

                    else {
                        //call signup method
                        sign_up(user_name, user_cell, user_password, user_division, user_area, user_type,user_fair,user_registration,user_facility,user_status,user_token);
                    }
                }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            // When an Image is picked
            if (requestCode == 1213 && resultCode == RESULT_OK && null != data) {


                mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                Bitmap selectedImage = BitmapFactory.decodeFile(mediaPath);
                viewImage.setImageBitmap(selectedImage);

            }


        } catch (Exception e) {
            Toasty.error(this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
        }

    }

    //signup method
    private void sign_up(String name,String cell,String password,String division,String area,String type, String fair, String registration,String facility,String status,String token) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();
        File file = new File(mediaPath);

        // Parsing any Media type file
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        RequestBody p_name = RequestBody.create(MediaType.parse("text/plain"), user_name);
        RequestBody p_cell = RequestBody.create(MediaType.parse("text/plain"), user_cell);
        RequestBody p_password = RequestBody.create(MediaType.parse("text/plain"), user_password);
        RequestBody p_division = RequestBody.create(MediaType.parse("text/plain"), user_division);
        RequestBody p_area = RequestBody.create(MediaType.parse("text/plain"), user_area);
        RequestBody p_type = RequestBody.create(MediaType.parse("text/plain"),user_type);
        RequestBody p_fair = RequestBody.create(MediaType.parse("text/plain"),user_fair);
        RequestBody p_registration = RequestBody.create(MediaType.parse("text/plain"),user_registration);
        RequestBody p_facility = RequestBody.create(MediaType.parse("text/plain"),user_facility);
        RequestBody p_status = RequestBody.create(MediaType.parse("text/plain"),user_status);
        RequestBody p_token = RequestBody.create(MediaType.parse("text/plain"),user_token);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Ambulance> call = apiInterface.register(fileToUpload, filename,p_name, p_cell, p_password,p_division,p_area,p_type,p_fair,p_registration,p_facility,p_status,p_token);
        call.enqueue(new Callback<Ambulance>() {
            @Override
            public void onResponse(Call<Ambulance> call, Response<Ambulance> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    loading.dismiss();
                    Toasty.success(DriverSignupActivity.this, message, Toasty.LENGTH_SHORT).show();
                    Intent intent=new Intent(DriverSignupActivity.this, DriverLoginActivity.class);
                    startActivity(intent);

                }

                else if (value.equals("exists"))
                {
                    loading.dismiss();
                    Toasty.error(DriverSignupActivity.this, message, Toasty.LENGTH_SHORT).show();


                }


                else {
                    loading.dismiss();
                    Toasty.error(DriverSignupActivity.this, message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Ambulance> call, Throwable t) {

                loading.dismiss();
                Toasty.error(DriverSignupActivity.this, "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

}