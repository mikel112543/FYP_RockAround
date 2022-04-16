package com.example.rockaroundapp.util;

import android.util.Log;

import androidx.databinding.BindingAdapter;

import com.example.rockaroundapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("android:imageURL")
    public static void setImageURL(ShapeableImageView imageView, String URL) {
        try {
            Picasso.get().load(URL)
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .error(R.drawable.ic_baseline_account_circle_24)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        } catch (Exception e) {
            Log.e("ImageURL", e.getMessage());
        }
    }
}
