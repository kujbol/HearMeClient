package com.kprod.hearme.ui.viewmodel;

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
    public ObservableField<List<String>> genders;
    public ObservableField<Boolean> is_in_same_country;
    public ObservableField<List<String>> languages;

    public ObservableField<String> profile_gender;
    public ObservableField<List<String>> profile_languages;

    public SettingsViewModel() {
        this.age_range_low = new ObservableField<>(20);
        this.age_range_top = new ObservableField<>(30);
        this.genders = new ObservableField<>(new ArrayList<>(Arrays.asList("female")));
        this.is_in_same_country = new ObservableField<>(false);
        this.languages = new ObservableField<>(new ArrayList<>(Arrays.asList("english")));

        this.profile_gender = new ObservableField<>("male");
        this.profile_languages = new ObservableField<>(new ArrayList<>(Arrays.asList("english")));
    }

    @Override
    public void onCompleted() {
        Log.d("SettingsSubscriber", "Settings completed");
    }

    @Override
    public void onError(Throwable e) {
        Log.d("SettingsSubscriber", "Settings completed");
    }

    @Override
    public void onNext(Settings settings) {
        loadSettings(settings);
    }

    private void loadSettings(Settings settings) {
        this.age_range_low.set(settings.search_preferences.age_range_low);
        this.age_range_top.set(settings.search_preferences.age_range_top);
        this.genders.set(settings.search_preferences.genders);
        this.is_in_same_country.set(settings.search_preferences.is_in_same_country);
        this.languages.set(settings.search_preferences.languages);

        this.profile_gender.set(settings.search_settings.gender);
        this.profile_languages.set(settings.search_settings.languages);
    }

    public Settings getSettings() {
        SearchPreferences searchPreferences = new SearchPreferences(age_range_low.get(),
                age_range_top.get(), genders.get(), is_in_same_country.get(), languages.get());
        SearchSettings searchSettings = new SearchSettings(profile_gender.get(), profile_languages.get());
        return new Settings(searchPreferences, searchSettings);

    }
}
