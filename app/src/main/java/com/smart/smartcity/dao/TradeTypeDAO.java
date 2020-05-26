package com.smart.smartcity.dao;

import android.util.Log;

import com.smart.smartcity.apiservices.TradeTypeApiService;
import com.smart.smartcity.context.trade.ITradeTypeListContext;
import com.smart.smartcity.model.TradeType;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TradeTypeDAO {
    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";

    private Retrofit retrofit;

    private ITradeTypeListContext tradeTypeListContext;

    public TradeTypeDAO() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setTradeTypeListContext(ITradeTypeListContext tradeTypeListContext) {
        this.tradeTypeListContext = tradeTypeListContext;
    }

    public void getContent(int categoryId) {
        TradeTypeApiService apiService = retrofit.create(TradeTypeApiService.class);

        Call<List<TradeType>> call = apiService.getContent(categoryId);
        call.enqueue(new Callback<List<TradeType>>() {
            @Override
            public void onResponse(Call<List<TradeType>> call, Response<List<TradeType>> response) {
                if (response.isSuccessful()) {
                    tradeTypeListContext.onGetContentSuccessful(response.body());
                } else {
                    tradeTypeListContext.onGetContentFailure();
                }
            }

            @Override
            public void onFailure(Call<List<TradeType>> call, Throwable t) {
                Log.e("trade_types", "Error while sending request to trade type API controller");

                tradeTypeListContext.onGetContentFailure();
            }
        });
    }
}
