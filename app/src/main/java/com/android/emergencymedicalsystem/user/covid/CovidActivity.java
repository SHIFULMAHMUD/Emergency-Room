package com.android.emergencymedicalsystem.user.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.emergencymedicalsystem.R;
import com.android.emergencymedicalsystem.adapter.MyPagerAdapter;
import com.android.emergencymedicalsystem.user.nurse.CovidNurseFragment;
import com.android.emergencymedicalsystem.user.nurse.GeneralNurseFragment;
import com.google.android.material.tabs.TabLayout;

public class CovidActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("COVID-19 Services");
        getSupportActionBar().setHomeButtonEnabled(true); //for back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//for back button
        viewPager = findViewById(R.id.simpleViewPager);
        tabLayout = findViewById(R.id.simpleTabLayout);

        setupViewPager();

    }
    private void setupViewPager() {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CovidTestCenterFragment()); //index 0
        adapter.addFragment(new SampleCollectionFragment()); //index 1
        adapter.addFragment(new IsolationCenterFragment()); //index 2
        adapter.addFragment(new CovidResultFragment()); //index 3
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.getTabAt(0).setText("Nearby COVID Test Center");
        tabLayout.getTabAt(1).setText("Sample Collection");
        tabLayout.getTabAt(2).setText("Nearby Isolation Center");
        tabLayout.getTabAt(3).setText("COVID Test Result");
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