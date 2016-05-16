package com.kprod.hearme.data.api.model;

public class NextUser {
    public String _id;
    public String image_url;
    public String visible_name;
    public Square square;

    public NextUser(String _id, String image_url, String visible_name, Square square) {
        this._id = _id;
        this.image_url = image_url;
        this.visible_name = visible_name;
        this.square = square;
    }
}
