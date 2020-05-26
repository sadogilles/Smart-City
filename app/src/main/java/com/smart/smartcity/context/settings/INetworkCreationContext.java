package com.smart.smartcity.context.settings;

import com.smart.smartcity.model.Network;

public interface INetworkCreationContext {
    void onNetworkCreationSuccessful(Network network);
    void onNetworkCreationFailure();
}
