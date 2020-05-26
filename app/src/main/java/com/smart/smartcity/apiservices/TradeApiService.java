package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Trade;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TradeApiService {
    @GET("api/trades/{id}")
    Call<Trade> findTrade(@Path("id") int id);
}
