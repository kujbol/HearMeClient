package com.kprod.hearme.ui.viewmodel;

import android.databinding.ObservableField;
import android.util.Log;

import com.kprod.hearme.data.api.model.NextUser.NextUser;

import rx.Subscriber;

public class NextUserViewModel{

    public String _id;

    public ObservableField<String> profile_image_url;
    public ObservableField<String> dispaly_name;

    public ObservableField<String> energy_track_id;
    public ObservableField<String> energy_track_image_url;

    public ObservableField<String> relax_track_id;
    public ObservableField<String> relax_track_image_url;

    public ObservableField<String> sadness_track_id;
    public ObservableField<String> sadness_track_image_url;

    public ObservableField<String> top_track_id;
    public ObservableField<String> top_track_image_url;

    public ObservableField<Boolean> is_loading;

    public NextUserViewModel() {
        this.profile_image_url = new ObservableField<>("");
        this.dispaly_name = new ObservableField<>("");
        this.energy_track_id = new ObservableField<>("");
        this.relax_track_id = new ObservableField<>("");
        this.energy_track_image_url = new ObservableField<>("");
        this.relax_track_image_url = new ObservableField<>("");
        this.sadness_track_id = new ObservableField<>("");
        this.sadness_track_image_url = new ObservableField<>("");
        this.top_track_id = new ObservableField<>("");
        this.top_track_image_url = new ObservableField<>("");
        this.is_loading = new ObservableField<>(Boolean.TRUE);
    }

    public void loadNextUser(NextUser nextUser) {
        _id = nextUser._id;
        profile_image_url.set(nextUser.image_url);
        dispaly_name.set(nextUser.display_name);

        energy_track_id.set(nextUser.square.energy.id);
        energy_track_image_url.set(nextUser.square.energy.image_url);

        relax_track_id.set(nextUser.square.relax.id);
        relax_track_image_url.set(nextUser.square.relax.image_url);

        sadness_track_id.set(nextUser.square.sadness.id);
        sadness_track_image_url.set(nextUser.square.sadness.image_url);

        top_track_id.set(nextUser.square.top.id);
        top_track_image_url.set(nextUser.square.top.image_url);
    }
}
