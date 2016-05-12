package com.kprod.hearme.data.api.model;

import java.util.List;

public class SearchSettings {
    public final String gender;
    public final List<String> languages;

    public SearchSettings(String gender, List<String> languages) {
        this.gender = gender;
        this.languages = languages;
    }
}
