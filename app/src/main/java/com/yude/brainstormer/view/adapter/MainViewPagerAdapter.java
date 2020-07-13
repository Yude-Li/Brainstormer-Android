package com.yude.brainstormer.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yude.brainstormer.MainActivity;
import com.yude.brainstormer.view.fragment.LoginFragment;
import com.yude.brainstormer.view.fragment.RegisterFragment;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private WeakReference<MainActivity> mainActivity;

    private List<Fragment> fragments = new LinkedList<>();

    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior, MainActivity mainActivity) {
        super(fm, behavior);
        this.mainActivity = new WeakReference<>(mainActivity);

        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//            case 1:
//            default:
//                return new Fragment();
//        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "Register";
            default:
                return "NA";
        }
    }
}
