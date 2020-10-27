package com.android.emergencymedicalsystem.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import es.dmoral.toasty.Toasty;

import com.android.emergencymedicalsystem.ConnectionDetector;
import com.android.emergencymedicalsystem.MyCustomPagerAdapter;
import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.user.ambulance.AmbulanceActivity;
import com.android.emergencymedicalsystem.user.bloodbank.BloodBankActivity;
import com.android.emergencymedicalsystem.user.blooddonor.BloodDonorActivity;
import com.android.emergencymedicalsystem.user.covid.CovidActivity;
import com.android.emergencymedicalsystem.user.hospital.HospitalActivity;
import com.android.emergencymedicalsystem.user.nurse.NurseActivity;
import com.android.emergencymedicalsystem.user.payment.PaymentActivity;
import com.android.emergencymedicalsystem.user.profile.ProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    int images[] = {R.drawable.imgone, R.drawable.imgtwo, R.drawable.imgthree};
    MyCustomPagerAdapter myCustomPagerAdapter;
    int currentPage = 0,NUM_PAGES=4;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 5000; // time in milliseconds between successive task executions.
    LinearLayout sliderDotspanel,nurseLayout,covidLayout,profileLayout,ambulanceLayout,logoutLayout,donorLayout,hospitalLayout,bloodBankLayout,paymentLayout;
    private int dotscount;
    Timer timer;
    private ImageView[] dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Emergency Medical Service");
        //Internet connection checker
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Toasty.error(MainActivity.this, "No Internet Connection", Toasty.LENGTH_LONG).show();
        }
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        nurseLayout=findViewById(R.id.nurseLayout);
        covidLayout=findViewById(R.id.covidLayout);
        profileLayout=findViewById(R.id.profile_layout);
        ambulanceLayout=findViewById(R.id.ambLayoutId);
        logoutLayout=findViewById(R.id.logoutLayoutId);
        donorLayout=findViewById(R.id.donorLayoutId);
        hospitalLayout=findViewById(R.id.hospitalLayoutId);
        bloodBankLayout=findViewById(R.id.bloodBankLayoutId);
        paymentLayout=findViewById(R.id.paymentLayoutId);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myCustomPagerAdapter = new MyCustomPagerAdapter(MainActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        dotscount = myCustomPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(MainActivity.this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        nurseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, NurseActivity.class);
                startActivity(intent);
            }
        });
        covidLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, CovidActivity.class);
                startActivity(intent);
            }
        });
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
        ambulanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, AmbulanceActivity.class);
                startActivity(intent);
            }
        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        donorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, BloodDonorActivity.class);
                startActivity(intent);
            }
        });
        hospitalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, HospitalActivity.class);
                startActivity(intent);
            }
        });
        bloodBankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, BloodBankActivity.class);
                startActivity(intent);
            }
        });
        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });
    }

}