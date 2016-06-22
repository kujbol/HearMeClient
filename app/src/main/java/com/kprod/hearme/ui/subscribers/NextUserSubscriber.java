package com.kprod.hearme.ui.subscribers;

import android.util.Log;

import com.kprod.hearme.data.api.model.NextUser.NextUser;
import com.kprod.hearme.ui.viewmodel.NextUserViewModel;

import rx.Subscriber;

public class NextUserSubscriber extends Subscriber<NextUser> {
    private final NextUserViewModel viewModel;

    public NextUserSubscriber(NextUserViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void onCompleted() {
        Log.d("NextUserSubscriber", "NextUser completed");
    }

    @Override
    public void onError(Throwable e) {
        Log.e("NextUserSubscriber", "Error on loading nextUser", e);
    }

    @Override
    public void onNext(NextUser nextUser) {
        viewModel.loadNextUser(nextUser);
        viewModel.is_loading.set(Boolean.FALSE);
        this.onCompleted();
    }
}
