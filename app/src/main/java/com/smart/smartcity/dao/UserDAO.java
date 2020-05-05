package com.smart.smartcity.dao;

import android.util.Log;

import com.smart.smartcity.apiservices.UserApiService;
import com.smart.smartcity.context.IAuthenticationContext;
import com.smart.smartcity.apiservices.AuthenticationApiService;
import com.smart.smartcity.context.IProfileUpdateContext;
import com.smart.smartcity.context.IRegistrationContext;
import com.smart.smartcity.model.User;
import com.smart.smartcity.protocol.AuthenticationData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDAO {
    private static final String BASE_API_URL = "https://smartcityapi20200414094628.azurewebsites.net/";
    private Retrofit retrofit;
    private IAuthenticationContext authenticationContext;
    private IRegistrationContext registrationContext;
    private IProfileUpdateContext profileUpdateContext;

    public UserDAO() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setAuthenticationContext(IAuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public void setRegistrationContext(IRegistrationContext registrationContext) {
        this.registrationContext = registrationContext;
    }

    public void setProfileUpdateContextContext(IProfileUpdateContext profileUpdateContext) {
        this.profileUpdateContext = profileUpdateContext;
    }

    // TODO : Check if context defined
    public void authentify(String mailAddress, String password) {
        AuthenticationApiService apiService = retrofit.create(AuthenticationApiService.class);
        AuthenticationData data = new AuthenticationData();
        data.setEmail(mailAddress);
        data.setPassword(password);

        Call<User> call = apiService.authentify(data);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (authenticationContext != null) {
                        authenticationContext.onSuccess(response.body());
                    }
                } else {
                    if (authenticationContext != null) {
                        authenticationContext.onUserNotFound();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("authentication", "Error while sending request to authentication API");
            }
        });
    }

    public void register(User user) {
        UserApiService apiService = retrofit.create(UserApiService.class);

        Call<User> call = apiService.register(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (registrationContext != null) {
                        registrationContext.onSuccess(user);
                    }
                } else {
                    if (registrationContext != null) {
                        registrationContext.onRegistrationError();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (registrationContext != null) {
                    registrationContext.onRegistrationError();
                }
            }
        });
    }

    public void update(User user) {
        UserApiService apiService = retrofit.create(UserApiService.class);

        Call<User> call = apiService.update(user.getId(), user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (profileUpdateContext != null) {
                        profileUpdateContext.onUpdateSuccessful(user);
                    }
                } else {
                    if (profileUpdateContext != null) {
                        profileUpdateContext.onUpdateFailure();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                registrationContext.onRegistrationError();
            }
        });
    }
}
