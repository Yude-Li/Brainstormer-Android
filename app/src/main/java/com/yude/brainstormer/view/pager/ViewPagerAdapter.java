package com.yude.brainstormer.view.pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yude.brainstormer.HomeActivity;
import com.yude.brainstormer.view.fragment.BrainProfileFragment;
import com.yude.brainstormer.view.fragment.FollowFragment;
import com.yude.brainstormer.view.fragment.IdeasFragment;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private WeakReference<HomeActivity> homeActivity;

    private List<Fragment> fragments = new LinkedList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, HomeActivity homeActivity) {
        super(fm, behavior);
        this.homeActivity = new WeakReference<>(homeActivity);

        fragments.add(new IdeasFragment());
        fragments.add(new BrainProfileFragment());
        fragments.add(new FollowFragment());

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//            case 1:
//            case 2:
//                return fragments.get(position);
//            default:
//                return new Fragment();
//        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Register";
            case 1:
                return "Auth";
            case 2:
                return "Client";
            default:
                return "NA";
        }
    }
}
