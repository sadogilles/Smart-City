package com.smart.smartcity.context.authentication;

import android.content.Context;

import com.smart.smartcity.model.User;

public interface IAuthenticationContext {
    void onSuccess(User user);
    void onUserNotFound();
}
