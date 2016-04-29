package com.kprod.hearme.data.api.auth;

import com.kprod.hearme.spotify.auth.AuthService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class AuthInterceptor implements Interceptor {

  private AuthService authService;
  public AuthInterceptor(AuthService authService){
    this.authService = authService;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request.Builder builder = chain.request().newBuilder();
    builder.header("token", this.authService.getAccessToken());
    return chain.proceed(builder.build());
  }
}
