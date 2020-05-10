package com.smart.smartcity.context;

import com.smart.smartcity.model.Subscription;

public interface ISubscribeContext {
    void onSubscribeSuccessful(Subscription subscription);
    void onSubscribeFailure();
}
