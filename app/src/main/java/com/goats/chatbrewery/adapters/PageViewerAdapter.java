package com.goats.chatbrewery.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goats.chatbrewery.fragments.Chats;
import com.goats.chatbrewery.fragments.Friends;
import com.goats.chatbrewery.fragments.Updates;

public class PageViewerAdapter extends FragmentPagerAdapter {

    public PageViewerAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Chats();
            case 1:
                return new Friends();
            case 2:
                return new Updates();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}