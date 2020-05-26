package com.smart.smartcity.context.services;

import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.User;

import java.util.List;

public interface IServiceListContext {
    void onSuccess(List<Service> services);
    void onGetServicesFailed();
}
