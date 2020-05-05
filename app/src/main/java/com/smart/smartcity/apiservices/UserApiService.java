package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.User;
import com.smart.smartcity.protocol.AuthenticationData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiService {
    @POST("api/users")
    Call<User> register(@Body User user);

    @PUT("api/users/{id}")
    Call<User> update(@Path("id") int id, @Body User user);
}
