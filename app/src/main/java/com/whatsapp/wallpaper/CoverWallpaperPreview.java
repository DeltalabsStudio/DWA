package com.whatsapp.wallpaper;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.whatsapp.DialogToastActivity;
import com.whatsapp.PhotoView;

import id.delta.whatsapp.R;
import id.delta.whatsapp.utils.Tools;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.Themes;

public class CoverWallpaperPreview extends DialogToastActivity {

    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Tools.intLayout("cover_wallpaper_preview"));


        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setBackgroundColor(Colors.setWarnaPrimer());
        mToolbar.setTitleTextColor(Colors.warnaAutoTitle());
        mToolbar.setNavigationIcon(Tools.colorDrawable("ic_back_white", Colors.warnaAutoTitle(), PorterDuff.Mode.SRC_IN));


        PhotoView mImage = findViewById(R.id.wallpaper_photo_view);

        if (getIntent().hasExtra("output")) {
            uri = (Uri)getIntent().getParcelableExtra("output");
        }

        Picasso.with(this).load(uri).into(mImage);

    }



    @Override
    protected void onResume() {
        Themes.setStatusBarView(this, Colors.alphaColor(Colors.setWarnaPrimer(), Themes.setStatusBarAlpha()));
        super.onResume();
    }

}
