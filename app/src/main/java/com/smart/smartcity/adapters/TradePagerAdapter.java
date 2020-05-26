package com.smart.smartcity.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.smart.smartcity.fragment.trades.NearTradesFragment;
import com.smart.smartcity.fragment.trades.OffersFragment;
import com.smart.smartcity.fragment.trades.TradeDirectoryFragment;

public class TradePagerAdapter extends FragmentPagerAdapter {
    public TradePagerAdapter(@NonNull FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return TradeDirectoryFragment.newInstance(-1);
            case 1:
                return NearTradesFragment.newInstance();
            case 2:
                return OffersFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Directory";
            case 1:
                return "Near trades";
            case 2:
                return "Offers";
            default:
                return null;
        }
    }
}
