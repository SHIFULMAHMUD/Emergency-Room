package com.android.emergencymedicalsystem.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SignupActivity extends AppCompatActivity {
    LinearLayout linearLayoutGotoLogin;
    EditText etxtName, etxtCell, etxtPassword,etxtDivision,etxtArea,etxtBloodGroup;
    Button btnSignUp;
    String text,user_name,user_cell,user_password,user_division,user_area,user_bg;
    private ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Account");
        linearLayoutGotoLogin=findViewById(R.id.ll9);
        linearLayoutGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        etxtName = findViewById(R.id.editTextRegisterName);
        etxtCell = findViewById(R.id.editTextRegisterPhone);
        etxtPassword = findViewById(R.id.editTextRegisterPassword);
        etxtDivision = findViewById(R.id.editTextRegisterDivision);
        etxtArea = findViewById(R.id.editTextRegisterArea);
        etxtBloodGroup = findViewById(R.id.editTextBloodGroup);
        linearLayoutGotoLogin=findViewById(R.id.ll9);
        btnSignUp = findViewById(R.id.cirRegisterButton);
        etxtDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] divisionList = {"Dhaka", "Chittagong"};

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
        etxtArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] areaList = {"Agrabad", "Gec Circle"};

                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                builder.setTitle("Choose Area");
                builder.setCancelable(false);
                builder.setItems(areaList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtArea.setText(areaList[position]);
                                text = areaList[position];
                                break;

                            case 1:
                                etxtArea.setText(areaList[position]);
                                text = areaList[position];
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

                final String[] bgList = {"O positive", "O negative","A positive", "A negative","B positive", "B negative","AB positive", "AB negative"};

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


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //taking values
                user_name = etxtName.getText().toString();
                user_cell = etxtCell.getText().toString();
                user_password = etxtPassword.getText().toString();
                user_division = etxtDivision.getText().toString();
                user_area = etxtArea.getText().toString();
                user_bg = etxtBloodGroup.getText().toString();


                //validation
                if (user_name.isEmpty())
                {
                    etxtName.setError("Name can not be empty! ");
                    etxtName.requestFocus();

                }

                else if (user_cell.length()!=11 || !user_cell.startsWith("01"))
                {
                    etxtCell.setError("Invalid cell!");
                    etxtCell.requestFocus();

                }


                else if (user_password.isEmpty())
                {
                    etxtPassword.setError("Password can not be empty! ");
                    etxtPassword.requestFocus();

                }else if ((user_password.length() < 4)) {
                    etxtPassword.setError("Password should be more than 3 characters!");
                    etxtPassword.requestFocus();

                }
                else if (user_division.isEmpty())
                {
                    etxtDivision.setError("Division can not be empty! ");
                    etxtDivision.requestFocus();
                    Toasty.error(SignupActivity.this, "Please select Division !", Toast.LENGTH_SHORT).show();
                }
                else if (user_area.isEmpty())
                {
                    etxtArea.setError("Please select area ! ");
                    etxtArea.requestFocus();
                    Toasty.error(SignupActivity.this, "Please select Area !", Toast.LENGTH_SHORT).show();
                }
                else if (user_bg.isEmpty())
                {
                    etxtBloodGroup.setError("Blood Group can't be empty! ");
                    etxtBloodGroup.requestFocus();
                }
                else
                {
                    //call signup method
                    sign_up(user_name,user_cell,user_password,user_division,user_area,user_bg);
                }

            }
        });


    }

    //signup method
    private void sign_up(String name,String cell,String password,String division,String area,String blood_group) {

        loading=new ProgressDialog(this);
        loading.setMessage("Please wait....");
        loading.show();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<User> call = apiInterface.signUp(name, cell,password, division, area,blood_group);
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