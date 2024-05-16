package com.example.netflop.data.services;

import com.example.netflop.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    public static  Retrofit getRetrofitInstance(String baseURL){
//        String baseURL = context.getResources().getString(R.string.base_url);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        // Thêm Interceptor vào OkHttpClient
        httpClient.addInterceptor(new HeaderInterceptor());
        if(retrofit==null){
            retrofit=new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).client(httpClient.build()).build();
        }
        return  retrofit;
    }
}
