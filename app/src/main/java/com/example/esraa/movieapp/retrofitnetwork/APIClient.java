package com.example.esraa.movieapp.retrofitnetwork;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static APIClient apiClientInstance;
    private IApiClient apiClient;
    private int cacheSize = 10 * 1024 * 1024;
    private Cache cache;


    private APIClient() {

    }

    public static APIClient getApiClientInstance() {
        if (apiClientInstance == null)
            return new APIClient();
        return apiClientInstance;
    }

    public IApiClient getApiService(Context context) {
        cache = new Cache(context.getCacheDir(), cacheSize);
        if (apiClient == null) {
            apiClient = provideRetrofit(IApiClient.BASE_URL).create(IApiClient.class);
        }
        return apiClient;
    }


    private Retrofit provideRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.addInterceptor(logging);
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.cache(cache);
        return okhttpClientBuilder.build();
    }
}
