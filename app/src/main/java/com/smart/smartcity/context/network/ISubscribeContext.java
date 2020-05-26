package com.smart.smartcity.context.network;

import com.smart.smartcity.model.Subscription;

public interface ISubscribeContext {
    void onSubscribeSuccessful(Subscription subscription);
    void onSubscribeFailure();
}
