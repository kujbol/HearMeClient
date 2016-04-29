package com.kprod.hearme.UI;

import android.content.Intent;
import android.os.Bundle;

import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.R;
import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.spotify.auth.AuthService;

public class MainActivity extends NavigationActivity{

    private AuthService authService;
    private HearMeService hearMeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.prepareLayout(R.layout.activity_main);

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

        }
    }
}
