package com.smart.smartcity.context;

import com.smart.smartcity.model.Network;

public interface INetworkCreationContext {
    void onNetworkCreationSuccessful(Network network);
    void onNetworkCreationFailure();
}
