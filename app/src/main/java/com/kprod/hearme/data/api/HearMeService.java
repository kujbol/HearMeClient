package com.kprod.hearme.data.api;


import com.kprod.hearme.data.api.model.NextUser.NextUser;
import com.kprod.hearme.data.api.model.NextUser.NextUserLike;
import com.kprod.hearme.data.api.model.NextUser.NextUserLikeResponse;
import com.kprod.hearme.data.api.model.Settings;
import com.kprod.hearme.data.api.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import rx.Observable;

import retrofit2.http.GET;

public interface HearMeService {
    @GET("user")
    Observable<User> getUser();

    @GET("settings")
    Observable<Settings> getSettings();

    @POST("settings")
    Call<Settings> postSettings(@Body Settings settings);

    @POST("next_user")
    Observable<NextUser> postNextUser();

    @PATCH("next_user")
    Call<NextUserLikeResponse> patchNextUser(@Body NextUserLike nextUserLike);
}
