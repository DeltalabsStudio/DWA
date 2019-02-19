package com.whatsapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import id.delta.whatsapp.R;

import static id.delta.whatsapp.value.Main.actionBarTitle;
import static id.delta.whatsapp.value.Main.headerBackground;


public class ArchivedConversationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        actionBarTitle(this, R.string.deltalabs);
        headerBackground(this, getSupportActionBar());
    }
}
