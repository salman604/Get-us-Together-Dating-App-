package com.firedating.chat.Extra;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import ss.com.bannerslider.ImageLoadingService;

public class ImageClass implements ImageLoadingService {
    public Context context;

    public ImageClass(Context context2) {
        this.context = context2;
    }

    public void loadImage(String str, ImageView imageView) {
        Picasso.get().load(str).into(imageView);
    }

    public void loadImage(int i, ImageView imageView) {
        Picasso.get().load(i).into(imageView);
    }

    public void loadImage(String str, int i, int i2, ImageView imageView) {
        Picasso.get().load(str).placeholder(i).error(i2).into(imageView);
    }
}
