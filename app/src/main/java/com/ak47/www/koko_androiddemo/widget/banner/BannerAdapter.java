package com.ak47.www.koko_androiddemo.widget.banner;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 1796 on 2018/7/16.
 * Banner适配器,就是Viewpage的适配器
 */

public class BannerAdapter extends PagerAdapter {
    private String TAG = "BannerAdapter";
    private List<ImageView> mList;
    private int pos;
    private ViewPagerOnItemClickListener mViewPagerOnItemClickListener;

    void setmViewPagerOnItemClickListener(ViewPagerOnItemClickListener mViewPagerOnItemClickListener) {
        this.mViewPagerOnItemClickListener = mViewPagerOnItemClickListener;
    }

    BannerAdapter(List<ImageView> list) {
        this.mList = list;
        Log.e(TAG, "list.size=" + list.size());
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    interface ViewPagerOnItemClickListener {
        void onItemClick();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        position %= mList.size();
        Log.e(TAG, "position=" + position);
        if (position < 0) {
            position = mList.size() + position;
        }
        ImageView v = mList.get(position);
        pos = position;
        v.setScaleType(ImageView.ScaleType.CENTER);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp = v.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(v);
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewPagerOnItemClickListener != null) {
                    mViewPagerOnItemClickListener.onItemClick();
                }
            }
        });
        container.addView(v);
        return v;
    }
}
