package com.kprod.hearme.data.api.model;

public final class User{
    public final String _id;
    public final String is_active;
    public final String image_url;

    public User(String _id, String is_active, String image_url) {
        this._id = _id;
        this.is_active = is_active;
        this.image_url = image_url;
    }
}
