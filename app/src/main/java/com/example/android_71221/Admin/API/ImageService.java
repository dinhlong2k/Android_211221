package com.example.android_71221.Admin.API;

import com.example.android_71221.Admin.Model.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageService {
    //https://accountservermanagement.herokuapp.com/api/accounts
    public static final String DOMAIN = "https://accountservermanagement.herokuapp.com/api/";

    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    ImageService imageService = new Retrofit.Builder().baseUrl(DOMAIN).addConverterFactory(GsonConverterFactory.create(gson)).build().create(ImageService.class);

    // ánh xạ response trả về vào obj
    @Multipart
    @POST("accounts")
    Call<Image> registerAccount(@Part(Const.KEY_USERNAME) RequestBody username,
                                @Part(Const.KEY_PASSWORD) RequestBody password,
                                @Part MultipartBody.Part avt);

}
