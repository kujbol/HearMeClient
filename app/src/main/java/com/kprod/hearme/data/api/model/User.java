package com.kprod.hearme.data.api.model;

public final class User{
    public final String _id;
    public final Boolean is_active;
    public final String image_url;
    public final String display_name;
    public final String email;

    public User(String _id, Boolean is_active, String image_url, String display_name, String email) {
        this._id = _id;
        this.is_active = is_active;
        this.image_url = image_url;
        this.display_name = display_name;
        this.email = email;
    }
}
