package com.kprod.hearme.ui.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import com.kprod.hearme.data.api.model.SearchPreferences;
import com.kprod.hearme.data.api.model.SearchSettings;
import com.kprod.hearme.data.api.model.Settings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscriber;

public class SettingsViewModel extends Subscriber<Settings> {

    public ObservableField<Integer> age_range_low;
    public ObservableField<Integer> age_range_top;
    public ObservableString genders;
    public ObservableField<Boolean> is_in_same_country;
    public ObservableString languages;

    public ObservableString profile_gender;
    public ObservableString profile_languages;

    public SettingsViewModel() {
        this.age_range_low = new ObservableField<>(20);
        this.age_range_top = new ObservableField<>(30);
        this.genders = new ObservableString("female");
        this.is_in_same_country = new ObservableField<>(false);
        this.languages = new ObservableString("english");

        this.profile_gender = new ObservableString("male");
        this.profile_languages = new ObservableString("english");
    }

    @Override
    public void onCompleted() {
        Log.d("SettingsSubscriber", "Settings completed");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("SettingsSubscriber", "Error on loading settings", e);
    }

    @Override
    public void onNext(Settings settings) {
        loadSettings(settings);
    }

    private void loadSettings(Settings settings) {
        this.age_range_low.set(settings.search_preferences.age_range_low);
        this.age_range_top.set(settings.search_preferences.age_range_top);
        this.genders.set(settings.search_preferences.genders.get(0));
        this.is_in_same_country.set(settings.search_preferences.is_in_same_country);
        this.languages.set(settings.search_preferences.languages.get(0));

        this.profile_gender.set(settings.search_settings.gender);
        this.profile_languages.set(settings.search_settings.languages.get(0));
    }

    public Settings getSettings() {
        SearchPreferences searchPreferences = new SearchPreferences(age_range_low.get(),
                age_range_top.get(), stringToArray(genders.get()), is_in_same_country.get(), stringToArray(languages.get()));
        SearchSettings searchSettings = new SearchSettings(profile_gender.get(), stringToArray(profile_languages.get()));
        return new Settings(searchPreferences, searchSettings);
    }

    private List<String> stringToArray(String string) {
        return Arrays.asList(new String[]{string});
    }
}

