package com.kprod.hearme.data.api.model;


public class Settings {
    public final SearchPreferences search_preferences;
    public final SearchSettings search_settings;

    public Settings(SearchPreferences search_preferences, SearchSettings search_settings) {
        this.search_preferences = search_preferences;
        this.search_settings = search_settings;
    }
}
