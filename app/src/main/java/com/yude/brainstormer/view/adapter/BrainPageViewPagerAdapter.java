package com.yude.brainstormer.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yude.brainstormer.view.fragment.MyBrainFriendsFragment;
import com.yude.brainstormer.view.fragment.MyBrainIdeasFragment;
import com.yude.brainstormer.view.fragment.BrainProfileFragment;
import com.yude.brainstormer.view.fragment.MyTodoFragment;

import java.util.LinkedList;
import java.util.List;

public class BrainPageViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new LinkedList<>();

    public BrainPageViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        fragments.add(new MyBrainIdeasFragment());
        fragments.add(new MyTodoFragment());
        fragments.add(new MyBrainFriendsFragment());
        fragments.add(new BrainProfileFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ideas";
            case 1:
                return "Todo";
            case 2:
                return "Friends";
            case 3:
                return "Profile";
            default:
                return "NA";
        }
    }
}