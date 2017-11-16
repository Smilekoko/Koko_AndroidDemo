package com.ak47.www.koko_androiddemo.demo2;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ak47.www.koko_androiddemo.R;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * this is a TabLayout dependence on ViewPager
 */
public class SlidingTabLayout_Activity extends AppCompatActivity {
    SlidingTabLayout slidingTabLayout;
    ViewPager viewPager;
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端", "后端", "设计", "工具资源","Web","云服务"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_demo2);
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        //检索顶层窗口装饰视图（包含标准窗口框架/装饰以及其内部的客户端内容），
        View decorView = getWindow().getDecorView();
        viewPager = decorView.findViewById(R.id.viewpager);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tablayout);
        slidingTabLayout.setViewPager(viewPager);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
