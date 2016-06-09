package com.kprod.hearme.ui.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewAnimator;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.ui.transformations.CircleTransform;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class Bindings {
    private static Picasso picasso;

    public static Picasso getPicasso(Context context) {
        if (picasso == null) {
            picasso = ((HearMeApp)context.getApplicationContext()).picasso;
        }
        return picasso;
    }

    @BindingAdapter({"imageUrl", "placeholderDrawable", "placeholderDrawableError"})
    public static void loadImageRound(ImageView imageView, String imageUrl,
                                      Drawable placeholderDrawable,
                                      Drawable placeholderDrawableError)
    {
        if (imageUrl != null && !imageUrl.equals("")) {
            getPicasso(imageView.getContext()).load(imageUrl)
                    .placeholder(placeholderDrawable)
                    .transform(new CircleTransform())
                    .error(placeholderDrawableError)
                    .into(imageView);
        }
        else{
            imageView.setImageDrawable(placeholderDrawableError);
        }
    }

    @BindingAdapter({"squareImageUrl", "placeholderDrawable", "placeholderDrawableError"})
    public static void loadImageSquare(ImageView imageView, String squareImageUrl,
                                      Drawable placeholderDrawable,
                                      Drawable placeholderDrawableError) {
        if (squareImageUrl != null && !squareImageUrl.equals("")) {
                getPicasso(imageView.getContext()).load(squareImageUrl)
                    .placeholder(placeholderDrawable)
                    .error(placeholderDrawableError)
                    .into(imageView);
        }
        else{
            imageView.setImageDrawable(placeholderDrawableError);
        }
    }


    @BindingAdapter({"isLoading"})
    public static void viewAnimatorHandle(ViewAnimator viewAnimator, Boolean isLoading){
        if (isLoading == Boolean.FALSE)
            viewAnimator.showNext();
    }

}