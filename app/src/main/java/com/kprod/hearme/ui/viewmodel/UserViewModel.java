package com.kprod.hearme.ui.viewmodel;


import android.databinding.ObservableField;
import android.util.Log;

import com.kprod.hearme.data.api.model.User;

import rx.Subscriber;

public class UserViewModel extends Subscriber<User>{
    public ObservableField<String> spotifyId;
    public ObservableField<String> imageUrl;
    public ObservableField<String> email;
    public ObservableField<String> dispalyName;

    public UserViewModel(String spotifyId, String imageUrl, String dispalyName, String email){
        this.spotifyId = new ObservableField<>(spotifyId);
        this.imageUrl = new ObservableField<>(imageUrl);
        this.dispalyName = new ObservableField<>(dispalyName);
        this.email = new ObservableField<>(email);
    }

    public UserViewModel(){
        this("", "", "", "");
    }

    public void loadUser(User user){
        this.spotifyId.set(user._id);
        this.imageUrl.set(user.image_url);
        this.dispalyName.set(user.display_name);
        this.email.set(user.email);
    }

    @Override
    public void onCompleted() {
        Log.d("UserSubscriber", "User completed");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("UserSubscriber", "Error", e);
    }

    @Override
    public void onNext(User user) {
        loadUser(user);
    }
}
