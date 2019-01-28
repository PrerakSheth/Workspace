package com.patchpets.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

public class MyApp extends MultiDexApplication {

    private static MyApp mInstance;
    public static Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        Fabric.with(this, new Crashlytics());
        File httpCacheDirectory = new File(getCacheDir(), "picasso-cache");
        Cache cache = new Cache(httpCacheDirectory, 30 * 1024 * 1024);
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().cache(cache);
        picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(okHttpClientBuilder.build())).build();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
