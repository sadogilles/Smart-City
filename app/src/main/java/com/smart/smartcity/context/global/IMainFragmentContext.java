package com.smart.smartcity.context.global;

import com.smart.smartcity.model.Network;

public interface IMainFragmentContext {
    void showNewsFragment(boolean initial);
    void showTradeFragment();
    void showNetworkFragment();
    void configureAndShowSettingsFragment();
    void showNetworkDetailsFragment(Network network);
    void showTrafficFragment();
    void showWeatherFragment();
    void showNetworkAdministrationFragment(Network network);
    void showTradeDetailsFragment(int tradeId);
}
