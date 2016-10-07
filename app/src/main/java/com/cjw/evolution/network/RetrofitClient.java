package com.cjw.evolution.network;

import com.cjw.evolution.account.UserSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 3/15/16
 * Time: 10:58 PM
 * Desc: RetrofitClient
 */
public class RetrofitClient {

    public static Retrofit defaultInstance() {
        return new Retrofit.Builder()
                .client(defaultApiOkHttpClient())
                .baseUrl(ServerConfig.API_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(defaultGson()))
                .build();
    }

    public static Retrofit authorizeInstance() {
        return new Retrofit.Builder()
                .client(authorizeOkHttpClient())
                .baseUrl(ServerConfig.AUTHORIZE_HOST)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(defaultGson()))
                .build();
    }

    public static OkHttpClient defaultApiOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new AuthInterceptor())
                .build();
    }

    public static OkHttpClient authorizeOkHttpClient() {
        return new OkHttpClient.Builder()
                .build();
    }

    public static Gson defaultGson() {
        return new GsonBuilder()
                .create();
    }
}
