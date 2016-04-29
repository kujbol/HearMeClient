package com.kprod.hearme.data.api;


import com.kprod.hearme.data.api.model.User;

import rx.Observable;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;

public interface HearMeService {
    @GET("v1/user")
    Observable<Result<User>> getUser();
}
