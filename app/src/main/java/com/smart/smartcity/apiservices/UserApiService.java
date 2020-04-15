package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.User;
import com.smart.smartcity.protocol.AuthenticationData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("api/users")
    Call<User> register(@Body User user);
}
