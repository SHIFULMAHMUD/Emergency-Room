package com.android.emergencymedicalsystem.user.covid;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.adapter.RecyclerViewAdapter;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.model.Sample;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.SignupActivity;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SampleCollectionFragment extends Fragment {

    public SampleCollectionFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sample_collection, container, false);
        final EditText name_et=view.findViewById(R.id.editTextName);
        final EditText phone_et=view.findViewById(R.id.editTextPhone);
        final EditText address_et=view.findViewById(R.id.editTextAddress);
        final EditText bg_et=view.findViewById(R.id.editTextBloodGroup);
        final EditText gender_et=view.findViewById(R.id.editTextGender);
        final EditText age_et=view.findViewById(R.id.editTextAge);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =this.getActivity().getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
        phone_et.setText(getCell);
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(getContext(), "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        Button submit_btn=view.findViewById(R.id.cirSubmitButton);
        bg_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] bgList = {"O positive", "O negative", "A positive", "A negative", "B positive", "B negative", "AB positive", "AB negative"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Blood Group");
                builder.setCancelable(false);
                builder.setItems(bgList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                bg_et.setText(bgList[position]);
                                String text = bgList[position];
                                break;

                            case 1:
                                bg_et.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 2:
                                bg_et.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 3:
                                bg_et.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 4:
                                bg_et.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 5:
                                bg_et.setText(bgList[position]);
                                text = bgList[position];
                                break;
                            case 6:
                                bg_et.setText(bgList[position]);
                                text = bgList[position];
                                break;

                            case 7:
                                bg_et.setText(bgList[position]);
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
        gender_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] genderList = {"Male", "Female"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Gender");
                builder.setCancelable(false);
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                gender_et.setText(genderList[position]);
                                String text = genderList[position];
                                break;

                            case 1:
                                gender_et.setText(genderList[position]);
                                text = genderList[position];
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
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] taskList = {"Yes", "No"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirm Submission?");
                builder.setCancelable(false);
                builder.setItems(taskList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                String name = name_et.getText().toString();
                                String cell = phone_et.getText().toString();
                                String address = address_et.getText().toString();
                                String blood_group = bg_et.getText().toString();
                                String gender = gender_et.getText().toString();
                                String age = age_et.getText().toString();
                                String status = "Pending";

                                //validation

                                if (name.isEmpty()) {
                                    name_et.setError("Name can't be empty ! ");
                                    name_et.requestFocus();

                                }
                                else if (cell.length() != 11 || !cell.startsWith("01")) {
                                    phone_et.setError("Invalid cell !");
                                    phone_et.requestFocus();

                                }
                                else if (address.isEmpty()) {
                                    Toasty.error(getActivity(), "Address can't be empty !", Toast.LENGTH_SHORT).show();
                                }
                                else if (blood_group.isEmpty()) {
                                    bg_et.setError("Blood Group can't be empty ! ");
                                    Toasty.error(getActivity(), "Please select blood group !", Toast.LENGTH_SHORT).show();
                                }
                                else if (gender.isEmpty()) {
                                    gender_et.setError("Gender can't be empty ! ");
                                    Toasty.error(getActivity(), "Please select gender !", Toast.LENGTH_SHORT).show();
                                }
                                else if (age.isEmpty()) {
                                    age_et.setError("Age can't be empty ! ");
                                    age_et.requestFocus();

                                }
                                else {
                                    //call method
                                    sampleCollection(name,cell, address,blood_group,gender,age,status);
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


        return view;
    }
    private void sampleCollection(String name,String cell,String address,String blood, String gender, String age, String status) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Sample> call = apiInterface.collectSample(name,cell,address,blood,gender,age,status);
        call.enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("success"))
                {
                    Toasty.success(getContext(), message, Toasty.LENGTH_SHORT).show();
                    /*Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();*/
                }



                else {
                    Toasty.error(getContext(), message, Toasty.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

                Toasty.error(getContext(), "Error! " + t.toString(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

}
