package com.smart.smartcity.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smart.smartcity.fragment.settings.AlarmSettingsFragment;
import com.smart.smartcity.fragment.settings.CreateNetworkSettingsFragment;
import com.smart.smartcity.fragment.settings.InterestSettingsFragment;
import com.smart.smartcity.fragment.settings.ProfileSettingsFragment;
import com.smart.smartcity.fragment.settings.ServiceSettingsFragment;

public class SettingsPageAdapter extends FragmentPagerAdapter {
    public SettingsPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return  ProfileSettingsFragment.newInstance();
            case 1:
                return InterestSettingsFragment.newInstance();
            case 2:
                return ServiceSettingsFragment.newInstance();
            case 3:
                return AlarmSettingsFragment.newInstance();
            case 4:
                return CreateNetworkSettingsFragment.newInstance();
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
            case 4:
                return "Network";
            default:
                return null;
        }
    }
}
