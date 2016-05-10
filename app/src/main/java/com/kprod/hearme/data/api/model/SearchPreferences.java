package com.kprod.hearme.data.api.model;

import java.util.List;

public class SearchPreferences {
    public final Integer age_range_low;
    public final Integer age_range_top;
    public final List<String> genders;
    public final Boolean is_in_same_country;
    public final List<String> languages;

    public SearchPreferences(Integer age_range_low, Integer age_range_top, List<String> genders, Boolean is_in_same_country, List<String> languages) {
        this.age_range_low = age_range_low;
        this.age_range_top = age_range_top;
        this.genders = genders;
        this.is_in_same_country = is_in_same_country;
        this.languages = languages;
    }
}
