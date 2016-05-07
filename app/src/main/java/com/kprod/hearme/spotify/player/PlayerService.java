package com.kprod.hearme.spotify.player;

import android.app.Activity;

import com.kprod.hearme.spotify.SpotifyBaseModule;
import com.kprod.hearme.spotify.auth.AuthService;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.Spotify;

public class PlayerService extends SpotifyBaseModule {
    public static Player getPlayer(AuthService authService, Activity activity, Player.InitializationObserver callback) {
        Config playerConfig = new Config(activity, authService.getAccessToken(), CLIENT_ID);
        return Spotify.getPlayer(playerConfig, activity, callback);
    }
}
//            @Override
//            public void onInitialized(Player player) {
//                player.addConnectionStateCallback(activity);
//                player.addPlayerNotificationCallback(activity);
//                player.play("spotify:track:2TpxZ7JUBn3uw46aR7qd6V");
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                Log.e("LoginActivity", "Could not initialize player: " + throwable.getMessage());
//            }
//        });
//    }
//}
