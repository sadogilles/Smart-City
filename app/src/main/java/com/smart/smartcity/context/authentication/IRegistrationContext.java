package com.smart.smartcity.context.authentication;

import com.smart.smartcity.model.User;

public interface IRegistrationContext {
    void onSuccess(User user);
    void onRegistrationError();
}
