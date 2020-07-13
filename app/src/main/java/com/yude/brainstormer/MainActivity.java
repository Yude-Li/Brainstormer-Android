package com.yude.brainstormer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.yude.brainstormer.view.callback.LoginCallback;
import com.yude.brainstormer.view.adapter.MainViewPagerAdapter;

public class MainActivity extends AppCompatActivity implements LoginCallback {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialViewPager();
    }

    private void initialViewPager() {
        MainViewPagerAdapter viewPagerAdapter = new MainViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                this
        );

        viewPager = findViewById(R.id.mainViewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void signInResultCallback() {
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void signUpBtnOnClickCallback() {
        viewPager.setCurrentItem(1);
    }

    @Override
    public void registerResultCallback() {
        viewPager.setCurrentItem(0);
    }
}