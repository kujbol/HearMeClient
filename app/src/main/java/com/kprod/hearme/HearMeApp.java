package com.kprod.hearme;

import android.app.Application;

import com.kprod.hearme.data.DataServices;
import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.data.api.auth.AuthInterceptor;
import com.kprod.hearme.spotify.auth.AuthService;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class HearMeApp extends Application{

    public AuthService authService;
    public Picasso picasso;
    public HearMeService hearMeService;

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        initServices();
    }

    private void initServices(){
        authService = new AuthService();
        okHttpClient = DataServices.createOkHttpClient(this, new AuthInterceptor(authService));
        picasso = DataServices.createPicasso(this, okHttpClient);
        retrofit = DataServices.createRetrofit(okHttpClient);
        hearMeService = retrofit.create(HearMeService.class);
    }
}
