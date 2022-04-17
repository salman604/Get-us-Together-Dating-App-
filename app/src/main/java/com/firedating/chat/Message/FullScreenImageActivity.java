package com.firedating.chat.Message;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firedating.chat.R;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        String imageUrl = getIntent().getStringExtra("Image");
        if(imageUrl!=null && !imageUrl.isEmpty()) {
            Glide.with(this).load(imageUrl).into((ImageView) findViewById(R.id.fullscreen_content));
        }else{
            Glide.with(this).load(R.drawable.profile_image).into((ImageView) findViewById(R.id.fullscreen_content));
        }
    }
}
