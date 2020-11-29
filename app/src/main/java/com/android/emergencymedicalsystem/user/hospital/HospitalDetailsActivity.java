package com.android.emergencymedicalsystem.user.hospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.CovidTestCenter;
import com.android.emergencymedicalsystem.model.Hospital;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import java.util.List;

public class HospitalDetailsActivity extends AppCompatActivity {
    TextView name_tv,cell_tv,address_tv,website_tv,distance_tv;
    String id,name,cell,address,website,distance;
    private ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nearby Hospital");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        name_tv=findViewById(R.id.center_name_tv);
        cell_tv=findViewById(R.id.center_cell_tv);
        address_tv=findViewById(R.id.address_tv);
        website_tv=findViewById(R.id.web_tv);
        distance_tv=findViewById(R.id.distance_tv);

        id = getIntent().getStringExtra("id");
        String data[]=id.split(",");
        distance=data[1];
        distance_tv.setText(distance+" Km");
        Log.d("data",data[0]+ " KM="+data[1]);


        // Check if Internet present
        ConnectionDetector cd = new ConnectionDetector(HospitalDetailsActivity.this);
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(HospitalDetailsActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
            getHospitalData(data[0]);
        }
    }
    public void getHospitalData(String id) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Hospital>> call;
        call = apiInterface.getHospital(id);

        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(Call<List<Hospital>> call, Response<List<Hospital>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Hospital> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {


                        name = profileData.get(0).getName();
                        cell = profileData.get(0).getCell();
                        address = profileData.get(0).getAddress();
                        website = profileData.get(0).getWebsite();

                        name_tv.setText(name);
                        cell_tv.setText(cell);
                        address_tv.setText(address);
                        website_tv.setText(website);
                        distance_tv.setText(distance+" Km");

                    }

                }
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {

            }
        });
    }
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