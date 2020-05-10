package com.smart.smartcity.context;

import com.smart.smartcity.model.Subscription;

public interface IAcceptSubscriptionContext {
    void onAcceptSubscriptionSuccessful(Subscription subscription);
    void onAcceptSubscriptionError();
}
