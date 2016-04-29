package com.kprod.hearme.UI.user;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kprod.hearme.R;
import com.kprod.hearme.data.api.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import rx.functions.Action1;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
        implements Action1<User> {

    private User user;

    public UserAdapter(){
        setHasStableIds(true);
    }

    @Override
    public void call(User user) {
        this.user = user;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserView view = (UserView) LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.nav_header_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo(user);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final UserView itemView;

        public ViewHolder(UserView itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bindTo(User user) {
            itemView.bindTo(user);
        }
    }
}
