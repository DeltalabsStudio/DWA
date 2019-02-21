package com.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eightbitlab.blurview.BlurView;
import com.eightbitlab.blurview.SupportRenderScriptBlur;
import com.squareup.picasso.Picasso;
import com.whatsapp.wallpaper.WallPaperView;

import id.delta.whatsapp.R;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.FancyText;
import id.delta.whatsapp.value.Themes;

public class Conversation extends DialogToastActivity {

    public boolean isStyle;
    public int style;

    WallPaperView mWall;
    MentionableEntry ae;
    Chat chat;

    ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Themes.setAppTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        mWall = findViewById(R.id.conversation_background);
        initWall(mWall);

        Themes.setStatusBar(this);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Colors.setWarnaPrimer()));

        ae = findViewById(R.id.entry);
        id.delta.whatsapp.value.Conversation.setEntryTextColor(ae);

        LinearLayout mInput = findViewById(R.id.input_layout);
        mInput.setBackgroundDrawable(getResources().getDrawable(R.drawable.ib_new_round));
        id.delta.whatsapp.value.Conversation.setInputBackground(mInput.getBackground());

        try{
            if(getIntent()!=null){
                chat = getIntent().getParcelableExtra("chat");
                setTitle(chat.getNama());
                ae.setText(chat.getChat());
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        root = findViewById(R.id.root);
        BlurView mBlur = findViewById(R.id.mBlur);

        final float radius = 10f;

        //set background, if your root layout doesn't have one
        final Drawable windowBackground = getWindow().getDecorView().getBackground();

        mBlur.setupWith(root)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new SupportRenderScriptBlur(this))
                .setBlurRadius(radius)
                .setHasFixedTransformationMatrix(true);
    }

    public static Intent a(Context context, String string){
        return null;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        FancyText.addMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FancyText.onMenuClicked(this, item);
        return super.onOptionsItemSelected(item);
    }

    private void initWall(ImageView mWall){
        int theme = Integer.parseInt(Prefs.getString(Keys.KEY_THEME, "0"));
        if(theme!=0){
            Picasso.with(this).load(R.drawable.default_wallpaper_dark).into(mWall);
        }else {
            Picasso.with(this).load(R.drawable.default_wallpaper).into(mWall);
        }
    }
}
