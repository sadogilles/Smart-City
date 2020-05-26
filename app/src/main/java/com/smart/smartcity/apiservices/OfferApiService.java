package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Offer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OfferApiService {
    @GET("api/offers/{tradeId}")
    Call<List<Offer>> findOffersByTrade(@Path("tradeId") int tradeId);
}
