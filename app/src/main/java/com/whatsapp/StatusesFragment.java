package com.whatsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.whatsapp.data.ew;
import com.whatsapp.data.eh;
import com.whatsapp.emoji.c;
import com.whatsapp.util.as;

import java.util.ArrayList;


public class StatusesFragment extends ListFragment {

    public ArrayList<a> ap; //mStatusesArrayList
    public eh ag;
    public as aF;
    public com.whatsapp.g.d az;
    public c ay;

    public StatusesFragment() {

        ap = new ArrayList<>();

        f statusRow = new f();

        statusRow.b = new ew();
        statusRow.b.a = "";
        statusRow.b.i = 0;
        statusRow.b.j = 0;

        ap.add(statusRow);

        statusRow = new f();
        statusRow.b = new ew();
        statusRow.b.a = "someone";
        statusRow.b.i = 2;
        statusRow.b.j = 4;

        for (int i=0; i<20; i++) {
            ap.add(statusRow);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public class f extends a {
        //StatusesRow
        public ew b = null;

    }

    public class d extends a {
        //StatusesTitleRow
    }


    public class a {
        //StatusesRowAbstract
    }



    public class StatusesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            notifyDataSetInvalidated();
        }
    }


}
