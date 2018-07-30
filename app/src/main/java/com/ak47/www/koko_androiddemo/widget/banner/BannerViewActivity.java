package com.ak47.www.koko_androiddemo.widget.banner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ak47.www.koko_androiddemo.R;

import java.util.ArrayList;
import java.util.List;

public class BannerViewActivity extends AppCompatActivity {
    private List<BannerEntity> bannerList = new ArrayList<>();
    private BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_view);
        bannerView = (BannerView) findViewById(R.id.banner);
        bannerList.add(new BannerEntity("http://bangumi.bilibili.com/anime/24588", "工作细胞", "http://i0.hdslb.com/bfs/bangumi/c629dc8c783f7b1aa4e25f93fb8589c8f2557864.png"));
        bannerList.add(new BannerEntity("http://bangumi.bilibili.com/anime/24620", "天狼 Sirius the Jaeger", "http://i0.hdslb.com/bfs/bangumi/36611fa1667b4999fd3c1ff40170b7e54fe25fa2.png"));
        bannerList.add(new BannerEntity("http://bangumi.bilibili.com/anime/24570", "free", "http://i0.hdslb.com/bfs/bangumi/203dbdce93a205357c596b2a07c20cd38273c468.jpg"));
        bannerList.add(new BannerEntity("https://www.bilibili.com/blackboard/topic/activity-yome100h5.html", "梦100", "http://i0.hdslb.com/bfs/bangumi/3fa086ec65cc4becd85a3d71725dedce440d2adb.jpg"));
        bannerView.delayTime(5).build(bannerList);
    }
}
