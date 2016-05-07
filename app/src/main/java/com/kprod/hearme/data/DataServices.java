package com.kprod.hearme.data;


import android.app.Application;
import android.util.Log;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.kprod.hearme.data.api.auth.AuthInterceptor;
import com.squareup.moshi.Moshi;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class DataServices {
  private static final int DISK_CACHE_SIZE =  50 * 1024 * 1024; // 50 Mib
  private static final HttpUrl PRODUCTION_API_URL = new HttpUrl.Builder()
          .scheme("http")
          .host("139.59.142.23")
          .addEncodedPathSegment("v1")
          .addEncodedPathSegment("")
          .build();

  private static Moshi createMoshi() {
    return new Moshi.Builder().build();
  }

  public static Picasso createPicasso(Application app, OkHttpClient client) {
    return new Picasso.Builder(app)
            .downloader(new OkHttp3Downloader(client))
            .listener((picasso, uri, e) -> Log.e("ERROR: picasso", e.toString() + uri))
            .build();
  }

  public static OkHttpClient createOkHttpClient(Application app, AuthInterceptor oauthInterceptor) {
    // Install an HTTP cache in the application cache directory.
    File cacheDir = new File(app.getCacheDir(), "http");
    Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

    return new OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(oauthInterceptor)
            .build();
  }

  public static Retrofit createRetrofit(OkHttpClient client) {
    return new Retrofit.Builder() //
            .client(client) //
            .baseUrl(PRODUCTION_API_URL) //
            .addConverterFactory(MoshiConverterFactory.create(createMoshi())) //
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //
            .build();
  }
}
