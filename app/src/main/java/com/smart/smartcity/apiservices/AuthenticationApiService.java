package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.User;
import com.smart.smartcity.protocol.AuthenticationData;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApiService {
    @POST("api/authentication")
    Call<User> authentify(@Body AuthenticationData authenticationData);
}
