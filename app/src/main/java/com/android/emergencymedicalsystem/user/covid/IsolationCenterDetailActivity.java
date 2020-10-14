package com.android.emergencymedicalsystem.user.covid;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.CovidTestCenter;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IsolationCenterDetailActivity extends AppCompatActivity {
    TextView name_tv,cell_tv,address_tv,facility_tv;
    String id,name,cell,address,facility;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isolation_center_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Isolation Center");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        name_tv=findViewById(R.id.center_name_tv);
        cell_tv=findViewById(R.id.center_cell_tv);
        address_tv=findViewById(R.id.address_tv);
        facility_tv=findViewById(R.id.facility_tv);
        id = getIntent().getStringExtra("id");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(IsolationCenterDetailActivity.this);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(IsolationCenterDetailActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }else {
        getCovidCenterData(id);}
    }
    public void getCovidCenterData(String id) {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CovidTestCenter>> call;
        call = apiInterface.getIsolationCenter(id);

        call.enqueue(new Callback<List<CovidTestCenter>>() {
            @Override
            public void onResponse(Call<List<CovidTestCenter>> call, Response<List<CovidTestCenter>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<CovidTestCenter> profileData;
                    profileData = response.body();

                    if (profileData.isEmpty()) {

                    } else {


                        name = profileData.get(0).getName();
                        cell = profileData.get(0).getCell();
                        address = profileData.get(0).getAddress();
                        facility = profileData.get(0).getFacility();

                        name_tv.setText(name);
                        cell_tv.setText(cell);
                        address_tv.setText(address);
                        facility_tv.setText(facility);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<CovidTestCenter>> call, Throwable t) {

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