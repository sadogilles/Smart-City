package com.smart.smartcity.context.network;

import com.smart.smartcity.model.Network;

import java.util.List;

public interface INetworkListContext {
    void onGetNetworksSuccessful(List<Network> networks);
    void onGetNetworksFailure();
}
