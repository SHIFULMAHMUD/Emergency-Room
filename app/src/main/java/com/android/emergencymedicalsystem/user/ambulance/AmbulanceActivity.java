package com.android.emergencymedicalsystem.user.ambulance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.adapter.AmbulanceAdapter;
import com.android.emergencymedicalsystem.adapter.RecyclerViewAdapter;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.SignupActivity;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AmbulanceAdapter adapter;
    EditText etxtDivision,etxtArea;
    private List<User> areaList;
    Button btnSearch;
    ArrayList<String> areaNames  = new ArrayList<String>();
    String text="";
    private List<Ambulance> ambulanceList;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    String getCell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hire Ambulance");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerone);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");

        etxtDivision = findViewById(R.id.editTextDivision);
        etxtArea = findViewById(R.id.editTextArea);
        btnSearch= findViewById(R.id.btn_search);
        etxtDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String[] divisionList = {"Dhaka", "Chittagong"};

                AlertDialog.Builder builder = new AlertDialog.Builder(AmbulanceActivity.this);
                builder.setTitle("Choose Division");
                builder.setCancelable(false);
                builder.setItems(divisionList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                getDhkAreaData();
                                break;

                            case 1:
                                etxtDivision.setText(divisionList[position]);
                                text = divisionList[position];
                                getCtgAreaData();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AmbulanceActivity.this);
                builder.setTitle("Choose Area");
                builder.setItems(areaNames.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        etxtArea.setText(areaNames.get(i));
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
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String div_search=etxtDivision.getText().toString().trim();
                String area_search=etxtArea.getText().toString().trim();

                if (div_search.isEmpty())
                {
                    Toasty.info(AmbulanceActivity.this, "Please choose division !", Toast.LENGTH_LONG).show();
                }
                else if (area_search.isEmpty())
                {
                    Toasty.info(AmbulanceActivity.this, "Please choose area !", Toast.LENGTH_LONG).show();
                }
                else
                {
                    getData(div_search,area_search);

                }

            }
        });
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(AmbulanceActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getAmbulance();
        }

    }
    public void getDhkAreaData() {
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
    }
    public void getData(String division,String area) {
        Call<List<Ambulance>> call = apiInterface.getSearchAmbulance(division,area);
        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                ambulanceList = response.body();
                adapter = new AmbulanceAdapter(AmbulanceActivity.this, ambulanceList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AmbulanceActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getAmbulance() {
        Call<List<Ambulance>> call = apiInterface.getAmbulance();
        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                ambulanceList = response.body();
                adapter = new AmbulanceAdapter(AmbulanceActivity.this, ambulanceList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AmbulanceActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
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