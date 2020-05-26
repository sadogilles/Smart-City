package com.smart.smartcity.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smart.smartcity.fragment.networks.NetworkAvailableFragment;
import com.smart.smartcity.fragment.networks.NetworkSubscriptionFragment;

public class NetworkPageAdapter extends FragmentPagerAdapter {
    public NetworkPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 :
                return NetworkAvailableFragment.newInstance();
            case 1:
                return NetworkSubscriptionFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return  "Networks";
            case 1:
                return "Subscription";
            default:
                return null;
        }

    }
}
