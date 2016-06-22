package com.kprod.hearme.data.api.model.NextUser;

import com.kprod.hearme.data.api.model.Square;

public class NextUser {
    public String _id;
    public String image_url;
    public String display_name;
    public Square square;

    public NextUser(String _id, String image_url, String display_name, Square square) {
        this._id = _id;
        this.image_url = image_url;
        this.display_name = display_name;
        this.square = square;
    }
}
