package com.smart.smartcity.dao;

import android.util.Log;

import com.smart.smartcity.apiservices.ServiceApiService;
import com.smart.smartcity.context.services.IServiceListContext;
import com.smart.smartcity.model.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceDAO {
    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";

    private Retrofit retrofit;
    private IServiceListContext serviceListContext;

    public ServiceDAO() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setServiceListContext(IServiceListContext serviceListContext) {
        this.serviceListContext = serviceListContext;
    }

    public void findServicesByUserId(int userId) {
        ServiceApiService apiService = retrofit.create(ServiceApiService.class);

        Call<List<Service>> call = apiService.findServicesByUserId(userId);
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful()) {
                    serviceListContext.onSuccess(response.body());
                } else {
                    serviceListContext.onGetServicesFailed();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.e("services", "Error while sending request to authentication API");
            }
        });
    }

    public void findServices() {
        ServiceApiService apiService = retrofit.create(ServiceApiService.class);

        Call<List<Service>> call = apiService.findServices();
        call.enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful()) {
                    serviceListContext.onSuccess(response.body());
                } else {
                    serviceListContext.onGetServicesFailed();
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                Log.e("services", "Error while sending request to authentication API");
            }
        });
    }
}
