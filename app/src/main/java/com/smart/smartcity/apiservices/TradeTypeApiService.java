package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.Interest;
import com.smart.smartcity.model.TradeType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TradeTypeApiService {
    @GET("api/tradetypes/{categoryId}")
    Call<List<TradeType>> getContent(@Path("categoryId") int categoryId);
}
