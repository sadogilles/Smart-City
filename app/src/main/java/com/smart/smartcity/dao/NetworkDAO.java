package com.smart.smartcity.dao;

import android.util.Log;

import com.smart.smartcity.activity.LoginActivity;
import com.smart.smartcity.apiservices.InterestApiService;
import com.smart.smartcity.apiservices.NetworkApiService;
import com.smart.smartcity.apiservices.UserApiService;
import com.smart.smartcity.context.IAcceptSubscriptionContext;
import com.smart.smartcity.context.IAuthenticationContext;
import com.smart.smartcity.context.INetworkCreationContext;
import com.smart.smartcity.context.INetworkListContext;
import com.smart.smartcity.context.IProfileUpdateContext;
import com.smart.smartcity.context.IRejectSubscriptionContext;
import com.smart.smartcity.context.ISubscribeContext;
import com.smart.smartcity.fragment.NetworkAvailableFragment;
import com.smart.smartcity.fragment.NewsFragment;
import com.smart.smartcity.model.Interest;
import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Subscription;
import com.smart.smartcity.model.User;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    private INetworkListContext networkListContext;
    private IAcceptSubscriptionContext acceptSubscriptionContext;

    private ISubscribeContext subscribeContext;
    private IRejectSubscriptionContext rejectSubscriptionContext;

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

    public void setNetworkListContext(INetworkListContext networkListContext) {
        this.networkListContext = networkListContext;
    }

    public void setAcceptSubscriptionContext(IAcceptSubscriptionContext acceptSubscriptionContext) {
        this.acceptSubscriptionContext = acceptSubscriptionContext;
    }

    public void setRejectSubscriptionContext(IRejectSubscriptionContext rejectSubscriptionContext) {
        this.rejectSubscriptionContext = rejectSubscriptionContext;
    }

    public void setSubscribeContext(ISubscribeContext subscribeContext) {
        this.subscribeContext = subscribeContext;
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

    public void findNetworks() {
        NetworkApiService apiService = retrofit.create(NetworkApiService.class);

        Call<List<Network>> call = apiService.findNetworks();
        call.enqueue(new Callback<List<Network>>() {
            @Override
            public void onResponse(Call<List<Network>> call, Response<List<Network>> response) {
                if (response.isSuccessful()) {
                    networkListContext.onGetNetworksSuccessful(response.body());
                } else {
                    networkListContext.onGetNetworksFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Network>> call, Throwable t) {
                Log.e("network", "Error while sending request to authentication API");

                networkListContext.onGetNetworksFailure();
            }
        });
    }

    public void findSubscribedNetwork(User user) {
        UserApiService apiService = retrofit.create(UserApiService.class);

        Call<List<Network>> call = apiService.findSubscribedNetworks(user.getId());
        call.enqueue(new Callback<List<Network>>() {
            @Override
            public void onResponse(Call<List<Network>> call, Response<List<Network>> response) {
                if (response.isSuccessful()) {
                    networkListContext.onGetNetworksSuccessful(response.body());
                } else {
                    networkListContext.onGetNetworksFailure();
                }
            }

            @Override
            public void onFailure(Call<List<Network>> call, Throwable t) {
                Log.e("network", "Error while sending request to authentication API");

                networkListContext.onGetNetworksFailure();
            }
        });
    }

    public void insertSubscription(Subscription subscription) {
        NetworkApiService apiService = retrofit.create(NetworkApiService.class);

        Call<Subscription> call = apiService.insertSubscription(subscription.getNetworkId(), subscription);
        call.enqueue(new Callback<Subscription>() {
            @Override
            public void onResponse(Call<Subscription> call, Response<Subscription> response) {
                if (response.isSuccessful()) {
                    // TODO : Change subscribeContext to SubscriptionContext
                    if (subscribeContext != null) {
                        subscribeContext.onSubscribeSuccessful(response.body());
                    }
                } else {
                    if (subscribeContext != null) {
                        subscribeContext.onSubscribeFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<Subscription> call, Throwable t) {
                if (subscribeContext != null) {
                    subscribeContext.onSubscribeFailure();
                }
            }
        });
    }

    public void acceptSubscription(Subscription subscription) {
        NetworkApiService apiService = retrofit.create(NetworkApiService.class);

        Call<Subscription> call = apiService.updateSubscription(subscription.getNetworkId(), subscription.getId(), subscription);
        call.enqueue(new Callback<Subscription>() {
            @Override
            public void onResponse(Call<Subscription> call, Response<Subscription> response) {
                if (response.isSuccessful()) {
                    if (acceptSubscriptionContext != null) {
                        acceptSubscriptionContext.onAcceptSubscriptionSuccessful(response.body());
                    }
                } else {
                    if (acceptSubscriptionContext != null) {
                        acceptSubscriptionContext.onAcceptSubscriptionError();
                    }
                }
            }

            @Override
            public void onFailure(Call<Subscription> call, Throwable t) {
                if (acceptSubscriptionContext != null) {
                    acceptSubscriptionContext.onAcceptSubscriptionError();
                }
            }
        });
    }

    public void rejectSubscription (Subscription subscription){
        NetworkApiService apiService = retrofit.create(NetworkApiService.class);

        Call<ResponseBody> call = apiService.deleteSubscription(subscription.getNetworkId(), subscription.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (rejectSubscriptionContext != null) {
                        rejectSubscriptionContext.onRejectSubscriptionSuccessFull();
                    }
                } else {
                    if (rejectSubscriptionContext != null) {
                        rejectSubscriptionContext.onRejectSubscriptionFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (rejectSubscriptionContext != null) {
                    rejectSubscriptionContext.onRejectSubscriptionFailure();
                }
            }
        });
    }
}
