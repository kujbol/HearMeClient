package com.kprod.hearme.spotify.player;

import android.app.Activity;
import android.util.Log;

import com.kprod.hearme.spotify.SpotifyBaseModule;
import com.kprod.hearme.spotify.auth.AuthService;
import com.kprod.hearme.ui.MainActivity;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;

public class PlayerService extends SpotifyBaseModule {
    public static Player getPlayer(AuthService authService, Activity activity) {
        Config playerConfig = new Config(activity, authService.getAccessToken(), CLIENT_ID);

        Player player = Spotify.getPlayer(playerConfig, activity, new Player.InitializationObserver() {
            @Override
            public void onInitialized(Player player) {
                player.addConnectionStateCallback((ConnectionStateCallback) activity);
                player.addPlayerNotificationCallback((PlayerNotificationCallback) activity);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
            }
        });

        return player;
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
