package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.Interest;
import com.smart.smartcity.model.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface InterestApiService {
    @GET("api/interests")
    Call<List<Interest>> findInterests();

    @GET("api/interests/{id}")
    Call<List<Interest>> findServicesByUserId(@Path("id") int id);
}
