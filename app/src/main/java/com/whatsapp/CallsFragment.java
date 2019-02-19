package com.whatsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import id.delta.whatsapp.R;
import id.delta.whatsapp.home.fragments.StatusesAdapter;
import id.delta.whatsapp.ui.views.Card;
import id.delta.whatsapp.ui.views.FunkyHeader;
import id.delta.whatsapp.value.Row;


public class CallsFragment extends ListFragment {

    private String dataArray[];

    RecyclerView mStatusesRecyclerView;
    StatusesFragment mStockStatusesFragment;
    StatusesAdapter mStatusesAdapter;

    public CallsFragment() {
        dataArray = new String[] { "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam","Tujuh", "Delapan", "Sembilan","Sepuluh", "Sebelas", "Dua Belas","Tiga Belas","Empat Belas", "Lima Belas", "Enam Belas","Tujuh Belas", "Delapan Belas", "Sembilan Belas"};
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, dataArray);
        setListAdapter(listAdapter);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calls, container, false);
        final FunkyHeader mHeader = view.findViewById(R.id.wave_head);

        ListView mList = view.findViewById(android.R.id.list);
        mList.setDivider(Row.listDivider());

        Picasso.with(getContext()).load(R.drawable.cover).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                mHeader.setBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
        return view;
    }

    @Override
    public void onListItemClick(ListView list, View v, int position, long id) {

        startActivity(new Intent(getActivity(), Conversation.class));
    }


}
