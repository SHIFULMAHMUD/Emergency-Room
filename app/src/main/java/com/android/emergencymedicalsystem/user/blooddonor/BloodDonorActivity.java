package com.android.emergencymedicalsystem.user.blooddonor;

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
import com.android.emergencymedicalsystem.adapter.BloodDonorAdapter;
import com.android.emergencymedicalsystem.adapter.RecyclerViewAdapter;
import com.android.emergencymedicalsystem.model.Ambulance;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.ambulance.AmbulanceActivity;

import java.util.ArrayList;
import java.util.List;

public class BloodDonorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BloodDonorAdapter adapter;
    private List<User> donorList;
    Button btnSearch;
    EditText txtSearch;
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Blood Donors");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(BloodDonorActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        btnSearch=findViewById(R.id.btn_search);
        txtSearch=findViewById(R.id.txt_search);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(BloodDonorActivity.this);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(BloodDonorActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        else {
            getDonorData();
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search=txtSearch.getText().toString().trim();

                if (search.isEmpty())
                {
                    Toasty.info(BloodDonorActivity.this, "Please enter division name!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    getData(search);

                }

            }
        });


    }
    public void getDonorData() {
        Call<List<User>> call = apiInterface.getDonor();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                donorList = response.body();
                adapter = new BloodDonorAdapter(BloodDonorActivity.this, donorList);
                recyclerView.setLayoutManager(new GridLayoutManager(BloodDonorActivity.this,1));
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(BloodDonorActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getData(String text) {
        Call<List<User>> call = apiInterface.getSearchDonor(text);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                progressBar.setVisibility(View.INVISIBLE);
                donorList = response.body();
                adapter = new BloodDonorAdapter(BloodDonorActivity.this, donorList);
                recyclerView.setLayoutManager(new GridLayoutManager(BloodDonorActivity.this,1));
                recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(BloodDonorActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
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
