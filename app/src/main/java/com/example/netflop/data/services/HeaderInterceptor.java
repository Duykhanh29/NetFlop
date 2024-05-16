package com.example.netflop.data.services;

import com.example.netflop.constants.URLConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    public HeaderInterceptor() {
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + URLConstants.apiKey).header("accept","application/json")
                .build();
        return chain.proceed(modifiedRequest);
    }
}
