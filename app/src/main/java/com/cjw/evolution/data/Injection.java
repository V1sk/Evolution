package com.cjw.evolution.data;

import android.content.Context;

import com.cjw.evolution.EvoApplication;
import com.cjw.evolution.data.source.remote.api.DribbbleApiService;
import com.cjw.evolution.data.source.remote.api.DribbbleAuthService;
import com.cjw.evolution.network.RetrofitClient;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:48 AM
 * Desc: Injection
 */
public class Injection {

    public static Context provideContext() {
        return EvoApplication.getInstance();
    }

    public static DribbbleApiService provideDribbbleApi() {
        return RetrofitClient.defaultInstance().create(DribbbleApiService.class);
    }

    public static DribbbleAuthService provideDribbbleAuthApi() {
        return RetrofitClient.authorizeInstance().create(DribbbleAuthService.class);
    }


}
