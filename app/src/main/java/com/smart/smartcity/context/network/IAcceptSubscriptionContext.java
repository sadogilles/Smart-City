package com.smart.smartcity.context.network;

import com.smart.smartcity.model.Subscription;

public interface IAcceptSubscriptionContext {
    void onAcceptSubscriptionSuccessful(Subscription subscription);
    void onAcceptSubscriptionError();
}
