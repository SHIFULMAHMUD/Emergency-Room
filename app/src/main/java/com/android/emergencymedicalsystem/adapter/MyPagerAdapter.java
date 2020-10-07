package com.android.emergencymedicalsystem.adapter;

import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Class that stores fragments for tabs
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MyPagerAdapter";

    private final List<Fragment> mFragmentList = new ArrayList<>();


    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }

}