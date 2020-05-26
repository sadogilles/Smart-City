package com.smart.smartcity.context.settings;

import com.smart.smartcity.model.User;

public interface IProfileUpdateContext {
    void onUpdateSuccessful(User user);
    void onUpdateFailure();
}
