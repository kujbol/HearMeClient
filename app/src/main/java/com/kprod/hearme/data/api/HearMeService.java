package com.kprod.hearme.data.api;


import android.databinding.ObservableField;

import com.kprod.hearme.data.api.model.Conversation;
import com.kprod.hearme.data.api.model.Conversations;
import com.kprod.hearme.data.api.model.NextUser;
import com.kprod.hearme.data.api.model.Settings;
import com.kprod.hearme.data.api.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("next_user")
    Observable<NextUser> getNextUser();

    @GET("messages")
    Observable<Conversations> getConversations();
}
