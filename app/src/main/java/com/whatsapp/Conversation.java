package com.whatsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.whatsapp.wallpaper.WallPaperView;

import id.delta.whatsapp.R;
import id.delta.whatsapp.value.Colors;
import id.delta.whatsapp.value.FancyText;
import id.delta.whatsapp.value.Themes;

public class Conversation extends DialogToastActivity {

    public boolean isStyle;
    public int style;

    WallPaperView mWall;
    MentionableEntry ae;
    Chat chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        mWall = findViewById(R.id.conversation_background);

        Picasso.with(this).load(R.drawable.default_wallpaper).into(mWall);
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
}
