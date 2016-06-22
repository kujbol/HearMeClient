package com.kprod.hearme.ui.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.PropertyChangeRegistry;

import com.kprod.hearme.data.api.model.Conversation;
import com.kprod.hearme.data.api.model.Conversations;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class ConversationsViewModel extends Subscriber<Conversations> implements Observable{
    private transient PropertyChangeRegistry mCallbacks;

    @Bindable
    public ObservableArrayList<ConversationViewModel> conversations;

    public ConversationsViewModel()
    {
        this.conversations = new ObservableArrayList<>();
    }

    public void addConversation(String display_name, String _id, String image_url, String last_message)
    {
        this.conversations.add(new ConversationViewModel(new Conversation(display_name, _id, image_url, last_message)));
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(Conversations conversations) {
        for (Conversation c: conversations.conversations_preview) {
            this.conversations.add(new ConversationViewModel(c));
        }
    }

    @Override
    public synchronized void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        if (mCallbacks == null) {
            mCallbacks = new PropertyChangeRegistry();
        }
        mCallbacks.add(callback);
    }

    @Override
    public synchronized void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
        if (mCallbacks != null) {
            mCallbacks.remove(callback);
        }
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    public synchronized void notifyChange() {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, 0, null);
        }
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with {@link Bindable} to generate a field in
     * <code>BR</code> to be used as <code>fieldId</code>.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    public void notifyPropertyChanged(int fieldId) {
        if (mCallbacks != null) {
            mCallbacks.notifyCallbacks(this, fieldId, null);
        }
    }
}
