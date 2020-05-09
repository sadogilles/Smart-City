package com.smart.smartcity.dao;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.smart.smartcity.apiservices.NetworkApiService;
import com.smart.smartcity.context.IPublicationCreationContext;
import com.smart.smartcity.context.IPublicationListContext;
import com.smart.smartcity.model.Publication;
import com.smart.smartcity.model.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicationDAO {    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";
    private Retrofit retrofit;

    private IPublicationListContext publicationListContext;
    private IPublicationCreationContext publicationCreationContext;

    public PublicationDAO() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setPublicationListContext(IPublicationListContext publicationListContext) {
        this.publicationListContext = publicationListContext;
    }

    public void setPublicationCreationContext(IPublicationCreationContext publicationCreationContext) {
        this.publicationCreationContext = publicationCreationContext;
    }

    public void findPublications(int networkId) {
        NetworkApiService apiService = retrofit.create(NetworkApiService.class);

        Call<List<Publication>> call = apiService.findPublications(networkId);
        call.enqueue(new Callback<List<Publication>>() {
            @Override
            public void onResponse(Call<List<Publication>> call, Response<List<Publication>> response) {
                if (response.isSuccessful()) {
                    publicationListContext.onGetPublicationsSuccessful(response.body());
                } else {
                    publicationListContext.onGetPublicationsFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Publication>> call, Throwable t) {
                Log.e("publication", "Error while sending request to publication API");
                Log.e("publication", t.getMessage());
                publicationListContext.onGetPublicationsFailure();
            }
        });
    }

    public void insertPublication(Publication publication) {
        NetworkApiService apiService = retrofit.create(NetworkApiService.class);

        Call<Publication> call = apiService.insertPublication(publication.getNetworkId(), publication);
        call.enqueue(new Callback<Publication>() {
            @Override
            public void onResponse(Call<Publication> call, Response<Publication> response) {
                if (response.isSuccessful()) {
                    if (publicationCreationContext != null) {
                        publicationCreationContext.onPublicationCreationSuccessful(response.body());
                    }
                } else {
                    if (publicationCreationContext != null) {
                        publicationCreationContext.onPublicationCreationFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<Publication> call, Throwable t) {
                if (publicationCreationContext != null) {
                    publicationCreationContext.onPublicationCreationFailure();
                }
            }
        });
    }
}
