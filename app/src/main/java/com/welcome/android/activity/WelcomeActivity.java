package com.welcome.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.welcome.android.CustomViewPager;
import com.welcome.android.R;
import com.welcome.android.SlidingTabLayout;
import com.welcome.android.adapter.ViewPagerAdapter;

public class WelcomeActivity extends AppCompatActivity {
    Toolbar mToolbar;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Events", "My Stuff"};
    int Numboftabs = 2;
    CustomViewPager pager;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
        pager = (CustomViewPager) findViewById(R.id.pager);
        pager.setPagingEnabled(false);
        pager.setAdapter(adapter);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
        tabs.setViewPager(pager);


    }
}
