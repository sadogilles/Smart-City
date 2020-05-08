package com.smart.smartcity.dao;

import com.smart.smartcity.apiservices.NetworkApiService;
import com.smart.smartcity.apiservices.UserApiService;
import com.smart.smartcity.context.IAuthenticationContext;
import com.smart.smartcity.context.INetworkCreationContext;
import com.smart.smartcity.context.IProfileUpdateContext;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.User;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class NetworkDAO {
    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";
    private Retrofit retrofit;

    private INetworkCreationContext networkCreationContext;

    public NetworkDAO() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setNetworkCreationContext(INetworkCreationContext networkCreationContext) {
        this.networkCreationContext = networkCreationContext;
    }

    public void insert(Network network) {
        NetworkApiService apiService = retrofit.create(NetworkApiService.class);

        RequestBody authorId = RequestBody.create(MediaType.parse("text/plain"), Integer.toString(network.getAuthorId()));
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), network.getName());
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), network.getDescription());
        File file = new File(network.getLocalImageUri());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<Network> call = apiService.insert(authorId, name, description, image);
        call.enqueue(new Callback<Network>() {
            @Override
            public void onResponse(Call<Network> call, Response<Network> response) {
                if (response.isSuccessful()) {
                    if (networkCreationContext != null) {
                        networkCreationContext.onNetworkCreationSuccessful(network);
                    }
                } else {
                    if (networkCreationContext != null) {
                        networkCreationContext.onNetworkCreationFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<Network> call, Throwable t) {
                if (networkCreationContext != null) {
                    networkCreationContext.onNetworkCreationFailure();
                }
            }
        });
    }

}
