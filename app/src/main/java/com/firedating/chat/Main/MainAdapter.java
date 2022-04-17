package com.firedating.chat.Main;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.firedating.chat.Chats.ChatsFragment;
import com.firedating.chat.Feeds.FeedsFragment;
import com.firedating.chat.Profile.ProfileFragment;
import com.firedating.chat.Swipe.SwipeFragment;
import com.firedating.chat.Users.UsersFragment;

public class MainAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MainAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.mNumOfTabs = i;
    }

    public Fragment getItem(int i) {
        if (i == 0) {
            return new ProfileFragment();
        }
        if (i == 1) {
            return new UsersFragment();
        }
        if (i == 2) {
            return new SwipeFragment();
        }
        if (i == 3) {
            return new ChatsFragment();
        }
        if (i != 4) {
            return null;
        }
        return new FeedsFragment();
    }

    public int getCount() {
        return this.mNumOfTabs;
    }
}
