package com.smart.smartcity.context;

import android.content.Context;

import com.smart.smartcity.model.User;

public interface AuthenticationContext {
    void onSuccess(User user);
    void onUserNotFound();
}
