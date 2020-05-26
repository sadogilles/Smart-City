package com.smart.smartcity.dao;

import android.util.Log;

import com.smart.smartcity.apiservices.TradeApiService;
import com.smart.smartcity.context.trade.ITradeDetailsContext;
import com.smart.smartcity.model.Trade;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeDAO {
    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";
    private Retrofit retrofit;

    private ITradeDetailsContext tradeDetailsContext;

    public TradeDAO() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setTradeDetailsContext(ITradeDetailsContext tradeDetailsContext) {
        this.tradeDetailsContext = tradeDetailsContext;
    }

    public void findTrade(int id) {
        TradeApiService apiService = retrofit.create(TradeApiService.class);

        Call<Trade> call = apiService.findTrade(id);
        call.enqueue(new Callback<Trade>() {
            @Override
            public void onResponse(Call<Trade> call, Response<Trade> response) {
                if (response.isSuccessful()) {
                    tradeDetailsContext.onGetTradeSuccessful(response.body());
                } else {
                    tradeDetailsContext.onGetTradeFailure();
                }
            }

            @Override
            public void onFailure(Call<Trade> call, Throwable t) {
                Log.e("trades", "Error while sending request to trade API");
                Log.e("trade", t.getMessage());
                tradeDetailsContext.onGetTradeFailure();
            }
        });
    }
}
