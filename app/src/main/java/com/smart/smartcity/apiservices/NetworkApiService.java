package com.smart.smartcity.apiservices;

import com.smart.smartcity.model.Network;
import com.smart.smartcity.model.Publication;
import com.smart.smartcity.model.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface NetworkApiService {
    @Multipart
    @POST("api/networks")
    Call<Network> insert(@Part("author_id") RequestBody authorId,
                         @Part("name") RequestBody name,
                         @Part("description") RequestBody description,
                         @Part MultipartBody.Part image);

    @GET("api/networks")
    Call<List<Network>> findNetworks();

    @GET("api/networks/{id}/publications")
    Call<List<Publication>> findPublications(@Path("id") int id);

    @POST("api/networks/{id}/publications")
    Call<Publication> insertPublication(@Path("id") int id, @Body Publication publication);
}