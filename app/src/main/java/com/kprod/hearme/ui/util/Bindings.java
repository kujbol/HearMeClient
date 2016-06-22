package com.kprod.hearme.ui.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewAnimator;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.kprod.hearme.HearMeApp;
import com.kprod.hearme.ui.transformations.CircleTransform;
import com.kprod.hearme.ui.viewmodel.ObservableString;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class Bindings {
    private static Picasso picasso;

    public static Picasso getPicasso(Context context) {
        if (picasso == null) {
            picasso = ((HearMeApp) context.getApplicationContext()).picasso;
        }
        return picasso;
    }

    @BindingAdapter({"imageUrl", "placeholderDrawable", "placeholderDrawableError"})
    public static void loadImageRound(ImageView imageView, String imageUrl,
                                      Drawable placeholderDrawable,
                                      Drawable placeholderDrawableError) {
        if (imageUrl != null && !imageUrl.equals("")) {
            getPicasso(imageView.getContext()).load(imageUrl)
                    .placeholder(placeholderDrawable)
                    .transform(new CircleTransform())
                    .error(placeholderDrawableError)
                    .into(imageView);
        } else {
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
        if (isLoading == Boolean.TRUE)
            viewAnimator.setDisplayedChild(0);
        if (isLoading == Boolean.FALSE)
            viewAnimator.setDisplayedChild(1);
    }

    @BindingAdapter({"spinner_selection"})
    public static void setSpinnerSelection(AppCompatSpinner spinner, ObservableString value) {
        if (spinner.getTag() == null)
        {
            spinner.setTag(true);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedString = ((TextView) view).getText().toString();
                    if (!value.get().equals(selectedString)) {
                        value.set(selectedString);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        String newValue = value.get();
        int index = - 1;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(newValue)) {
                index = i;
            }
        }

        if (index != -1)
        {
            spinner.setSelection(index, true);
        }
    }

 /*   @InverseBindingMethods({
            @InverseBindingMethod(type = ColorPicker.class, attribute = "color"),
    })
    public static String getSpinnerSelection(android.support.v7.widget.AppCompatSpinner spinner) {
        return (String) spinner.getSelectedItem();
    }*/
}