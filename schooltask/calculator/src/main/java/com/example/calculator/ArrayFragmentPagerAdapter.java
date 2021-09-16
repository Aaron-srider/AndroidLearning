package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArrayFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();
    public ArrayFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public ArrayFragmentPagerAdapter(FragmentManager supportFragmentManager, int i, ArrayList<Fragment> fragments) {
        super(supportFragmentManager, i);
        this.fragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
