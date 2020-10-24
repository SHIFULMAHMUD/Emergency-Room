package com.android.emergencymedicalsystem.user.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.Constant;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.model.CovidTestCenter;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.hospital.HospitalActivity;
import com.android.emergencymedicalsystem.user.hospital.HospitalDetailsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class BloodBankActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    int MAX_SIZE = 999;
    String getCell, userLatitude, userLongitude;
    SharedPreferences sharedPreferences;
    public double userLat;
    public double userLong;
    public String centerId[] = new String[MAX_SIZE];
    public String centerName[] = new String[MAX_SIZE];
    public String centerCell[] = new String[MAX_SIZE];
    public String centerAddress[] = new String[MAX_SIZE];
    public double centerLatitude[] = new double[MAX_SIZE];
    public double centerLongitude[] = new double[MAX_SIZE];
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_bank);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nearby Blood Banks");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button

//Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getCell = sharedPreferences.getString(Constant.CELL_SHARED_PREF, "Not Available");
//Fetching latitude, longitude from shared preferences
        userLatitude = sharedPreferences.getString(Constant.LATITUDE_SHARED_PREF,"22.3237516");
        userLongitude = sharedPreferences.getString(Constant.LONGITUDE_SHARED_PREF, "91.8091193");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(BloodBankActivity.this);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(BloodBankActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        } else {
            getData();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getData();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                String id = marker.getSnippet();
                Intent intent = new Intent(BloodBankActivity.this, BloodBankDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            }
        });

    }
    public void getData(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CovidTestCenter>> call;
        call = apiInterface.getBloodBank();

        call.enqueue(new Callback<List<CovidTestCenter>>() {
            @Override
            public void onResponse(Call<List<CovidTestCenter>> call, Response<List<CovidTestCenter>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<CovidTestCenter> latlngData;
                    latlngData = response.body();

                    if (latlngData.isEmpty()) {

                    } else {

                        for (int i = 0; i < latlngData.size(); i++) {
                            final String center_id = latlngData.get(i).getId();
                            final String center_name = latlngData.get(i).getName();
                            final String center_cell = latlngData.get(i).getCell();
                            final String center_address = latlngData.get(i).getAddress();
                            final String center_latitude = latlngData.get(i).getLatitude();
                            final String center_longitude = latlngData.get(i).getLongitude();

                            if (!center_latitude.trim().equals("null")) {
                                centerLatitude[i] = Double.parseDouble(center_latitude);
                            }
                            if (!center_longitude.trim().equals("null")) {
                                centerLongitude[i] = Double.parseDouble(center_longitude);
                            }
                            //insert data into array for put extra
                            centerId[i] = center_id;
                            centerName[i] = center_name;
                            centerCell[i] = center_cell;
                            centerAddress[i] = center_address;
                            userLat = Double.parseDouble(userLatitude);
                            userLong = Double.parseDouble(userLongitude);
                            double startLatitude = userLat;
                            double startLongitude = userLong;
                            double endLatitude = centerLatitude[i];
                            double endLongitude = centerLongitude[i];

                            //calculate distance //
                            float[] results = new float[1];
                            Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
                            float distance = results[0];
                            int kilometer = (int) (distance / 1000);

                            if (kilometer <= 20) {
                                LatLng sydney = new LatLng(endLatitude, endLongitude);
                                mMap.addMarker(new MarkerOptions().position(sydney).title(centerName[i]).snippet(centerId[i]));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(endLatitude, endLongitude), 15.0f));
                            }

                        }
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