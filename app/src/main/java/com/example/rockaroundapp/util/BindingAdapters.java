package com.example.rockaroundapp.util;

import android.net.Uri;
import android.util.Log;

import androidx.databinding.BindingAdapter;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter("android:imageURL")
    public static void setImageURL(ShapeableImageView imageView, String URL) {
        try {
            Picasso.get().load(URL)
                    .fit()
                    .centerCrop()
                    .into(imageView);
        }catch (Exception e) {
            Log.e("ImageURL", e.getMessage());
        }
    }
}
