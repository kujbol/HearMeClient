package com.kprod.hearme.ui.viewmodel;

import android.databinding.BaseObservable;

import com.kprod.hearme.data.api.model.Conversation;

public class ConversationViewModel extends BaseObservable{
    private final Conversation model;

    public ConversationViewModel(Conversation model) {
        this.model = model;
    }

    public Conversation getModel() {
        return model;
    }

    public String getId() {
        return model.getId();
    }

    public String getDisplayName() {
        return model.getDisplayName();
    }

    public String getImageUrl() {
        return model.getImageUrl();
    }

    public String getLastMessage() {
        return model.getLastMessage();
    }
}
