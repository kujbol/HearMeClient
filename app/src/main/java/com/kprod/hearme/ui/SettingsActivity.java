package com.kprod.hearme.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.R;
import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.data.api.model.Settings;
import com.kprod.hearme.databinding.ActivitySettingsBinding;
import com.kprod.hearme.ui.viewmodel.SettingsViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingsActivity extends AppCompatActivity {

    private HearMeService hearMeService;
    private Subscription subscription;
    private SettingsViewModel settingsViewModel = new SettingsViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySettingsBinding binding = DataBindingUtil.setContentView(
                this, R.layout.activity_settings
        );
        loadServices();

        subscription = hearMeService.getSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(settingsViewModel);

        binding.setSettings(settingsViewModel);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (subscription != null)
            subscription.unsubscribe();
    }

    private void loadServices() {
        hearMeService = ((HearMeApp) getApplication()).hearMeService;
    }

    public void saveSettings(View view) {
        Call<Settings> call = hearMeService.postSettings(settingsViewModel.getSettings());
        call.enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(
                            SettingsActivity.this, "Unable to save settings",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                Log.d("SettingsActivity", "Settings saved");
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {
                Log.e("SettingsActivity", "Failed to save settings");
                Toast.makeText(
                        SettingsActivity.this, "Unable to save settings",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }
}
