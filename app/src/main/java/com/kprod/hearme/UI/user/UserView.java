package com.kprod.hearme.UI.user;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.R;
import com.kprod.hearme.data.api.HearMeService;
import com.kprod.hearme.data.api.model.User;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.adapter.rxjava.Result;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserView extends LinearLayout {

    @BindView(R.id.avatarView) ImageView avatarView;
    @BindView(R.id.nameView) TextView nameView;
    @BindView(R.id.nav_header_linear_view)

    private HearMeService  hearMeService;
    private Picasso picasso;

    public UserView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // It's not working ( need to implement dagger
        hearMeService = ((HearMeApp)context).hearMeService;
        picasso = ((HearMeApp)context).picasso;
    }


    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        hearMeService.getUser()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Result<User>>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {

                   }

                   @Override
                   public void onNext(Result<User> userResult) {
                       bindTo(userResult.response().body());
                   }
               }
            );
    }

    public void bindTo(User user){
        picasso.load(user.image_url)
                .placeholder(R.drawable.ic_avatar)
                .fit()
                .into(avatarView);
        nameView.setText(user._id);
    }
}
