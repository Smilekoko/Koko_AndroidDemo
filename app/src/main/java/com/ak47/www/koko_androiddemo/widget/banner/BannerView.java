package com.ak47.www.koko_androiddemo.widget.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ak47.www.koko_androiddemo.R;
import com.ak47.www.koko_androiddemo.utils.DisplayUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 1796 on 2018/7/16.
 * 自定义Banner无限轮播控件
 * 就是一个Viewpage实现滑动，添加一些代码实现指示标改变
 */

public class BannerView extends RelativeLayout {

    //    @BindView(R.id.layout_banner_viewpager)
//    ViewPager viewPager;
    private ViewPager viewPager;
    //    @BindView(R.id.layout_banner_points_group)
//    LinearLayout points;
    private LinearLayout points;
    private List<ImageView> imageViewList;
    private List<BannerEntity> bannerList;
    private boolean isStopScroll = false;
    //默认轮播时间，10s
    private int delayTime = 10;
    //选中显示Indicator
    private int selectRes = R.drawable.shape_dots_select;
    //非选中显示Indicator
    private int unSelcetRes = R.drawable.shape_dots_default;
    private CompositeDisposable compositeDisposable;
    //当前页的下标
    private int currentPos;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_banner, this, true);
//        ButterKnife.bind(this);
        viewPager = view.findViewById(R.id.layout_banner_viewpager);
        points = view.findViewById(R.id.layout_banner_points_group);
        imageViewList = new ArrayList<>();
    }

    /**
     * 设置轮播间隔时间
     *
     * @param time 轮播间隔时间，单位秒
     */
    public BannerView delayTime(int time) {
        this.delayTime = time;
        return this;
    }

    /**
     * 设置Points资源 Res
     *
     * @param selectRes   选中状态
     * @param unselcetRes 非选中状态
     */
    public void setPointsRes(int selectRes, int unselcetRes) {
        this.selectRes = selectRes;
        this.unSelcetRes = unselcetRes;
    }

    /**
     * 图片停止轮播
     */
    private void stopScroll() {
        isStopScroll = true;
    }

    public void destroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    /**
     * 图片轮播需要传入参数
     */
    public void build(List<BannerEntity> list) {
        destroy();
        if (list.size() == 0) {
            this.setVisibility(GONE);
            return;
        }
        bannerList = new ArrayList<>();
        bannerList.addAll(list);
        final int pointSize;
        pointSize = bannerList.size();
        //判断是否清空 指示器点
        if (points.getChildCount() != 0) {
            points.removeAllViewsInLayout();
        }
        //初始化与个数相同的指示器点
        for (int i = 0; i < pointSize; i++) {
            View dot = new View(getContext());
            dot.setBackgroundResource(unSelcetRes);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    DisplayUtil.dp2px(getContext(), 5),
                    DisplayUtil.dp2px(getContext(), 5));

            params.leftMargin = 10;
            dot.setLayoutParams(params);
            dot.setEnabled(false);
            points.addView(dot);
        }
        points.getChildAt(0).setBackgroundResource(selectRes);
        RequestOptions options = new RequestOptions();
        for (int i = 0; i < bannerList.size(); i++) {
            ImageView mImageView = new ImageView(getContext());
            Glide.with(getContext())
                    .load(bannerList.get(i).img)
                    .apply(RequestOptions.fitCenterTransform())
                    .apply(options.diskCacheStrategy(DiskCacheStrategy.ALL))
                    .apply(options.placeholder(R.drawable.bili_default_image_tv))
                    .apply(options.dontAnimate())
                    .into(mImageView);
            imageViewList.add(mImageView);
        }
        //监听图片轮播，改变指示器状态
        viewPager.clearOnPageChangeListeners();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = position % pointSize;//position从0开始，表示viewpage页的index
                currentPos = position;//很巧妙
                for (int i = 0; i < points.getChildCount(); i++) {
                    points.getChildAt(i).setBackgroundResource(unSelcetRes);
                }
                points.getChildAt(position).setBackgroundResource(selectRes);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (isStopScroll) {
                            startScroll();
                        }
                        break;
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        stopScroll();
                        compositeDisposable.dispose();
                        break;
                }
            }
        });
        BannerAdapter bannerAdapter = new BannerAdapter(imageViewList);
        viewPager.setAdapter(bannerAdapter);
        bannerAdapter.notifyDataSetChanged();
        //图片开始轮播
        startScroll();
    }

    /**
     * 图片开始轮播
     */
    private void startScroll() {
        compositeDisposable = new CompositeDisposable();
        isStopScroll = false;
        Disposable disposable = Observable.timer(delayTime, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (isStopScroll) {
                            return;
                        }
                        isStopScroll = true;
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                });
        compositeDisposable.add(disposable);
    }

}
