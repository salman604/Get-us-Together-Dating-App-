package com.firedating.chat.Accounts;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.firedating.chat.Favorite.FavoritesFragment;
import com.firedating.chat.Likes.LikesFragment;
import com.firedating.chat.Matches.MatchesFragment;
import com.firedating.chat.Visitors.VisitorsFragment;

public class AccountsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public AccountsAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.mNumOfTabs = i;
    }

    public Fragment getItem(int i) {
        if (i == 0) {
            return new MatchesFragment();
        }
        if (i == 1) {
            return new LikesFragment();
        }
        if (i == 2) {
            return new VisitorsFragment();
        }
        if (i != 3) {
            return null;
        }
        return new FavoritesFragment();
    }

    public int getCount() {
        return this.mNumOfTabs;
    }
}
