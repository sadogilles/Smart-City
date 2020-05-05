package com.smart.smartcity.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.smart.smartcity.AlarmSettingsFragment;
import com.smart.smartcity.R;
import com.smart.smartcity.fragment.InterestSettingsFragment;
import com.smart.smartcity.fragment.ProfileSettingsFragment;
import com.smart.smartcity.fragment.ServiceSettingsFragment;
import com.smart.smartcity.model.User;

public class SettingsPageAdapter extends FragmentPagerAdapter {
    private User user;

    public SettingsPageAdapter(@NonNull FragmentManager fm, User user) {
        super(fm);

        this.user = user;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return  ProfileSettingsFragment.newInstance(user);
            case 1:
                return InterestSettingsFragment.newInstance();
            case 2:
                return ServiceSettingsFragment.newInstance();
            case 3:
                return AlarmSettingsFragment.newInstance();
            default:
                return null;

        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch(position){
            case 0:
                return "Profile" ; //Resources.getSystem().getString(R.string.profile_settings); ->doesnot work
            case 1:
                return "Interest";
            case 2:
                return "Service";
            case 3:
                return "Alarm";
            default:
                return null;
        }
    }
}
