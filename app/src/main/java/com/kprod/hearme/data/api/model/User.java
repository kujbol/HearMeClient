package com.kprod.hearme.data.api.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public final class User {
    @NonNull public final String _id;
    @NonNull public final String is_active;
    @Nullable public final String image_url;

    public User(String _id, String is_active, @Nullable String image_url) {
        this._id = _id;
        this.is_active = is_active;
        this.image_url = image_url;
    }
}
