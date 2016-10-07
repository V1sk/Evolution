package com.cjw.evolution;

import android.app.Application;
import android.support.annotation.NonNull;

/**
 * Created by CJW on 2016/10/1.
 */

public class EvoApplication extends Application {

    private static EvoApplication sInstance;

    @NonNull
    public static EvoApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
