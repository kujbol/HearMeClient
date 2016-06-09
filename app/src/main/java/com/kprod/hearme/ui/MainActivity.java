package com.kprod.hearme.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.R;
import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.data.api.model.User;
import com.kprod.hearme.databinding.ActivityMainBinding;
import com.kprod.hearme.databinding.ContentMainBinding;
import com.kprod.hearme.databinding.NavHeaderMainBinding;
import com.kprod.hearme.spotify.SpotifyBaseModule;
import com.kprod.hearme.spotify.auth.AuthService;
import com.kprod.hearme.spotify.player.PlayerService;
import com.kprod.hearme.ui.viewmodel.NextUserViewModel;
import com.kprod.hearme.ui.viewmodel.UserViewModel;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerNotificationCallback;
import com.spotify.sdk.android.player.PlayerState;
import com.spotify.sdk.android.player.Spotify;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PlayerNotificationCallback, ConnectionStateCallback {
    private AuthService authService;
    private HearMeService hearMeService;
    private Subscription subscription;
    private UserViewModel userViewModel = new UserViewModel();
    private NextUserViewModel nextUserViewModel = new NextUserViewModel();
    private Subscription nextUserSubscription;

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.animated_logo) ImageView animatedLogo;
    @BindView(R.id.main_activity_grid)android.support.v7.widget.GridLayout gridLayout;

    private Player mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareLayout(R.layout.activity_main);

        loadServices();

        authService.authLogin(MainActivity.this);

        if (getResources().getConfiguration().orientation ==  Configuration.ORIENTATION_LANDSCAPE) {
                gridLayout.setColumnCount(4);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayout.setColumnCount(2);
        }
    }

    private void loadServices() {
        authService = ((HearMeApp) getApplication()).authService;
        hearMeService = ((HearMeApp) getApplication()).hearMeService;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (authService.authHandleResponse(requestCode, resultCode, intent)) {
            subscription = hearMeService.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userViewModel);

            nextUserSubscription = hearMeService.getNextUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(nextUserViewModel);

            mPlayer = PlayerService.getPlayer(authService, this);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayout.setColumnCount(4);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            gridLayout.setColumnCount(2);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscription != null)
            subscription.unsubscribe();
        if (nextUserSubscription != null)
            nextUserSubscription.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.nav_share) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }

    protected void prepareLayout(int layoutID) {
        ActivityMainBinding nextUserBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        nextUserBinding.setNextUser(nextUserViewModel);

        NavHeaderMainBinding binding = NavHeaderMainBinding.inflate(getLayoutInflater());
        navigationView.addHeaderView(binding.getRoot());
        navigationView.setNavigationItemSelectedListener(this);
        binding.setUser(userViewModel);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        animatedLogo.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pulse));

    }

   public void playSong(View view) {
        String trackId = (String) view.getTag();
        mPlayer.getPlayerState(playerState -> {
            if (playerState.trackUri.equals("spotify:track:" + trackId)) {
                if (playerState.playing)
                    mPlayer.pause();
                else
                    mPlayer.resume();
            }
            else {
                mPlayer.play("spotify:track:" + trackId);
            }
        });
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Throwable throwable) {
        Log.d("MainActivity", "Login failed");
    }

    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String s) {
        Log.d("MainActivity", "Received connection message: " + s);
    }

    @Override
    public void onPlaybackEvent(EventType eventType, PlayerState playerState) {
        Log.d("MainActivity", "Playback event received: " + eventType.name());
        switch (eventType) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(ErrorType errorType, String s) {
        Log.d("MainActivity", "Playback error received: " + errorType.name());
        switch (errorType) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}