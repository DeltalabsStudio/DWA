package com.whatsapp.wallpaper;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.whatsapp.DialogToastActivity;

import java.io.File;

import id.delta.whatsapp.R;

public class GalleryWallpaperPreview extends DialogToastActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversations);
        WallPaperView mWall = findViewById(R.id.mWallpaper);
    }
}
