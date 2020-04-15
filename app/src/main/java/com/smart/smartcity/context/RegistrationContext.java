package com.smart.smartcity.context;

import com.smart.smartcity.model.User;

public interface RegistrationContext {
    void onSuccess(User user);
    void onRegistrationError();
}
