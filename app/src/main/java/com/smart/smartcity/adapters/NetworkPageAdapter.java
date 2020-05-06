package com.smart.smartcity.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smart.smartcity.fragment.NetworkAvailableFragment;
import com.smart.smartcity.fragment.NetworkSubscriptionFragment;
import com.smart.smartcity.model.User;

public class NetworkPageAdapter extends FragmentPagerAdapter {

    private User user;
    public NetworkPageAdapter(@NonNull FragmentManager fm,User user) {
        super(fm);
        this.user = user;
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
