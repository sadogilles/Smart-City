package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.Service;
import com.smart.smartcity.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceApiService {
    @GET("api/services/user/{userId}")
    Call<List<Service>> findServicesByUserId(@Path("userId") int userId);

    @GET("api/services")
    Call<List<Service>> findServices();
}
