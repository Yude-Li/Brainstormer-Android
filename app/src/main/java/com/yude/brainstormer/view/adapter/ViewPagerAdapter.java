package com.yude.brainstormer.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yude.brainstormer.R;
import com.yude.brainstormer.view.fragment.BrainPageFragment;
import com.yude.brainstormer.view.fragment.BrainToFollowFragment;
import com.yude.brainstormer.view.fragment.IdeasFragment;

import java.util.LinkedList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new LinkedList<>();
    private String[] titles = {"Ideas", "Brains", "Profile"};
    private int[] images = {R.drawable.ic_book, R.drawable.ic_group, R.drawable.ic_account_circle};

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);

        fragments.add(new IdeasFragment());
        fragments.add(new BrainToFollowFragment());
        fragments.add(new BrainPageFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Ideas";
            case 1:
                return "Brains";
            case 2:
                return "Profile";
            default:
                return "NA";
        }
    }
}
