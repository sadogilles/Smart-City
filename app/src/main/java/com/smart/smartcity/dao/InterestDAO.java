package com.smart.smartcity.dao;

import android.util.Log;

import com.smart.smartcity.apiservices.InterestApiService;
import com.smart.smartcity.context.settings.IInterestListContext;
import com.smart.smartcity.model.Interest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterestDAO {
    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";

    private Retrofit retrofit;
    private IInterestListContext interestListContext;

    public InterestDAO() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setInterestListContext(IInterestListContext interestListContext) {
        this.interestListContext = interestListContext;
    }

    public void findInterests() {
        InterestApiService apiService = retrofit.create(InterestApiService.class);

        Call<List<Interest>> call = apiService.findInterests();
        call.enqueue(new Callback<List<Interest>>() {
            @Override
            public void onResponse(Call<List<Interest>> call, Response<List<Interest>> response) {
                if (response.isSuccessful()) {
                    interestListContext.onGetInterestsSuccessful(response.body());
                } else {
                    interestListContext.onGetInterestsFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Interest>> call, Throwable t) {
                Log.e("interests", "Error while sending request to authentication API");

                interestListContext.onGetInterestsFailure();
            }
        });
    }
}
