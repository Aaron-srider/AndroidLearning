package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayFragmentPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fragments.add(new BaseCalculatorFragment());
        tabLayout.addTab(tabLayout.newTab());

        fragments.add(new ConverterFragment());
        tabLayout.addTab(tabLayout.newTab());

        fragments.add(new HelpPageFragment());
        tabLayout.addTab(tabLayout.newTab());

        tabLayout.setupWithViewPager(viewPager);
        pagerAdapter = new ArrayFragmentPagerAdapter(getSupportFragmentManager(), 0, fragments);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.getTabAt(0).setText("计算器");
        tabLayout.getTabAt(1).setText("换算");
        tabLayout.getTabAt(2).setText("帮助");
    }
}


