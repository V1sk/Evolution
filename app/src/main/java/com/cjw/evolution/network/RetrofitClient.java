package com.cjw.evolution.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
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

    private static final String TAG = "RetrofitClient";

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

    private static OkHttpClient defaultApiOkHttpClient() {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.i(TAG, message);
                    }
                });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();

        builder.connectionSpecs(Collections.singletonList(spec))
                .addInterceptor(logging)
                .addNetworkInterceptor(new AuthInterceptor());
        return builder.build();
    }

    private static OkHttpClient authorizeOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Log.d(TAG, message);
                    }
                });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
        return builder.addInterceptor(logging).connectionSpecs(Collections.singletonList(spec)).build();
    }

    private static Gson defaultGson() {
        return new GsonBuilder()
                .create();
    }


}
