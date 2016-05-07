package com.kprod.hearme.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.R;
import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.data.api.model.User;
import com.kprod.hearme.databinding.NavHeaderMainBinding;
import com.kprod.hearme.spotify.auth.AuthService;
import com.kprod.hearme.ui.viewmodel.UserViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AuthService authService;
    private HearMeService hearMeService;
    private Subscription subscription;
    private UserViewModel userViewModel = new UserViewModel();

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareLayout(R.layout.activity_main);

        loadServices();

        authService.authLogin(MainActivity.this);
    }

    private void loadServices(){
        authService = ((HearMeApp)getApplication()).authService;
        hearMeService = ((HearMeApp)getApplication()).hearMeService;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (authService.authHandleResponse(requestCode, resultCode, intent)){
            subscription = hearMeService.getUser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(userViewModel);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if (subscription != null)
            subscription.unsubscribe();
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
        return true;
    }

    protected void prepareLayout(int layoutID) {
        setContentView(R.layout.activity_main);
        ButterKnife.setDebug(true);
        ButterKnife.bind(MainActivity.this);

        NavHeaderMainBinding binding = NavHeaderMainBinding.inflate(getLayoutInflater());
        navigationView.addHeaderView(binding.getRoot());
        binding.setUser(userViewModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }
}