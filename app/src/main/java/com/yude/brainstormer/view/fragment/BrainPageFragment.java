package com.yude.brainstormer.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.yude.brainstormer.MainActivity;
import com.yude.brainstormer.R;
import com.yude.brainstormer.view.adapter.BrainPageViewPagerAdapter;
import com.yude.brainstormer.view.adapter.ViewPagerAdapter;
import com.yude.brainstormer.view.callback.SignOutCallback;


public class BrainPageFragment extends Fragment {

    private Context context;

    public BrainPageFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brain_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BrainPageViewPagerAdapter viewPagerAdapter = new BrainPageViewPagerAdapter(
                getChildFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );

        ViewPager viewPager = view.findViewById(R.id.brainPageViewPager);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = view.findViewById(R.id.brainPageTabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }
}