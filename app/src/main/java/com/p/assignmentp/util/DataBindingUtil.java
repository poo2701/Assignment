package com.p.assignmentp.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.p.assignmentp.R;

public class DataBindingUtil {

    @BindingAdapter({"imageUrl"})
    public static void loadImageIntoView(ImageView imageView, String imageURL) {
        Glide.with(imageView)
                .applyDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .load(imageURL)
                .thumbnail(Glide.with(imageView.getContext()).load(R.drawable.loading))
                .into(imageView);
    }
}
