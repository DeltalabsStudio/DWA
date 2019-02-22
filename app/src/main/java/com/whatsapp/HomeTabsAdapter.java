package com.whatsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomeTabsAdapter extends FragmentStatePagerAdapter {

    public HomeTabsAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment=new CameraFragment();
                break;
            case 1:
                fragment=new ConversationsFragment();
                break;
            case 2:
                fragment=new StatusesFragment();
                break;
            case 3:
                fragment=new CallsFragment();
                break;
        }
        Bundle args = new Bundle();
        args.putInt(HomeActivity.ARG_INITIAL_POSITION, 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="";
                break;
            case 1:
                title="Chats";
                break;
            case 2:
                title="Status";
                break;
            case 3:
                title="Calls";
                break;
        }

        return title;
    }
}
