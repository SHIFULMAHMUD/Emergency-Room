package com.android.emergencymedicalsystem.user.ambulance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.android.emergencymedicalsystem.model.CovidTestCenter;
import com.android.emergencymedicalsystem.model.Nurse;
import com.android.emergencymedicalsystem.model.User;
import com.android.emergencymedicalsystem.remote.ApiClient;
import com.android.emergencymedicalsystem.remote.ApiInterface;
import com.android.emergencymedicalsystem.user.SignupActivity;
import com.android.emergencymedicalsystem.user.bloodbank.BloodBankActivity;
import com.android.emergencymedicalsystem.user.bloodbank.BloodBankDetailsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class AmbulanceActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    int MAX_SIZE = 999;
    String getCell, userLatitude, userLongitude;
    SharedPreferences sharedPreferences;
    public double userLat;
    public double userLong;
    public String kilo[] = new String[MAX_SIZE];
    public String ambId[] = new String[MAX_SIZE];
    public String ambName[] = new String[MAX_SIZE];
    public String ambCell[] = new String[MAX_SIZE];
    public String ambType[] = new String[MAX_SIZE];
    public String ambFair[] = new String[MAX_SIZE];
    public String ambRegNo[] = new String[MAX_SIZE];
    public String ambImage[] = new String[MAX_SIZE];
    public String ambFacility[] = new String[MAX_SIZE];
    public String ambDiv[] = new String[MAX_SIZE];
    public String ambArea[] = new String[MAX_SIZE];
    public double centerLatitude[] = new double[MAX_SIZE];
    public double centerLongitude[] = new double[MAX_SIZE];
    private ApiInterface apiInterface;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance);
        progressBar = findViewById(R.id.progressBar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nearby Ambulance");
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
        ConnectionDetector cd = new ConnectionDetector(AmbulanceActivity.this);
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(AmbulanceActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        } else {
            getData();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
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
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        getData();
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                String id = marker.getSnippet();
                Intent intent = new Intent(AmbulanceActivity.this, AmbulanceDetailsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
                return true;
            }
        });

    }
    public void getData(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Ambulance>> call;
        call = apiInterface.getAmbulance();

        call.enqueue(new Callback<List<Ambulance>>() {
            @Override
            public void onResponse(Call<List<Ambulance>> call, Response<List<Ambulance>> response) {


                if (response.isSuccessful() && response.body() != null) {

                    List<Ambulance> ambData;
                    ambData = response.body();

                    if (ambData.isEmpty()) {

                    } else {

                        for (int i = 0; i < ambData.size(); i++) {
                            final String center_id = ambData.get(i).getId();
                            final String center_name = ambData.get(i).getName();
                            final String center_cell = ambData.get(i).getCell();
                            final String center_type = ambData.get(i).getType();
                            final String center_fair = ambData.get(i).getFair();
                            final String center_reg_no = ambData.get(i).getV_reg_no();
                            final String center_image = ambData.get(i).getImage();
                            final String center_facility = ambData.get(i).getFacility();
                            final String center_div = ambData.get(i).getDivision();
                            final String center_area = ambData.get(i).getArea();
                            final String center_latitude = ambData.get(i).getLatitude();
                            final String center_longitude = ambData.get(i).getLongitude();

                            if (!center_latitude.trim().equals("null")) {
                                centerLatitude[i] = Double.parseDouble(center_latitude);
                            }
                            if (!center_longitude.trim().equals("null")) {
                                centerLongitude[i] = Double.parseDouble(center_longitude);
                            }
                            //insert data into array for put extra
                            ambId[i] = center_id;
                            ambName[i] = center_name;
                            ambCell[i] = center_cell;
                            ambType[i] = center_type;
                            ambFair[i] = center_fair;
                            ambRegNo[i] = center_reg_no;
                            ambImage[i] = center_image;
                            ambFacility[i] = center_facility;
                            ambDiv[i] = center_div;
                            ambArea[i] = center_area;
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
                            kilo[i]=""+kilometer;
                            if (kilometer <= 20) {
                                LatLng sydney = new LatLng(endLatitude, endLongitude);
                                mMap.addMarker(new MarkerOptions().position(sydney).title(ambName[i]).snippet(ambId[i]+","+kilo[i]));
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(endLatitude, endLongitude), 15.0f));
                            }

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Ambulance>> call, Throwable t) {

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