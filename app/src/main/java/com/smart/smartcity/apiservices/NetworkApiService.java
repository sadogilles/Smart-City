package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface NetworkApiService {
    @Multipart
    @POST("api/networks")
    Call<Network> insert(@Part("author_id") RequestBody authorId,
                         @Part("name") RequestBody name,
                         @Part("description") RequestBody description,
                         @Part MultipartBody.Part image);
}
