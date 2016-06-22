package com.kprod.hearme.data.api.model;

public class Conversation {
    private String _id;
    private String display_name;
    private String image_url;
    private String last_message;

    public Conversation(String display_name, String _id, String image_url, String last_message) {
        this.display_name = display_name;
        this._id = _id;
        this.image_url = image_url;
        this.last_message = last_message;
    }


    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getDisplayName() {
        return display_name;
    }

    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    public String getImageUrl() {
        return image_url;
    }

    public void setImageUrl(String image_url) {
        this.image_url = image_url;
    }

    public String getLastMessage() {
        return last_message;
    }

    public void setLastMessage(String last_message) {
        this.last_message = last_message;
    }
}
