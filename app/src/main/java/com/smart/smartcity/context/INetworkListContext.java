package com.smart.smartcity.context;

import com.smart.smartcity.model.Network;

import java.util.List;

public interface INetworkListContext {
    void onGetNetworksSuccessful(List<Network> networks);
    void onGetNetworksFailure();
}
