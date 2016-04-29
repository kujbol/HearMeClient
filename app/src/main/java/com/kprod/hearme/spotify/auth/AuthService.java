package com.kprod.hearme.spotify.auth;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.kprod.hearme.spotify.SpotifyBaseModule;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;

public class AuthService extends SpotifyBaseModule{
    private AuthenticationResponse response = null;
    private static final String[] SCOPE = {"user-top-read", "user-read-private", "user-read-email",
            "user-follow-read", "user-read-birthdate", "streaming"};

    public void authLogin(Activity activity){
        AuthenticationClient.openLoginActivity(activity, REQUEST_CODE, getAuthRequest());
    }

    public boolean authHandleResponse(int requestCode, int resultCode, Intent intent){
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                this.response = response;
                return true;
            }
        }
        return false;
    }

    private AuthenticationRequest getAuthRequest(){
        AuthenticationRequest.Builder builder = getAuthRequestBuilder();
        return builder.build();
    }

    private AuthenticationRequest.Builder getAuthRequestBuilder(){
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(SCOPE);
        return builder;
    }

    public String getAccessToken() {
        return response.getAccessToken();
    }
}
