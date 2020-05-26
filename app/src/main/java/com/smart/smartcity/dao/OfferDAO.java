package com.smart.smartcity.dao;

import android.util.Log;

import com.smart.smartcity.apiservices.OfferApiService;
import com.smart.smartcity.context.trade.IOfferListContext;
import com.smart.smartcity.context.trade.ITradeDetailsContext;
import com.smart.smartcity.model.Offer;
import com.smart.smartcity.model.Trade;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfferDAO {
    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";
    private Retrofit retrofit;

    private IOfferListContext offerListContext;

    public OfferDAO() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setOfferListContext(IOfferListContext offerListContext) {
        this.offerListContext = offerListContext;
    }

    public void findOffersByTrade(Trade trade) {
        OfferApiService apiService = retrofit.create(OfferApiService.class);

        Call<List<Offer>> call = apiService.findOffersByTrade(trade.getId());
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful()) {
                    offerListContext.onGetOffersSuccessful(response.body());
                } else {
                    offerListContext.onGetOffersFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Log.e("offers", "Error while sending request to offers API");
                Log.e("offers", t.getMessage());

                offerListContext.onGetOffersFailure();
            }
        });
    }
}
