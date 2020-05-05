package com.smart.smartcity.context;

import com.smart.smartcity.model.User;

public interface IProfileUpdateContext {
    void onUpdateSuccessful(User user);
    void onUpdateFailure();
}
